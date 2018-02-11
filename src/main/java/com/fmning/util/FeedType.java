package com.fmning.util;

public enum FeedType {
	EVENT("Event"),
	TRADE("Trade"),
	BLOG("Blog");
	
	private final String name;
	
	FeedType(String name) {
        this.name = name;
    }
	
	public String getName(){
		return name;
	}
}