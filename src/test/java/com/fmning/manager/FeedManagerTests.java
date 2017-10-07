package com.fmning.manager;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fmning.service.domain.Feed;
import com.fmning.service.manager.FeedManager;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="/sdkUnitTesting.xml")
public class FeedManagerTests {
	
	@Autowired private FeedManager feedManager;
	
	@Test
	public void testRegister(){
		Feed feed = feedManager.getFeedById(3);
		assertEquals(feed.getOwnerId(), 4);
	}

}
