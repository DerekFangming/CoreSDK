package com.fmning.service.manager.impl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fmning.service.dao.ErrorLogDao;
import com.fmning.service.domain.ErrorLog;
import com.fmning.service.exceptions.NotFoundException;
import com.fmning.service.exceptions.SessionExpiredException;
import com.fmning.service.manager.ErrorManager;
import com.fmning.util.ErrorMessage;

@Component
public class ErrorManagerImpl implements ErrorManager {
	
	@Autowired private ErrorLogDao errorLogDao;

	@Override
	public Map<String, Object> createErrorRespondFromException(Exception e, HttpServletRequest request) {
		StringBuffer requestURL = request.getRequestURL();
	    String queryString = request.getQueryString();
		return createErrorRespondFromException(e, requestURL.toString(), queryString);
	}

	@Override
	public Map<String, Object> createErrorRespondFromException(Exception e, String url, Map<String, Object> request) {
		// TODO Auto-generated method stub
		return createErrorRespondFromException(e, url, getPostRequestsString(request));
	}
	
	private Map<String, Object> createErrorRespondFromException (Exception e, String url, String params) {
		Map<String, Object> respond = new HashMap<String, Object>();
		boolean writeToLog = false;
		if (e instanceof IllegalStateException) {
			respond.put("error", e.getMessage());
		} else if (e instanceof NotFoundException) {
			respond.put("error", e.getMessage());
		} else if(e instanceof SessionExpiredException) {
			respond.put("error", ErrorMessage.SESSION_EXPIRED.getMsg());
		} else if (e instanceof NullPointerException || e instanceof NumberFormatException) {
			writeToLog = true;
			respond.put("error", ErrorMessage.INCORRECT_PARAM.getMsg());
		} else if (e instanceof FileNotFoundException) {
			writeToLog = true;
			respond.put("error", ErrorMessage.INCORRECT_INTER_IMG_PATH.getMsg());
		} else if (e instanceof IOException) {
			writeToLog = true;
			respond.put("error", ErrorMessage.INCORRECT_INTER_IMG_IO.getMsg());
		} else{
			writeToLog = true;
			respond.put("error", ErrorMessage.UNKNOWN_ERROR.getMsg());
		}
		
		if(writeToLog) {
			try {
				Writer writer = new StringWriter();
				e.printStackTrace(new PrintWriter(writer));
				String stackTrace = writer.toString();
				
				ErrorLog errorLog = new ErrorLog();
				errorLog.setUrl(StringUtils.abbreviate(url, 100));
				errorLog.setParam(params);
				errorLog.setTrace(stackTrace);
				errorLog.setCreatedAt(Instant.now());
				errorLogDao.persist(errorLog);
			} catch (Exception e1) {
				try {
					ErrorLog errorLog = new ErrorLog();
					errorLog.setUrl(StringUtils.abbreviate(url, 100));
					errorLog.setParam(params);
					errorLog.setTrace("Cannot read or having other issues");
					errorLog.setCreatedAt(Instant.now());
					errorLogDao.persist(errorLog);
				} catch(Exception e2){}
			}
		}
		
		return respond;
	}
	
	private String getPostRequestsString(Map<String, Object> request) {
		ObjectMapper mapperObj = new ObjectMapper();
		try {
			return mapperObj.writeValueAsString(request);
		} catch (JsonProcessingException e) {
			String json = "{";
			boolean flag = false;
			for (String key : request.keySet()) {
				if (flag) {
					json += ", ";
				}
				flag = true;
				json += key + ": ";
				json += String.valueOf(request.get(key));
			}
			json += "}";
			return json;
		}
	}

}
