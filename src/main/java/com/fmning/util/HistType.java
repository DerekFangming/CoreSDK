package com.fmning.util;

public enum HistType {
	UPDATE("U"),
	DELETE("D"),
	CHANGE_LOCATION("L");
	
	private final String name;
	
	HistType(String name) {
        this.name = name;
    }
	
	public String getName(){
		return name;
	}

}
