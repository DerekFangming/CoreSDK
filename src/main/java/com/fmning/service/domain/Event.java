package com.fmning.service.domain;

import java.time.Instant;

public class Event {
	private int id;
	private int mappingId;
	private String title;
	private Instant startTime;
	private Instant endTime;
	private String location;
	private Instant createdAt;
	private int ownerId;
	
	public int getId(){
		return id;
	}
	
	public void setId(int id){
		this.id = id;
	}
	
	public int getMappingId(){
		return mappingId;
	}
	
	public void setMappingId(int mappingId){
		this.mappingId = mappingId;
	}
	
	public String getTitle(){
		return title;
	}
	
	public void setTitle(String title){
		this.title = title;
	}
	
	public Instant getStartTime(){
		return startTime;
	}
	
	public void setStartTime(Instant startTime){
		this.startTime = startTime;
	}
	
	public Instant getEndTime(){
		return endTime;
	}
	
	public void setEndTime(Instant endTime){
		this.endTime = endTime;
	}
	
	public String getLocation(){
		return location;
	}
	
	public void setLocation(String location){
		this.location = location;
	}
	
	public Instant getCreatedAt(){
		return createdAt;
	}
	
	public void setCreatedAt(Instant createdAt){
		this.createdAt = createdAt;
	}
	
	public int getOwnerId(){
		return ownerId;
	}
	
	public void setOwnerId(int ownerId){
		this.ownerId = ownerId;
	}
	
}
