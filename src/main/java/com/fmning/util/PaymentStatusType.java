package com.fmning.util;

//TODO: Status should only be Done, Not exists or Rejected
public enum PaymentStatusType {
	DONE("Done"),
	ALREADY_PAID("AlreadyPaid"),//TODO: Remove this
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
