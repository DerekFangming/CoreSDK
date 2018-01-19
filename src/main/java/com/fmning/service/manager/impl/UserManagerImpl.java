package com.fmning.service.manager.impl;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fmning.service.dao.UserDao;
import com.fmning.service.dao.UserDetailDao;
import com.fmning.service.dao.impl.NVPair;
import com.fmning.service.dao.impl.QueryTerm;
import com.fmning.service.domain.User;
import com.fmning.service.domain.UserDetail;
import com.fmning.service.exceptions.NotFoundException;
import com.fmning.service.manager.HelperManager;
import com.fmning.service.manager.UserManager;
import com.fmning.util.ErrorMessage;
import com.fmning.util.Util;

@Component
public class UserManagerImpl implements UserManager{

	@Autowired private UserDao userDao;
	@Autowired private UserDetailDao userDetailDao;
	@Autowired private HelperManager helperManager;
	
	@Override
	public User webRegister(String username, String password) throws IllegalStateException {
		String lowerUsername = username.toLowerCase();
		if(checkUsername(lowerUsername))
			throw new IllegalStateException(ErrorMessage.USERNAME_UNAVAILABLE.getMsg());
		if (lowerUsername.length() > 32)
			throw new IllegalStateException(ErrorMessage.USERNAME_TOO_LONG.getMsg());
		Pattern p = Pattern.compile(".+@.+\\.[a-z]+");
		Matcher m = p.matcher(lowerUsername);
		if(!m.matches())
			throw new IllegalStateException(ErrorMessage.USERNAME_NOT_EMAIL.getMsg());
		
		final Random r = new SecureRandom();
		byte[] salt = new byte[32];
		r.nextBytes(salt);
		String encodedSalt = Base64.encodeBase64String(salt).substring(0, 32);
		
		String encodedPassword;
		try {
			encodedPassword = Util.MD5(password + encodedSalt);
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalStateException(ErrorMessage.INTERNAL_LOGIC_ERROR.getMsg());
		}
		
		String veriCode = helperManager.getEmailConfirmCode(username);
		Instant exp = Instant.now().plus(Duration.ofDays(Util.tokenTimeout));
		String accessToken = helperManager.createAccessToken(username, exp);
		
		User user = new User();
		user.setUsername(lowerUsername);
		user.setPassword(encodedPassword);
		user.setVeriToken(veriCode);
		user.setAccessToken(accessToken);
		user.setCreatedAt(Instant.now());
		user.setEmailConfirmed(false);
		user.setSalt(encodedSalt);
		user.setRoleId(3);//NormalUser
		int userId = userDao.persist(user);
		user.setId(userId);
		
		return user;
	}

	@Override
	public boolean checkUsername(String username) {
		List<QueryTerm> terms = new ArrayList<QueryTerm>();
		terms.add(UserDao.Field.USERNAME.getQueryTerm(username.toLowerCase()));
		return userDao.exists(terms);
		
	}

	@Override
	public void updateVeriCode(String username, String code) throws NotFoundException{
		List<QueryTerm> terms = new ArrayList<QueryTerm>();
		terms.add(UserDao.Field.USERNAME.getQueryTerm(username.toLowerCase()));
		User user;
		try{
			user = userDao.findObject(terms);
		}catch(NotFoundException e){
			throw new NotFoundException(ErrorMessage.USER_NOT_FOUND.getMsg());
		}
		
		NVPair newValue = new NVPair(UserDao.Field.VERI_TOKEN.name, code);
		
		userDao.update(user.getId(), newValue);
	}

	@Override
	public void checkVeriCode(String username, String code) throws NotFoundException {
		List<QueryTerm> terms = new ArrayList<QueryTerm>();
		terms.add(UserDao.Field.USERNAME.getQueryTerm(username.toLowerCase()));
		User user;
		try {
			 user = userDao.findObject(terms);
		} catch (NotFoundException e) {
			throw new NotFoundException(ErrorMessage.INVALID_VERIFICATION_CODE.getMsg());
		}
		if (user.getEmailConfirmed()) {
			throw new NotFoundException(ErrorMessage.EMAIL_ALREADY_VERIFIED.getMsg());
		}
		if (!user.getVeriToken().equals(code)) {
			throw new NotFoundException(ErrorMessage.INVALID_VERIFICATION_CODE.getMsg());
		}
	}

