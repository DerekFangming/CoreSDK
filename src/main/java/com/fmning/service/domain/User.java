package com.fmning.service.domain;

import java.time.Instant;

public class User {
	private int id;
	private String username;
	private String password;
	private String authToken;
	private String veriToken;
	private Instant createdAt;
	private boolean emailConfirmed;
	private String salt;
	private int timezoneOffset;
	
	//Following params are not from DB
	private boolean tokenUpdated = false;
	private String name;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAuthToken() {
		return authToken;
	}

	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}

	public String getVeriToken() {
		return veriToken;
	}

	public void setVeriToken(String veriToken) {
		this.veriToken = veriToken;
	}

	public Instant getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Instant createdAt) {
		this.createdAt = createdAt;
	}

	public boolean getEmailConfirmed() {
		return emailConfirmed;
	}

	public void setEmailConfirmed(boolean emailConfirmed) {
		this.emailConfirmed = emailConfirmed;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public int getTimezoneOffset() {
		return timezoneOffset;
	}

	public void setTimezoneOffset(int timezoneOffset) {
		this.timezoneOffset = timezoneOffset;
	}
	
	public boolean isTokenUpdated() {
		return tokenUpdated;
	}

	public void setTokenUpdated() {
		this.tokenUpdated = true;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}