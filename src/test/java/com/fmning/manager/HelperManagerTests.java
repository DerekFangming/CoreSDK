package com.fmning.manager;

import static org.junit.Assert.*;

import java.time.Duration;
import java.time.Instant;
import java.util.TimeZone;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fmning.service.exceptions.SessionExpiredException;
import com.fmning.service.manager.HelperManager;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="/sdkUnitTesting.xml")
public class HelperManagerTests {

	@Autowired private HelperManager helperManager;
	
	@BeforeClass
    public static void setUpBaseClass() {
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }
	
	@Test
	public void testCompareTime(){
		Instant tomorrow = Instant.now().plus(Duration.ofDays(1));
		try{
			helperManager.checkSessionTimeOut(tomorrow.toString());
			return;
		}catch (SessionExpiredException e){
			fail();
		}
	}
}
