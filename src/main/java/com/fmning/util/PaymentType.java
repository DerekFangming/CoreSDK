package com.fmning.util;

public enum PaymentType {
	EVENT("Event");
	
	private final String name;
	
	PaymentType(String name) {
        this.name = name;
    }
	
	public String getName(){
		return name;
	}
}