package com.fmning.service.dao.impl;

public enum LogicalOpType
{
	AND,
	OR;

	protected String asString;
	
	LogicalOpType()
	{
		this.asString = " " + this.name() + " ";			
	}		
}

