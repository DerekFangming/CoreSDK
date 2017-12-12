package com.fmning.util;

public enum ImageType {
	AVATAR("Avatar"),
	FEED("Feed"),
	FEED_COVER("FeedCover"),
	OTHERS("Others");
	
	private final String name;
	
	ImageType(String name) {
        this.name = name;
    }
	
	public String getName(){
		return name;
	}
}
