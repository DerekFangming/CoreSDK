package com.fmning.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.fmning.service.exceptions.NotFoundException;
import com.fmning.service.exceptions.SessionExpiredException;

public class Util {
	
	public static String imagePath = "/Volumes/Data/images/";
	public final static int nullInt = -1;

	public static String verifyImageType(String type) {
		if (type == null)
			return ImageType.OTHERS.getName();
		
		for (ImageType it : ImageType.values()) {
	        if (it.getName().toUpperCase().equals(type.toUpperCase())) {
	            return type;
	        }
	    }
		
		return ImageType.OTHERS.getName();
    }
	
	public static Map<String, Object> createErrorRespondFromException(Exception e){
		Map<String, Object> respond = new HashMap<String, Object>();
		if(e instanceof NullPointerException || e instanceof NumberFormatException){
			respond.put("error", ErrorMessage.INCORRECT_PARAM.getMsg());
		}else if(e instanceof IllegalStateException){
			respond.put("error", e.getMessage());
		}else if(e instanceof NotFoundException){
			respond.put("error", e.getMessage());
		}else if(e instanceof SessionExpiredException){
			respond.put("error", ErrorMessage.SESSION_EXPIRED.getMsg());
		}else if(e instanceof FileNotFoundException) {
			respond.put("error", ErrorMessage.INCORRECT_INTER_IMG_PATH.getMsg());
		}else if(e instanceof IOException){
			respond.put("error", ErrorMessage.INCORRECT_INTER_IMG_IO.getMsg());
		}else if(e instanceof InterruptedException){
			// test delay only
		}else{
			//TODO Put into db
			e.printStackTrace();
			respond.put("error", ErrorMessage.UNKNOWN_ERROR.getMsg());
		}
		return respond;
	}
	
	public static String nullToEmptyString(String input){
		if(input == null){
			return "";
		}else{
			return input;
		}
	}
	
	public static String emptyStringToNull(String input){
		if(input.trim().equals("")){
			return null;
		}else{
			return input.trim();
		}
	}
	
	public static Date parseDate(Instant instant) {
		try {
			return Date.from(instant);
		} catch (Exception e) {
			return null;
		}
	}
	
	public static Instant parseTimestamp(Timestamp timestamp) {
		return timestamp == null ? null : timestamp.toInstant();
	}
}
