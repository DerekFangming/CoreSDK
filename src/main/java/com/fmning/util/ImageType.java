package com.fmning.util;

public enum ImageType {
	AVATAR("Avatar"),
	FEED("Feed"),
	FEED_COVER("FeedCover"),
	SURVIVAL_GUIDE("SG"),
	OTHERS("Others");
	
	private final String name;
	
	ImageType(String name) {
        this.name = name;
    }
	
	public String getName(){
		return name;
	}
}
