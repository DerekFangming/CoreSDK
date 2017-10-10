package com.fmning.manager;

import static org.junit.Assert.assertEquals;
import java.util.TimeZone;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fmning.service.domain.Event;
import com.fmning.service.manager.EventManager;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="/sdkUnitTesting.xml")
public class EventManagerTests {
	
	@Autowired private EventManager eventManager;
	
	@BeforeClass
    public static void setUpBaseClass() {
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }
	
	@Test
	public void testGetEventById(){
		Event event = eventManager.getEventById(1);
		assertEquals(event.getMappingId(), 9);
		assertEquals(event.getTitle(), "Dragon night 2018");
	}
	
	@Test
	public void testGetEventByMapping(){
		Event event = eventManager.getEventByMappingId(9);
		assertEquals(event.getId(), 1);
		assertEquals(event.getTitle(), "Dragon night 2018");
	}

}
