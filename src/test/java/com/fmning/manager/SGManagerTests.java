package com.fmning.manager;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.TimeZone;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fmning.service.domain.SurvivalGuide;
import com.fmning.service.domain.TicketTemplate;
import com.fmning.service.manager.SGManager;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="/sdkUnitTesting.xml")
public class SGManagerTests {
	
	
	@Autowired private SGManager sgManager;
	
	@BeforeClass
    public static void setUpBaseClass() {
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }
	
	
	@Test
	public void testGetTicketTemplate(){
		List<SurvivalGuide> menuList = sgManager.getMenu();
		assertEquals(menuList.size(), 85);
	}
	
}
