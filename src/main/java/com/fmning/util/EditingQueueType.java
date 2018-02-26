package com.fmning.util;

public enum EditingQueueType {
	SG("SG"),
	FEED("Feed");
	
	private final String name;
	
	EditingQueueType(String name) {
        this.name = name;
    }
	
	public String getName(){
		return name;
	}
}
