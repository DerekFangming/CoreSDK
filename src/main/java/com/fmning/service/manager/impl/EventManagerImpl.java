package com.fmning.service.manager.impl;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fmning.service.dao.EventDao;
import com.fmning.service.domain.Event;
import com.fmning.service.manager.EventManager;

@Component
public class EventManagerImpl implements EventManager{
	
	@Autowired private EventDao eventDao;

	@Override
	public int createEvent(int mappingId, String title, Instant startTime, Instant endTime, String location,
			int ownerId) {
		Event event = new Event();
		event.setMappingId(mappingId);
		event.setTitle(title);
		event.setStartTime(startTime);
		event.setEndTime(endTime);
		event.setLocation(location);
		event.setOwnerId(ownerId);
		event.setCreatedAt(Instant.now());
		return eventDao.persist(event);
	}

}
