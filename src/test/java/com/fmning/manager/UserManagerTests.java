package com.fmning.manager;

import static org.junit.Assert.*;

import java.time.Duration;
import java.time.Instant;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fmning.service.domain.User;
import com.fmning.service.exceptions.NotFoundException;
import com.fmning.service.manager.HelperManager;
import com.fmning.service.manager.UserManager;
import com.fmning.util.ErrorMessage;
import com.fmning.util.Util;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="/sdkUnitTesting.xml")
public class UserManagerTests {
	
	@Autowired private UserManager userManager;
	@Autowired private HelperManager helperManager;
	
	@Test
	public void testLoginMigrate(){
		try {
			User user = userManager.loginMigrate("TestUser@fmning.com", "testUserPassword");
			if(user.isTokenUpdated()) {
				fail("Flag error for log in action");
			}
		} catch (NotFoundException e) {
			fail(e.toString());
		}
		try{
			userManager.loginMigrate("TestUser@fmning.com", "WRONG");
			fail(ErrorMessage.SHOULD_NOT_PASS_ERROR.getMsg());
		} catch (NotFoundException e){
		}
		try{
			userManager.loginMigrate("WRONG", "testUserPassword");
			fail(ErrorMessage.SHOULD_NOT_PASS_ERROR.getMsg());
		} catch (NotFoundException e){
			return;
		}
	}
	
	@Test
	public void testCheckUsername(){
		assertTrue(userManager.checkUsername("TestUser@fmning.com"));
		assertFalse(userManager.checkUsername("WRONG"));
	}
	
	@Test
	public void testUpdateVerificationCode(){
		try{
			userManager.updateVeriCode("WRONG", "");
		}catch(NotFoundException e){
			assertEquals(e.getMessage(), ErrorMessage.USER_NOT_FOUND.getMsg());
		}
	}
	
	@Test
	public void testCheckVerificationCode(){
		try{
			userManager.checkVeriCode("WRONG","");
			fail(ErrorMessage.SHOULD_NOT_PASS_ERROR.getMsg());
		}catch(NotFoundException e){
		}
		try{
			userManager.checkVeriCode("TestUser@fmning.com","");
		}catch(NotFoundException e){
			if (!e.getMessage().equals(ErrorMessage.EMAIL_ALREADY_VERIFIED.getMsg())) {
				fail(e.toString());
			}
		}
	}
	
	@Test
	public void testNewRegister() {
		try {
			userManager.webRegister("TestUserNew@fmning.com", "testUserPassword");
			fail(ErrorMessage.SHOULD_NOT_PASS_ERROR.getMsg());
		} catch (IllegalStateException e) {
			assertEquals(e.getMessage(), ErrorMessage.USERNAME_UNAVAILABLE.getMsg());
		}
	}
	
	@Test
	public void testNewLogin() {
		try {
			userManager.webLogin("TestUserNew@fmning.com", "testUserPassword");
		} catch (NotFoundException e) {
			fail(e.toString());
		}
		try{
			userManager.webLogin("TestUserNew@fmning.com", "WRONG");
			fail(ErrorMessage.SHOULD_NOT_PASS_ERROR.getMsg());
		} catch (NotFoundException e){
		}
		
	}
	
	@Test
	public void testValidatingAccessToken() {
		//validating same access token which has expired
		String username = "TestUserNew@fmning.com";
		String accessToken = helperManager.createAccessToken(username, Instant.now());
		userManager.updateAccessToken(username, accessToken);
		User user = userManager.validateAccessToken(accessToken);
		assertTrue(user.isTokenUpdated());
		assertNotEquals(accessToken, user.getAccessToken());
		
		//validating same access token which is not expired
		accessToken = helperManager.createAccessToken(username,
				Instant.now().plus(Duration.ofDays(1)));
		userManager.updateAccessToken(username, accessToken);
		user = userManager.validateAccessToken(accessToken);
		assertFalse(user.isTokenUpdated());
		assertEquals(accessToken, user.getAccessToken());
		
		//validating an old access token which had expired but there is a current valid token in db
		accessToken = helperManager.createAccessToken(username, Instant.now());
		String newValidToken = helperManager.createAccessToken(username,
				Instant.now().plus(Duration.ofDays(1)));
		userManager.updateAccessToken(username, newValidToken);
		user = userManager.validateAccessToken(accessToken);
		assertTrue(user.isTokenUpdated());
		assertEquals(newValidToken, user.getAccessToken());
		
		//validate an old access token which had expired but the db one is also expired
		accessToken = helperManager.createAccessToken(username, Instant.now());
		String newInvalidToken = helperManager.createAccessToken(username, Instant.now());
		userManager.updateAccessToken(username, newInvalidToken);
		user = userManager.validateAccessToken(accessToken);
		assertNotEquals(accessToken, newInvalidToken);
		assertTrue(user.isTokenUpdated());
		assertNotEquals(accessToken, user.getAccessToken());
		assertNotEquals(newInvalidToken, user.getAccessToken());
	}
	
	@Test
	public void testNewLoginForAccessToken() {
		String username = "TestUserNew@fmning.com";
		String accessToken = helperManager.createAccessToken(username, Instant.now());
		userManager.updateAccessToken(username, accessToken);
		User user = userManager.webLogin(username, "testUserPassword");
		assertTrue(user.isTokenUpdated());
		assertNotEquals(accessToken, user.getAccessToken());
		
		accessToken = helperManager.createAccessToken(username,
				Instant.now().plus(Duration.ofDays(1)));
		userManager.updateAccessToken(username, accessToken);
		user = userManager.webLogin(username, "testUserPassword");
		assertFalse(user.isTokenUpdated());
		assertEquals(accessToken, user.getAccessToken());
	}

}
