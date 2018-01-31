package com.fmning.service.manager;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.fmning.service.domain.User;
import com.fmning.service.domain.UserDetail;
import com.fmning.service.exceptions.NotFoundException;

public interface UserManager {
	
	/**
	 * Get user by database Id
	 * @param id the user db id
	 * @return the user object
	 * @throws NotFoundException if user with the id does not exist
	 */
	public User getUserById(int id) throws NotFoundException;
	
	/**
	 * Get user by username
	 * @param username the username
	 * @return the user object
	 * @throws NotFoundException if such user does not exist
	 */
	public User getUserByUsername(String username) throws NotFoundException;
	
	/**
	 * The method that handles web register, where there is no sending of salt and the password is not encrypted
	 * @param username the username
	 * @param password the plain text password, without hashing
	 * @return the user id
	 * @throws IllegalStateException
	 * @throws NotFoundException
	 */
	public User webRegister(String username, String password) throws IllegalStateException;
	
	/**
	 * Check if the username has been registered
	 * @param username the username to be checked
	 * @return true if the username has been registered
	 */
	public boolean checkUsername(String username);
	
	/**
	 * Update the verification code for the specific user for future check
	 * @param username the user to be updated the verification code
	 * @param code the verification code
	 * @throws NotFoundException if the user is not found
	 */
	public void updateVeriCode(String username, String code) throws NotFoundException;
	
	/**
	 * Check if the verification code matches what exists on the user
	 * @param username the user to be checked
	 * @param code the verification code
	 * @param action change email or password
	 * @throws NotFoundException if the user with the verification code does not exist
	 */
	public void checkVeriCode(String username, String code, String action) throws NotFoundException;
	
	/**
	 * Confirm that the email address of the user has been confirmed
	 * @param username the user name to set email flag to be true
	 * @throws NotFoundException if the user is not found
	 */
	public void confirmEmail(String username) throws NotFoundException;
	
	/**
	 * Update access token for a given user
	 * @param username the user
	 * @param token the access token code
	 * @throws NotFoundException id the user is not found by its username
	 */
	public void updateAccessToken(String username, String token) throws NotFoundException;
	
	
	/**
	 * Check if the login username and password is correct
	 * @param username the user
	 * @param password the hashed password
	 * @throws NotFoundException
	 */
	public User loginMigrate (String username, String password) throws NotFoundException;

	/**
	 * The method that handles web login, where there is no sending of salt and the password is not encrypted
	 * @param username the username
	 * @param password the password that is not encrypted
	 * @return the user
	 * @throws NotFoundException if the user does not exist
	 */
	public User webLogin(String username, String password) throws NotFoundException;
	
	/**
	 * Validate the access token from post request params
	 * If it expired, get a new one from DB or create a new one if DB version is also expired
	 * @param request the request object (JSON object, map)
	 * @return the user for this access token
	 * @throws NotFoundException if the access token is invalid
	 */
	public User validateAccessToken(Map<String, Object> request)  throws NotFoundException;
	
	/**
	 * Validate the access token from request cookies
	 * If it expired, get a new one from DB or create a new one if DB version is also expired
	 * @param request the servlet request
	 * @return the user for this access token
	 * @throws NotFoundException if the access token is invalid
	 */
	public User validateAccessToken(HttpServletRequest request)  throws NotFoundException;
	
	/**
	 * Validate the access token itself.
	 * If it expired, get a new one from DB or create a new one if DB version is also expired
	 * @param accessToken the accessToken string
	 * @return the user for this access token
	 * @throws NotFoundException if the access token is invalid
	 */
	public User validateAccessToken(String accessToken)  throws NotFoundException;
	
	/**
	 * Get a name of a user for displaying purpose. This method will try to get nickname from user detail.
	 * If there is no nickname, get real name. If there is no user detail, get the username.
	 * @param userId the id of the user
	 * @return a name in the preference order mentioned above
	 * @throws NotFoundException if the user does not exist
	 */
	public String getUserDisplayedName(int userId) throws NotFoundException;
	
	/**
	 * Change the password of a given user
	 * @param userId the id of the user
	 * @param password the new password
	 * @throws IllegalStateException if password format is incorrect
	 * @throws NotFoundException if the user does not exist
	 */
	public void changePassword(int userId, String password) 
			throws IllegalStateException, NotFoundException;
	
	/**
	 * Get all the users from database
	 * @return
	 */
	public List<User> getAllUsers();
	
	/**
	 * Update the role id of user
	 * @param userId the user
	 * @param roleId the new role id
	 * @throws NotFoundException if the user is not found
	 */
	public void setUserRole(int userId, int roleId) throws NotFoundException;
	
	/* The following methods are for user details*/

	/**
	 * Return the user detail object for the given user
	 * @param userId the id of the user
	 * @return the user detail object
	 * @throws NotFoundException if the user does not have details listed
	 */
	public UserDetail getUserDetail(int userId) throws NotFoundException;
	
	/**
	 * Save the detail of a user to database. Update current record if already exist
	 * @param userId
	 * @param name the real name
	 * @param nickname
	 * @param age
	 * @param gender mail is M, female is F
	 * @param location
	 * @param whatsUp
	 * @param birthday
	 * @param year
	 * @param major
	 */
	public void saveUserDetail(int userId, String name, String nickname, int age, String gender,
			String location, String whatsUp, String birthday, String year, String major);
	
}
