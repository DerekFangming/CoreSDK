package com.fmning.manager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.time.Instant;
import java.time.Year;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.TimeZone;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fmning.service.domain.Event;
import com.fmning.service.exceptions.NotFoundException;
import com.fmning.service.manager.EventManager;
import com.fmning.util.ErrorMessage;
import com.fmning.util.EventType;

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
		Event event = eventManager.getEventById(2);
		assertEquals(event.getMappingId(), 5);
		assertEquals(event.getTitle(), "Hop Pot Event");
	}
	
	@Test
	public void testNullColumn(){
		Event event = eventManager.getEventById(2);
		assertEquals(event.getMappingId(), 5);
		assertEquals(event.getFee(), 0, 0.001);
		assertEquals(event.getTitle(), "Hop Pot Event");
	}
	
	@Test
	public void testGetEventByMapping(){
		Event event = eventManager.getEventByType(EventType.FEED.getName(), 5);
		assertEquals(event.getId(), 2);
		assertEquals(event.getTitle(), "Hop Pot Event");
	}
	
	@Test
	public void testGetRecentEvents(){
		List<Event> eventList = eventManager.getRecentEventByDate(Instant.now(), 20);
		assertEquals(eventList.size(), 6);
		
		eventList = eventManager.getRecentEventByDate(Instant.now(), 1);
		assertEquals(eventList.size(), 1);
		
		try {
			eventList = eventManager.getRecentEventByDate(ZonedDateTime.now().minusYears(20).toInstant(), 20);
			fail(ErrorMessage.SHOULD_NOT_PASS_ERROR.getMsg());
		} catch (NotFoundException e) {
			assertEquals(e.getMessage(), ErrorMessage.NO_MORE_EVENTS_FOUND.getMsg());
		}
	}
	
	

}
