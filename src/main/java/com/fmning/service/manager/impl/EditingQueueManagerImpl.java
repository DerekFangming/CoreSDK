package com.fmning.service.manager.impl;

import java.sql.Date;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fmning.service.dao.EditingQueueDao;
import com.fmning.service.dao.impl.NVPair;
import com.fmning.service.dao.impl.QueryTerm;
import com.fmning.service.domain.EditingQueue;
import com.fmning.service.exceptions.NotFoundException;
import com.fmning.service.manager.EditingQueueManager;
import com.fmning.util.EditingQueueType;

@Component
public class EditingQueueManagerImpl implements EditingQueueManager{
	
	@Autowired private EditingQueueDao eqDao;

	@Override
	public EditingQueue updateQueue(EditingQueueType type, int typeMappingId, int ownerId) {
		List<QueryTerm> values = new ArrayList<QueryTerm>();
		values.add(EditingQueueDao.Field.TYPE.getQueryTerm(type.getName()));
		values.add(EditingQueueDao.Field.MAPPING_ID.getQueryTerm(typeMappingId));
		try {
			EditingQueue eq = eqDao.findObject(values);
			
			List<NVPair> newValues = new ArrayList<NVPair>();
			newValues.add(new NVPair(EditingQueueDao.Field.OWNER_ID.name, ownerId));
			newValues.add(new NVPair(EditingQueueDao.Field.CREATED_AT.name, Date.from(Instant.now())));
			
			eqDao.update(eq.getId(), newValues);
			return eq;
		} catch (NotFoundException e) {
			EditingQueue eq = new EditingQueue();
			eq.setType(type.getName());
			eq.setMappingId(typeMappingId);
			eq.setOwnerId(ownerId);
			eq.setCreatedAt(Instant.now());
			eqDao.persist(eq);
			return null;
		}
	}

}
