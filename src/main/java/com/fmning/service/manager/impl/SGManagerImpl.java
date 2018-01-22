package com.fmning.service.manager.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fmning.service.dao.SurvivalGuideDao;
import com.fmning.service.dao.impl.CoreTableType;
import com.fmning.service.dao.impl.QueryBuilder;
import com.fmning.service.dao.impl.QueryTerm;
import com.fmning.service.dao.impl.QueryType;
import com.fmning.service.dao.impl.RelationalOpType;
import com.fmning.service.domain.SurvivalGuide;
import com.fmning.service.manager.SGManager;
import com.fmning.service.manager.UserManager;

@Component
public class SGManagerImpl implements SGManager {
	
	@Autowired private SurvivalGuideDao sgDao;
	@Autowired private UserManager userManager;

	@Override
	public List<SurvivalGuide> getMenu() {
		QueryBuilder qb = QueryType.getQueryBuilder(CoreTableType.SURVIVAL_GUIDES, QueryType.FIND);
		qb.addFirstQueryExpression(new QueryTerm(SurvivalGuideDao.Field.ID.name, RelationalOpType.EQ, 85));
		qb.setReturnField("id, title, '' as content, parent_id, position, created_at, owner_id");
		return sgDao.findAllObjects(qb.createQuery());
	}

}
