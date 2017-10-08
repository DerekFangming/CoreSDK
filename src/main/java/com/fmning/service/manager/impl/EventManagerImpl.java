package com.fmning.service.manager.impl;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fmning.service.dao.EventDao;
import com.fmning.service.domain.Event;
import com.fmning.service.exceptions.NotFoundException;
import com.fmning.service.manager.EventManager;
import com.fmning.util.ErrorMessage;

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

	@Override
	public Event getEventById(int id) throws NotFoundException {
		try{
			return eventDao.findById(id);
		} catch (NotFoundException e) {
			throw new NotFoundException(ErrorMessage.EVENT_NOT_FOUND.getMsg());
		}
	}

	@Override
	public Event getEventByMappingId(int mappingId) throws NotFoundException {
		try {
			return eventDao.findObject(EventDao.Field.MAPPING_ID.getQueryTerm(mappingId));
		} catch (NotFoundException e) {
			throw new NotFoundException(ErrorMessage.EVENT_NOT_FOUND.getMsg());
		}
	}

}
