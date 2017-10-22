package com.fmning.manager;

import static org.junit.Assert.assertEquals;

import java.util.TimeZone;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fmning.service.domain.Ticket;
import com.fmning.service.domain.TicketTemplate;
import com.fmning.service.manager.TicketManager;
import com.fmning.util.TicketType;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="/sdkUnitTesting.xml")
public class TicketManagerTests {

	@Autowired private TicketManager ticketManager;
	
	@BeforeClass
    public static void setUpBaseClass() {
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }
	
	@Test
	public void testGetTicketTemplate(){
		TicketTemplate template = ticketManager.getTicketTemplateById(1);
		assertEquals(template.getOwnerId(), 11);
	}
	
	@Test
	public void testGetTicketById(){
		Ticket ticket = ticketManager.getTicketById(1);
		assertEquals(ticket.getOwnerId(), 11);
	}
	
	@Test
	public void testGetTicketByType(){
		Ticket ticket = ticketManager.getTicketByType(TicketType.PAYMENT.getName(), 1);
		assertEquals(ticket.getTemplateId(), 1);
	}
}