	@Override
	public void confirmEmail(String username) throws NotFoundException {
		List<QueryTerm> terms = new ArrayList<QueryTerm>();
		terms.add(UserDao.Field.USERNAME.getQueryTerm(username.toLowerCase()));
		User user;
		try{
			user = userDao.findObject(terms);
		}catch(NotFoundException e){
			throw new NotFoundException(ErrorMessage.USER_NOT_FOUND.getMsg());
		}
		
		List<NVPair> newValue = new ArrayList<NVPair>();
		newValue.add(new NVPair("veri_token", ""));
		newValue.add(new NVPair("email_confirmed", true));
		
		userDao.update(user.getId(), newValue);
		
	}

	@Override
	public void updateAccessToken(String username, String token) throws NotFoundException {
		List<QueryTerm> terms = new ArrayList<QueryTerm>();
		terms.add(UserDao.Field.USERNAME.getQueryTerm(username.toLowerCase()));
		User user;
		try{
			user = userDao.findObject(terms);
		}catch(NotFoundException e){
			throw new NotFoundException(ErrorMessage.USER_NOT_FOUND.getMsg());
		}
		
		NVPair pair = new NVPair(UserDao.Field.ACCESS_TOKEN.name, token);
		
		userDao.update(user.getId(), pair);
		
	}

	@Override
	public User loginMigrate(String username, String password) throws NotFoundException {
		List<QueryTerm> terms = new ArrayList<QueryTerm>();
		terms.add(UserDao.Field.USERNAME.getQueryTerm(username.toLowerCase()));
		terms.add(UserDao.Field.PASSWORD.getQueryTerm(password));
		User user;
		try{
			user = userDao.findObject(terms);
		}catch(NotFoundException e){
			throw new NotFoundException(ErrorMessage.USER_NOT_FOUND.getMsg());
		}
		
		boolean recFlag = false;
		try{
			Map<String, Object> result = helperManager.decodeJWT(user.getAccessToken());
			Instant exp = Instant.parse((String)result.get("expire"));
			
			if(exp.compareTo(Instant.now()) < 0){
				recFlag = true;
			}
		}catch (IllegalStateException e) {
			recFlag = true;
		}
		
		if(recFlag){
			Instant newExp = Instant.now().plus(Duration.ofDays(Util.tokenTimeout));
			String accessToken = helperManager.createAccessToken(username, newExp);
			NVPair pair = new NVPair(UserDao.Field.ACCESS_TOKEN.name, accessToken);
			
			userDao.update(user.getId(), pair);
			user.setAccessToken(accessToken);
		}
		
		return user;
	}
	
	@Override
	public User webLogin(String username, String password) throws NotFoundException {
		List<QueryTerm> terms = new ArrayList<QueryTerm>();
		terms.add(UserDao.Field.USERNAME.getQueryTerm(username.toLowerCase()));
		User user;
		try{
			user = userDao.findObject(terms);
			
			if (!user.getPassword().equals(Util.MD5(password + user.getSalt())))
				throw new NotFoundException();
			
		}catch(NotFoundException | NoSuchAlgorithmException e){
			throw new NotFoundException(ErrorMessage.USER_NOT_FOUND.getMsg());
		}
		
		boolean recreateAccessToken = false;
		try{
			Map<String, Object> result = helperManager.decodeJWT(user.getAccessToken());
			Instant exp = Instant.parse((String)result.get("expire"));
			
			if(exp.compareTo(Instant.now()) < 0){
				recreateAccessToken = true;
			}
		}catch (IllegalStateException e) {
			recreateAccessToken = true;
		}
		
		if(recreateAccessToken){
			Instant newExp = Instant.now().plus(Duration.ofDays(Util.tokenTimeout));
			String accessToken = helperManager.createAccessToken(username, newExp);
			NVPair pair = new NVPair(UserDao.Field.ACCESS_TOKEN.name, accessToken);
			
			userDao.update(user.getId(), pair);
			user.setAccessToken(accessToken);
			user.setTokenUpdated();
		}
		
		return user;
	}
	
	@Override
	public User validateAccessToken(Map<String, Object> request) throws NotFoundException{
		return validateAccessToken((String) request.get("accessToken"));
	}
	
	@Override
	public User validateAccessToken(HttpServletRequest request)  throws NotFoundException {
		Cookie[] cookies = request.getCookies();
		if(cookies != null) {
			for(Cookie c : cookies){
				if (c.getName().equals("accessToken")) {
					return validateAccessToken(c.getValue());
				}
			}
		}
		throw new NotFoundException(ErrorMessage.INVALID_ACCESS_TOKEN.getMsg());
	}
	
