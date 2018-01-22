package com.fmning.service.dao.impl.jdbc;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.fmning.service.dao.SurvivalGuideDao;
import com.fmning.service.dao.impl.CoreTableType;
import com.fmning.service.dao.impl.NVPairList;
import com.fmning.service.domain.SurvivalGuide;
import com.fmning.util.Util;

@Repository
@Jdbc
public class JdbcSurvivalGuideDao extends JdbcBaseDao<SurvivalGuide> implements SurvivalGuideDao{
	public JdbcSurvivalGuideDao() {
		super(CoreTableType.SURVIVAL_GUIDES);
	}

	@Override
	protected NVPairList getNVPairs(SurvivalGuide obj) {
		NVPairList params = new NVPairList();

		params.addValue(SurvivalGuideDao.Field.TITLE.name, obj.getTitle());
		params.addValue(SurvivalGuideDao.Field.CONTENT.name, obj.getContent());
		params.addNullableNumValue(SurvivalGuideDao.Field.PARENT_ID.name, obj.getParentId());
		params.addValue(SurvivalGuideDao.Field.POSITION.name, obj.getPosition());
		params.addValue(SurvivalGuideDao.Field.CREATED_AT.name, Date.from(obj.getCreatedAt()));
		params.addValue(SurvivalGuideDao.Field.OWNER_ID.name, obj.getOwnerId());

		return params;
	}

	@Override
	protected RowMapper<SurvivalGuide> getRowMapper() {
		RowMapper<SurvivalGuide> rm = new RowMapper<SurvivalGuide>() {
			@Override
			public SurvivalGuide mapRow(ResultSet rs, int row) throws SQLException {
				SurvivalGuide obj = new SurvivalGuide();

				obj.setId(rs.getInt(SurvivalGuideDao.Field.ID.name));
				obj.setTitle(rs.getString(SurvivalGuideDao.Field.TITLE.name));
				obj.setContent(rs.getString(SurvivalGuideDao.Field.CONTENT.name));
				obj.setParentId(Util.getNullableInt(rs, SurvivalGuideDao.Field.PARENT_ID.name));
				obj.setPosition(rs.getInt(SurvivalGuideDao.Field.POSITION.name));
				obj.setCreatedAt(rs.getTimestamp(SurvivalGuideDao.Field.CREATED_AT.name).toInstant());
				obj.setOwnerId(rs.getInt(SurvivalGuideDao.Field.OWNER_ID.name));

				return obj;
			}
		};
		return rm;
	}

}