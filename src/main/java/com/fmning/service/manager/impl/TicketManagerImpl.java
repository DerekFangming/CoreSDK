package com.fmning.service.manager.impl;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fmning.service.dao.TicketDao;
import com.fmning.service.dao.TicketTemplateDao;
import com.fmning.service.dao.impl.NVPair;
import com.fmning.service.dao.impl.QueryTerm;
import com.fmning.service.domain.Ticket;
import com.fmning.service.domain.TicketTemplate;
import com.fmning.service.exceptions.NotFoundException;
import com.fmning.service.manager.TicketManager;
import com.fmning.util.ErrorMessage;

@Component
public class TicketManagerImpl implements TicketManager{
	
	@Autowired private TicketDao ticketDao;
	@Autowired private TicketTemplateDao ticketTemplateDao;

	@Override
	public int createTicketTemplate(String location, String description, String logoText, String bgColor,
			int ownerId) {
		TicketTemplate ticketTemplate = new TicketTemplate();
		ticketTemplate.setLocation(location);
		ticketTemplate.setSerialNumber(0);
		ticketTemplate.setDescription(description);
		ticketTemplate.setLogoText(logoText);
		ticketTemplate.setBgColor(bgColor);
		ticketTemplate.setOwnerId(ownerId);
		ticketTemplate.setCreatedAt(Instant.now());
		int dbId = ticketTemplateDao.persist(ticketTemplate);
		
		NVPair pair = new NVPair(TicketTemplateDao.Field.SERIAL_NUMBER.name, 1000000000 + dbId);
		ticketTemplateDao.update(dbId, pair);
		
		return dbId;
	}

	@Override
	public TicketTemplate getTicketTemplateById(int id) throws NotFoundException {
		try{
			return ticketTemplateDao.findObject(TicketTemplateDao.Field.ID.getQueryTerm(id));
		}catch(NotFoundException e){
			throw new NotFoundException(ErrorMessage.TICKET_TEMPLATE_NOT_FOUND.getMsg());
		}
	}

	@Override
	public int createTicket(int templateId, String type, int mappingId, String location, int ownerId) {
		Ticket ticket = new Ticket();
		ticket.setTemplateId(templateId);
		ticket.setType(type);
		ticket.setMappingId(mappingId);
		ticket.setLocation(location);
		ticket.setOwnerId(ownerId);
		ticket.setCreatedAt(Instant.now());
		return ticketDao.persist(ticket);
	}

	@Override
	public Ticket getTicketById(int id) throws NotFoundException {
		try{
			return ticketDao.findObject(TicketDao.Field.ID.getQueryTerm(id));
		}catch(NotFoundException e){
			throw new NotFoundException(ErrorMessage.TICKET_NOT_FOUND.getMsg());
		}
	}

	@Override
	public Ticket getTicketByType(String type, int mappingId) throws NotFoundException {
		try {
			List<QueryTerm> values = new ArrayList<QueryTerm>();
			values.add(TicketDao.Field.TYPE.getQueryTerm(type));
			values.add(TicketDao.Field.MAPPING_ID.getQueryTerm(mappingId));
			
			return ticketDao.findObject(values);
		}catch(NotFoundException e) {
			throw new NotFoundException(ErrorMessage.TICKET_NOT_FOUND.getMsg());
		}
	}

}