	@Override
	public User validateAccessToken(String accessToken)  throws NotFoundException {
		try{
			Map<String, Object> result = helperManager.decodeJWT(accessToken);
			Instant exp = Instant.parse((String)result.get("expire"));
			
			List<QueryTerm> terms = new ArrayList<QueryTerm>();
			terms.add(UserDao.Field.USERNAME.getQueryTerm(((String)result.get("username")).toLowerCase()));
			User user = userDao.findObject(terms);
			
			if(exp.compareTo(Instant.now()) < 0){
				user.setTokenUpdated();//Token from client is out-dated and needs update
				result = helperManager.decodeJWT(user.getAccessToken());
				exp = Instant.parse((String)result.get("expire"));
				
				if(exp.compareTo(Instant.now()) < 0){//The token in DB is also expired
					Instant newExp = Instant.now().plus(Duration.ofDays(Util.tokenTimeout));
					String newToken = helperManager.createAccessToken(user.getUsername(), newExp);
					NVPair pair = new NVPair(UserDao.Field.ACCESS_TOKEN.name, newToken);
					
					userDao.update(user.getId(), pair);
					user.setAccessToken(newToken);
				}
				
				return user;
			} else {
				return user;
			}
		}catch (Exception e){
			throw new NotFoundException(ErrorMessage.INVALID_ACCESS_TOKEN.getMsg());
		}
	}
	
	@Override
	public String getUserDisplayedName(int userId) throws NotFoundException{
		try{
			UserDetail userDetail = getUserDetail(userId);
			if(userDetail.getNickname() != null){
				return userDetail.getNickname();
			}else if(userDetail.getName() != null){
				return userDetail.getName();
			}else{
				throw new NotFoundException();
			}
		}catch(NotFoundException e){
			try{
				return "Unknown";
			}catch(NotFoundException ex){
				throw new IllegalStateException(ErrorMessage.USER_NOT_FOUND.getMsg());
			}
		}
	}
	
	@Override
	public void changePassword(int userId, String password) throws IllegalStateException, NotFoundException {
		List<QueryTerm> terms = new ArrayList<QueryTerm>();
		terms.add(UserDao.Field.ID.getQueryTerm(userId));
		User user;
		try{
			user = userDao.findObject(terms);
		}catch(NotFoundException e){
			throw new NotFoundException(ErrorMessage.USER_NOT_FOUND.getMsg());
		}
		
		String encodedPassword;
		try {
			encodedPassword = Util.MD5(password + user.getSalt());
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalStateException(ErrorMessage.INTERNAL_LOGIC_ERROR.getMsg());
		}
		
		List<NVPair> newValues = new ArrayList<NVPair>();
		newValues.add(new NVPair(UserDao.Field.PASSWORD.name, encodedPassword));
		
		userDao.update(user.getId(), newValues);
	}
	
	/* The following methods are for user details*/

	@Override
	public UserDetail getUserDetail(int userId) throws NotFoundException{
		List<QueryTerm> terms = new ArrayList<QueryTerm>();
		terms.add(UserDetailDao.Field.USER_ID.getQueryTerm(userId));
		try{
			return userDetailDao.findObject(terms);
		}catch(NotFoundException e){
			throw new NotFoundException(ErrorMessage.USER_DETAIL_NOT_FOUND.getMsg());
		}
	}
	
	@Override
	public void saveUserDetail(int userId, String name, String nickname, int age, String gender,
			String location, String whatsUp, String birthday, String year, String major){
		List<QueryTerm> terms = new ArrayList<QueryTerm>();
		terms.add(UserDetailDao.Field.USER_ID.getQueryTerm(userId));
		try{
			UserDetail userDetail = userDetailDao.findObject(terms);
			userDetail.setName(name);
			userDetail.setNickname(nickname);
			userDetail.setAge(age);
			userDetail.setGender(gender);
			userDetail.setLocation(location);
			userDetail.setWhatsUp(whatsUp);
			userDetail.setBirthday(birthday);
			userDetail.setYear(year);
			userDetail.setMajor(major);
			userDetailDao.update(userDetail.getId(), userDetail);
		}catch(NotFoundException e){
			UserDetail userDetail = new UserDetail();
			userDetail.setUserId(userId);
			userDetail.setName(name);
			userDetail.setNickname(nickname);
			userDetail.setAge(age);
			userDetail.setGender(gender);
			userDetail.setLocation(location);
			userDetail.setWhatsUp(whatsUp);
			userDetail.setBirthday(birthday);
			userDetail.setYear(year);
			userDetail.setMajor(major);
			userDetailDao.persist(userDetail);
		}
		
	}
	
	
}

	
