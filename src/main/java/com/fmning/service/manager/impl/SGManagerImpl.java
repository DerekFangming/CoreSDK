package com.fmning.service.manager.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fmning.service.dao.SurvivalGuideDao;
import com.fmning.service.dao.impl.CoreTableType;
import com.fmning.service.dao.impl.LogicalOpType;
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
	public SurvivalGuide getArticleById(int sgId) throws NotFoundException {
		try{
			return sgDao.findObject(SurvivalGuideDao.Field.ID.getQueryTerm(sgId));
		}catch(NotFoundException e){
			throw new NotFoundException(ErrorMessage.SURVIVAL_GUIDE_NOT_FOUND.getMsg());
		}
	}

	@Override
	public List<SurvivalGuide> getChildArticles(int parentId) {
		QueryBuilder qb = QueryType.getQueryBuilder(CoreTableType.SURVIVAL_GUIDES, QueryType.FIND);
		
		if (parentId == Util.nullInt) {
			qb.addFirstQueryExpression(new QueryTerm(SurvivalGuideDao.Field.PARENT_ID.name,
					RelationalOpType.ISNULL, null));
		} else {
			qb.addFirstQueryExpression(new QueryTerm(SurvivalGuideDao.Field.PARENT_ID.name,
					RelationalOpType.EQ, parentId));
		}
		qb.setReturnField("id, title, '' as content, parent_id, position, created_at, owner_id");
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
