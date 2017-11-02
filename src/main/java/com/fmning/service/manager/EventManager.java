package com.fmning.service.manager;

import java.time.Instant;

import com.fmning.service.domain.Event;
import com.fmning.service.exceptions.NotFoundException;

public interface EventManager {
	
	/**
	 * Create an Event object and save into database
	 * @param type
	 * @param mappingId
	 * @param title
	 * @param description
	 * @param startTime
	 * @param endTime
	 * @param location
	 * @param fee
	 * @param ownerId
	 * @return the database id of the row
	 */
	public int createEvent(String type, int mappingId, String title, String description, Instant startTime,
			Instant endTime, String location, int fee, int ownerId);
	
	/**
	 * Get event by database id
	 * @param id database id of the row
	 * @return Event object
	 * @throws NotFoundException if such event does not exist
	 */
	public Event getEventById(int id) throws NotFoundException;
	
	/**
	 * Get event by type and mapping id
	 * @param type the type
	 * @param mappingId the id for the type
	 * @return Event object
	 * @throws NotFoundException
	 */
	public Event getEventByType(String type, int mappingId) throws NotFoundException;

	/**
	 * Update the ticket balance of a event
	 * @param id the database id of the event
	 * @param balance the new ticket balance for the event
	 * @throws NotFoundException
	 */
	public void setBalance(int id, int balance) throws NotFoundException;
}
