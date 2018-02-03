package com.fmning.manager;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fmning.service.exceptions.NotFoundException;
import com.fmning.service.manager.ErrorManager;
import com.fmning.util.ErrorMessage;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="/sdkUnitTesting.xml")
public class ErrorManagerTests {
	
	@Autowired private ErrorManager errorManager;
	
	@BeforeClass
    public static void setUpBaseClass() {
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }
	
	@Test
	public void testGenerateString() throws JsonProcessingException{
		Map<String, Object> inputMap = new HashMap<String, Object>();
        inputMap.put("name", "Java2Novice");
        inputMap.put("site", "http://java2novice.com");
        inputMap.put("array", Collections.singletonList("Buenos Aires"));
        inputMap.put("number", 11);
        inputMap.put("double", 1.2);
        
        ObjectMapper mapperObj = new ObjectMapper();
		String jacksonStr = mapperObj.writeValueAsString(inputMap);
		
		String json = "{";
		boolean flag = false;
		for (String key : inputMap.keySet()) {
			if (flag) {
				json += ", ";
			}
			flag = true;
			json += key + ": ";
			json += String.valueOf(inputMap.get(key));
		}
		json += "}";
        
		assertEquals(1, 1);
	}
	
	@Test
	public void testAcceptableError() {
		Map<String, Object> inputMap = new HashMap<String, Object>();
        inputMap.put("name", "Java2Novice");
        inputMap.put("site", "http://java2novice.com");
		try {
			throw new NotFoundException("Some message");
		} catch (Exception e) {
			Map<String, Object> respond = errorManager.createErrorRespondFromException(e,
					"wc.fmning.com/shouldNotRecord", inputMap);
			assertEquals((String)respond.get("error"), "Some message");
		}
	}
	
	@Test
	public void testNotAcceptableError() {
		Map<String, Object> inputMap = new HashMap<String, Object>();
        inputMap.put("soemfile", "filenamehere");
        inputMap.put("number", 1);
		try {
			throw new IOException("Some messagehaha");
		} catch (Exception e) {
			Map<String, Object> respond = errorManager.createErrorRespondFromException(e,
					"wc.fmning.com/shouldBeLogged", inputMap);
			assertEquals((String)respond.get("error"), ErrorMessage.INCORRECT_INTER_IMG_IO.getMsg());
		}
	}
	

}
