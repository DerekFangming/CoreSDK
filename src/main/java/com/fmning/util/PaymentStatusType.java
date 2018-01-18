package com.fmning.util;

public enum PaymentStatusType {
	DONE("Done"),
	ALREADY_PAID("AlreadyPaid"),
	NOT_EXIST("NotExist"),
	REJECTED("Rejected");
	
	private final String name;
	
	PaymentStatusType(String name) {
        this.name = name;
    }
	
	public String getName(){
		return name;
	}
}
