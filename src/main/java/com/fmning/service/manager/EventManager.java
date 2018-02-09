package com.fmning.service.manager;

import java.time.Instant;
import java.util.List;

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
	 * @param ticketTemplaceId
	 * @param active
	 * @param message
	 * @param ticketBalance
	 * @return the database id of the row
	 */
	public int createEvent(String type, int mappingId, String title, String description, Instant startTime,
			Instant endTime, String location, double fee, int ownerId, int ticketTemplateId, boolean active,
			String message, int ticketBalance);
	
	/**
	 * Update an event by id.
	 * If any column is null, skip the update
	 * @param id
	 * @param type
	 * @param mappingId
	 * @param title
	 * @param description
	 * @param startTime
	 * @param endTime
	 * @param location
	 * @param fee
	 * @throws NotFoundException
	 */
	public void updateEventDetails(int id, String title, String description,
			Instant startTime, Instant endTime, String location, int fee) throws NotFoundException;
	
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
	 * Get a list of most recent events by provided date
	 * @param date the date for the most recent line
	 * @param limit the maximum of event that will be returned at once
	 * @return a list of events that meet the criteria
	 * @throws NotFoundException if no event meets the criteria
	 */
	public List<Event> getRecentEventByDate(Instant date, int limit) throws NotFoundException;

	/**
	 * Update the ticket balance of a event
	 * @param id the database id of the event
	 * @param balance the new ticket balance for the event
	 * @throws NotFoundException
	 */
	public void setBalance(int id, int balance) throws NotFoundException;
	
	/**
	 * Update the ticket active status of a event
	 * If set to active, message will be set to empty string and the input is ignored
	 * @param id the database id of the event
	 * @param active the active status of the ticket
	 * @param message the message for the reason of an inactive ticket
	 * @throws NotFoundException
	 */
	public void setStatus(int id, boolean active, String message) throws NotFoundException;
	
}
