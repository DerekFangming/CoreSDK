package com.fmning.service.domain;

import java.time.Instant;

public class Event {
	private int id;
	private String type;
	private int mappingId;
	private String title;
	private String description;
	private Instant startTime;
	private Instant endTime;
	private String location;
	private double fee;
	private Instant createdAt;
	private int ownerId;
	private int ticketTemplateId;
	
	public int getId(){
		return id;
	}
	
	public void setId(int id){
		this.id = id;
	}
	
	public String getType(){
		return type;
	}
	
	public void setType(String type){
		this.type = type;
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
	
	public String getDescription(){
		return description;
	}
	
	public void setDescription(String description){
		this.description = description;
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
	
	public double getFee(){
		return fee;
	}
	
	public void setFee(double fee){
		this.fee = fee;
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

	public int getTicketTemplateId() {
		return ticketTemplateId;
	}

	public void setTicketTemplateId(int ticketTemplateId) {
		this.ticketTemplateId = ticketTemplateId;
	}

	
}
