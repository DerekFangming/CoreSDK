package com.fmning.util;

public enum EventType {
	FEED("Feed");
	
	private final String name;
	
	EventType(String name) {
        this.name = name;
    }
	
	public String getName(){
		return name;
	}
}
