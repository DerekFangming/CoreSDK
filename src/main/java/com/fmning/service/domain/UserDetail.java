package com.fmning.service.domain;

public class UserDetail {
	private int id;
    private int userId;
    private String name;
    private String nickname;
    private int age;
    private String gender;
    private String location;
    private String whatsUp;
    private String birthday;
    private String year;
    private String major;
    
    public int getId(){
    	return id;
    }
    
    public void setId(int id){
    	this.id = id;
    }
    
    public int getUserId(){
    	return userId;
    }
    
    public void setUserId(int userId){
    	this.userId = userId;
    }
    
    public String getName(){
    	return name;
    }
    
    public void setName(String name){
    	this.name = name;
    }
    
    public String getNickname(){
    	return nickname;
    }
    
    public void setNickname(String nickname){
    	this.nickname = nickname;
    }
    
    public int getAge(){
    	return age;
    }
    
    public void setAge(int age){
    	this.age = age;
    }
    
    public String getGender(){
    	return gender;
    }
    
    public void setGender(String gender){
    	this.gender = gender;
    }
    
    public String getLocation(){
    	return location;
    }
    
    public void setLocation(String location){
    	this.location = location;
    }
    
    public String getWhatsUp(){
    	return whatsUp;
    }
    
    public void setWhatsUp(String whatsUp){
    	this.whatsUp = whatsUp;
    }
    
    public String getBirthday(){
    	return birthday;
    }
    
    public void setBirthday(String birthday){
    	this.birthday = birthday;
    }
    
    public String getYear(){
    	return year;
    }
    
    public void setYear(String year){
    	this.year = year;
    }
    
    public String getMajor(){
    	return major;
    }
    
    public void setMajor(String major){
    	this.major = major;
    }

}
