package com.fmning.manager;

import static org.junit.Assert.assertEquals;

import java.util.TimeZone;

import org.junit.BeforeClass;
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
	
	@BeforeClass
    public static void setUpBaseClass() {
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }
	
	@Test
	public void testGetFeed(){
		Feed feed = feedManager.getFeedById(3);
		assertEquals(feed.getOwnerId(), 4);
	}
	
	@Test
	public void testGetFeed2(){
		Feed feed = feedManager.getFeedById(4);
		assertEquals(feed.getOwnerId(), 4);
	}

}
