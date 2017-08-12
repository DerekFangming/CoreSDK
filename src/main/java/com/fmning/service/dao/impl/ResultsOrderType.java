package com.fmning.service.dao.impl;

public enum ResultsOrderType{
	ASCENDING("ASC"),
	DESCENDING("DESC"),
	NONE(null);
	
	protected String asString;
	
	ResultsOrderType(String keyword){
		this.asString = " " + keyword + " "; 
	}
}
