package com.fmning.util;

public enum PaymentStatusType {
	DONE("Done");
	
	private final String name;
	
	PaymentStatusType(String name) {
        this.name = name;
    }
	
	public String getName(){
		return name;
	}
}
