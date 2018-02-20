package com.fmning.service.manager.impl;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fmning.service.dao.SurvivalGuideDao;
import com.fmning.service.dao.impl.CoreTableType;
import com.fmning.service.dao.impl.LogicalOpType;
import com.fmning.service.dao.impl.NVPair;
import com.fmning.service.dao.impl.QueryBuilder;
import com.fmning.service.dao.impl.QueryTerm;
import com.fmning.service.dao.impl.QueryType;
import com.fmning.service.dao.impl.RelationalOpType;
import com.fmning.service.dao.impl.ResultsOrderType;
import com.fmning.service.domain.SurvivalGuide;
import com.fmning.service.exceptions.NotFoundException;
import com.fmning.service.manager.SGManager;
import com.fmning.util.ErrorMessage;
import com.fmning.util.Util;

@Component
public class SGManagerImpl implements SGManager {
	
	@Autowired private SurvivalGuideDao sgDao;
	
	@Override
	public int createSG(String title, String content, int parentId, int position, int ownerId) {
		SurvivalGuide sg = new SurvivalGuide();
		sg.setTitle(title);
		sg.setContent(content);
		sg.setParentId(parentId);
		sg.setPosition(position);
		sg.setOwnerId(ownerId);
		sg.setCreatedAt(Instant.now());
		return sgDao.persist(sg);
	}
	
	public void softUpdateSG(int sgId, String title, String content, int parentId, int position, int updatedBy) throws NotFoundException {
		SurvivalGuide sg = getArticleById(sgId);
		
		List<NVPair> newValues = new ArrayList<>();
		if(title != null) {
			newValues.add(new NVPair(SurvivalGuideDao.Field.TITLE.name, title));
		}
		if(content != null) {
			newValues.add(new NVPair(SurvivalGuideDao.Field.CONTENT.name, content));
		}
		if(parentId != Util.nullInt) {
			newValues.add(new NVPair(SurvivalGuideDao.Field.PARENT_ID.name, parentId));
		}
		if(position != Util.nullInt) {
			newValues.add(new NVPair(SurvivalGuideDao.Field.POSITION.name, position));
		}
		newValues.add(new NVPair(SurvivalGuideDao.Field.OWNER_ID.name, updatedBy));
		sgDao.update(sg.getId(), newValues);
	}
	
	@Override
	public SurvivalGuide getArticleById(int sgId) throws NotFoundException {
		try{
			return sgDao.findObject(SurvivalGuideDao.Field.ID.getQueryTerm(sgId));
		}catch(NotFoundException e){
			throw new NotFoundException(ErrorMessage.SURVIVAL_GUIDE_NOT_FOUND.getMsg());
		}
	}

	@Override
	public List<SurvivalGuide> getChildArticles(int parentId) {
		return getChildArticles(parentId, false);
	}
	
	public List<SurvivalGuide> getChildArticles(int parentId, boolean returnContent) {
		QueryBuilder qb = QueryType.getQueryBuilder(CoreTableType.SURVIVAL_GUIDES, QueryType.FIND);
		
		if (parentId == Util.nullInt) {
			qb.addFirstQueryExpression(new QueryTerm(SurvivalGuideDao.Field.PARENT_ID.name,
					RelationalOpType.ISNULL, null));
		} else {
			qb.addFirstQueryExpression(new QueryTerm(SurvivalGuideDao.Field.PARENT_ID.name,
					RelationalOpType.EQ, parentId));
		}
		if (!returnContent) {
			qb.setReturnField("id, title, '' as content, parent_id, position, created_at, owner_id");
		}
		qb.setOrdering(SurvivalGuideDao.Field.POSITION.name, ResultsOrderType.ASCENDING);
		try {
			return sgDao.findAllObjects(qb.createQuery());
		} catch (NotFoundException e) {
			return new ArrayList<>();
		}
	}
	
	public List<SurvivalGuide> searchArticle(String keyword) throws NotFoundException {
		keyword = "%" + keyword.toUpperCase() + "%";
		QueryBuilder qb = QueryType.getQueryBuilder(CoreTableType.SURVIVAL_GUIDES, QueryType.FIND);
		qb.addFirstQueryExpression(new QueryTerm(SurvivalGuideDao.Field.TITLE.name, RelationalOpType.LIKE, keyword));
		qb.addNextQueryExpression(LogicalOpType.AND,
				new QueryTerm(SurvivalGuideDao.Field.TITLE.name, RelationalOpType.LIKE, keyword));
		qb.addNextQueryExpression(LogicalOpType.AND,
				new QueryTerm(SurvivalGuideDao.Field.CONTENT.name, RelationalOpType.ISNOTNULL, null));
		qb.addNextQueryExpression(LogicalOpType.OR,
				new QueryTerm(SurvivalGuideDao.Field.CONTENT.name, RelationalOpType.LIKE, keyword));
		try {
			return sgDao.findAllObjects(qb.createQuery());
		} catch (NotFoundException e) {
			throw new NotFoundException(ErrorMessage.SURVIVAL_GUIDE_NOT_FOUND.getMsg());
		}
		
	}

}
