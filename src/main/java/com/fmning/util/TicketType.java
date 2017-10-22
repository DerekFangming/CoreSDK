package com.fmning.util;

public enum TicketType {

	PAYMENT("Payment");
	
	private final String name;
	
	TicketType(String name) {
        this.name = name;
    }
	
	public String getName(){
		return name;
	}
}
