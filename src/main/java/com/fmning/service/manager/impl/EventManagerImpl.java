package com.fmning.service.manager.impl;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fmning.service.dao.EventDao;
import com.fmning.service.dao.impl.CoreTableType;
import com.fmning.service.dao.impl.NVPair;
import com.fmning.service.dao.impl.QueryBuilder;
import com.fmning.service.dao.impl.QueryTerm;
import com.fmning.service.dao.impl.QueryType;
import com.fmning.service.dao.impl.RelationalOpType;
import com.fmning.service.dao.impl.ResultsOrderType;
import com.fmning.service.domain.Event;
import com.fmning.service.exceptions.NotFoundException;
import com.fmning.service.manager.EventManager;
import com.fmning.util.ErrorMessage;
import com.fmning.util.Util;

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
	
	public void updateEventDetails(int id, String title, String description,
			Instant startTime, Instant endTime, String location, int fee) throws NotFoundException {
		List<NVPair> newValues = new ArrayList<NVPair>();
		if (title != null)
			newValues.add(new NVPair(EventDao.Field.TITLE.name, title));
		if (description != null)
			newValues.add(new NVPair(EventDao.Field.DESCRIPTION.name, description));
		if (startTime != null)
			newValues.add(new NVPair(EventDao.Field.START_TIME.name, Date.from(startTime)));
		if (endTime != null)
			newValues.add(new NVPair(EventDao.Field.END_TIME.name, Date.from(endTime)));
		if (location != null)
			newValues.add(new NVPair(EventDao.Field.LOCATION.name, location));
		if (fee != Util.nullInt)
			newValues.add(new NVPair(EventDao.Field.FEE.name, fee));
		
		if(newValues.size() > 0) {
			try{
				eventDao.update(id, newValues);
			} catch (NotFoundException e){
				throw new NotFoundException(ErrorMessage.EVENT_NOT_FOUND.getMsg());
			}
		}
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
	
	@Override
	public List<Event> getRecentEventByDate(Instant date, int limit) throws NotFoundException {
		QueryBuilder qb = QueryType.getQueryBuilder(CoreTableType.EVENTS, QueryType.FIND);
	    
	    qb.addFirstQueryExpression(new QueryTerm(EventDao.Field.CREATED_AT.name, RelationalOpType.LT, Date.from(date)));
	    qb.setOrdering(EventDao.Field.CREATED_AT.name, ResultsOrderType.DESCENDING);
	    qb.setLimit(limit);
	    
	    try{
	    		return eventDao.findAllObjects(qb.createQuery());
	    }catch(NotFoundException e){
	    		throw new NotFoundException(ErrorMessage.NO_MORE_EVENTS_FOUND.getMsg());
	    }
	}

	@Override
	public void setBalance(int id, int balance) throws NotFoundException {
		try{
			eventDao.update(id, EventDao.Field.TICKET_BALANCE.getQueryTerm(balance));
		} catch (NotFoundException e){
			throw new NotFoundException(ErrorMessage.EVENT_NOT_FOUND.getMsg());
		}
		
	}

	@Override
	public void setStatus(int id, boolean active, String message) throws NotFoundException {
		List<NVPair> newValues = new ArrayList<NVPair>();
		newValues.add(new NVPair(EventDao.Field.ACTIVE.name, active));
		newValues.add(new NVPair(EventDao.Field.MESSAGE.name, active ? "" : message));
		
		try{
			eventDao.update(id, newValues);
		} catch (NotFoundException e){
			throw new NotFoundException(ErrorMessage.EVENT_NOT_FOUND.getMsg());
		}
	}

}
