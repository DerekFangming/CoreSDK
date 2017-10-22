package com.fmning.service.manager;

import com.fmning.service.domain.Ticket;
import com.fmning.service.domain.TicketTemplate;
import com.fmning.service.exceptions.NotFoundException;

public interface TicketManager {
	
	/**
	 * Create ticket template object and save to database
	 * @param location
	 * @param serial the serial number for the ticket
	 * @param description ticket description
	 * @param logoText
	 * @param bgColor background color in strip mode ticket
	 * @param ownerId
	 * @return database id of the new row
	 */
	public int createTicketTemplate(String location, int serial, String description, String logoText,
			String bgColor, int ownerId);
	
	/**
	 * Get ticket template by db id
	 * @param id the row id
	 * @return ticket template object
	 * @throws NotFoundException if the ticket template does not exist
	 */
	public TicketTemplate getTicketTemplateById(int id) throws NotFoundException;

	/**
	 * Create ticket object and save to database
	 * @param templateId link to ticket template
	 * @param type
	 * @param mappingId
	 * @param location the location of the ticket file in file system
	 * @param ownerId
	 * @return database id of the new row
	 */
	public int createTicket(int templateId, String type, int mappingId, String location, int ownerId);
	
	/**
	 * Get ticket by db id
	 * @param id the row id
	 * @return ticket object
	 * @throws NotFoundException if the ticket does not eixts
	 */
	public Ticket getTicketById(int id) throws NotFoundException;
	
	/**
	 * Get ticket by type and mapping id
	 * @param type
	 * @param mappingId mapping id for the type
	 * @return ticket object
	 * @throws NotFoundException if the ticket for given type and mapping id does not exist
	 */
	public Ticket getTicketByType(String type, int mappingId) throws NotFoundException;
}
