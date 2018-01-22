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
import com.fmning.service.manager.SGManager;
import com.fmning.util.Util;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="/sdkUnitTesting.xml")
public class SGManagerTests {
	
	
	@Autowired private SGManager sgManager;
	
	@BeforeClass
    public static void setUpBaseClass() {
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }
	
	@Test
	public void testGetArticle(){
		SurvivalGuide sg = sgManager.getArticleById(1);
		assertEquals(sg.getTitle(), "关于我们");
		assertEquals(sg.getPosition(), 0);
	}
	
	@Test
	public void testGetChildArticles(){
		List<SurvivalGuide> menuList = sgManager.getChildArticles(9999);
		assertEquals(menuList.size(), 0);
		
		menuList = sgManager.getChildArticles(Util.nullInt);
		assertEquals(menuList.size(), 11);
	}
	
}
