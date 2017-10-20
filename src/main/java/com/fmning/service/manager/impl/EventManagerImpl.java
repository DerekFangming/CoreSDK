package com.fmning.service.manager.impl;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fmning.service.dao.EventDao;
import com.fmning.service.dao.impl.QueryTerm;
import com.fmning.service.domain.Event;
import com.fmning.service.exceptions.NotFoundException;
import com.fmning.service.manager.EventManager;
import com.fmning.util.ErrorMessage;

@Component
public class EventManagerImpl implements EventManager{
	
	@Autowired private EventDao eventDao;

	@Override
	public int createEvent(String type, int mappingId, String title, String description, Instant startTime,
			Instant endTime, String location, int fee, int ownerId) {
		Event event = new Event();
		event.setType(type);
		event.setMappingId(mappingId);
		event.setTitle(title);
		event.setDescription(description);
		event.setStartTime(startTime);
		event.setEndTime(endTime);
		event.setLocation(location);
		event.setFee(fee);
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
	public Event getEventByType(String type, int mappingId) throws NotFoundException {
		try {
			List<QueryTerm> values = new ArrayList<QueryTerm>();
			values.add(EventDao.Field.TYPE.getQueryTerm(type));
			values.add(EventDao.Field.MAPPING_ID.getQueryTerm(mappingId));
			
			return eventDao.findObject(values);
		} catch (NotFoundException e) {
			throw new NotFoundException(ErrorMessage.EVENT_NOT_FOUND.getMsg());
		}
	}

}
