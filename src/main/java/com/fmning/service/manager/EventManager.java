package com.fmning.service.manager;

import java.time.Instant;

import com.fmning.service.domain.Event;
import com.fmning.service.exceptions.NotFoundException;

public interface EventManager {
	
	/**
	 * Create an Event object and save into database
	 * @param mappingId
	 * @param title
	 * @param startTime
	 * @param endTime
	 * @param location
	 * @param ownerId
	 * @return the database id of the row
	 */
	public int createEvent(int mappingId, String title, Instant startTime, Instant endTime, String location,
			int ownerId);
	
	/**
	 * Get event by database id
	 * @param id database id of the row
	 * @return Event object
	 * @throws NotFoundException if such event does not exist
	 */
	public Event getEventById(int id) throws NotFoundException;
	
	/**
	 * Get event by mapping id
	 * @param mappingId the id of the feed, which is the only thing that can have event for now
	 * @return Event object
	 * @throws NotFoundException
	 */
	public Event getEventByMappingId(int mappingId) throws NotFoundException;

}
