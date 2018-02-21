package com.fmning.service.dao.impl.jdbc;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.fmning.service.dao.SurvivalGuideHistDao;
import com.fmning.service.dao.impl.CoreTableType;
import com.fmning.service.dao.impl.NVPairList;
import com.fmning.service.domain.SurvivalGuideHist;
import com.fmning.util.Util;

@Repository
@Jdbc
public class JdbcSurvivalGuideHistDao extends JdbcBaseDao<SurvivalGuideHist> implements SurvivalGuideHistDao{
	public JdbcSurvivalGuideHistDao() {
		super(CoreTableType.SURVIVAL_GUIDE_HISTS);
	}

	@Override
	protected NVPairList getNVPairs(SurvivalGuideHist obj) {
		NVPairList params = new NVPairList();

		params.addNullableNumValue(SurvivalGuideHistDao.Field.OID.name, obj.getOid());
		params.addValue(SurvivalGuideHistDao.Field.TITLE.name, obj.getTitle());
		params.addValue(SurvivalGuideHistDao.Field.CONTENT.name, obj.getContent());
		params.addNullableNumValue(SurvivalGuideHistDao.Field.PARENT_ID.name, obj.getParentId());
		params.addValue(SurvivalGuideHistDao.Field.POSITION.name, obj.getPosition());
		params.addValue(SurvivalGuideHistDao.Field.CREATED_AT.name, Date.from(obj.getCreatedAt()));
		params.addValue(SurvivalGuideHistDao.Field.OWNER_ID.name, obj.getOwnerId());
		params.addValue(SurvivalGuideHistDao.Field.ACTION.name, obj.getAction());
		params.addValue(SurvivalGuideHistDao.Field.ACTION_DATE.name, Date.from(obj.getActionDate()));

		return params;
	}

	@Override
	protected RowMapper<SurvivalGuideHist> getRowMapper() {
		RowMapper<SurvivalGuideHist> rm = new RowMapper<SurvivalGuideHist>() {
			@Override
			public SurvivalGuideHist mapRow(ResultSet rs, int row) throws SQLException {
				SurvivalGuideHist obj = new SurvivalGuideHist();

				obj.setId(rs.getInt(SurvivalGuideHistDao.Field.ID.name));
				obj.setOid(Util.getNullableInt(rs, SurvivalGuideHistDao.Field.OID.name));
				obj.setTitle(rs.getString(SurvivalGuideHistDao.Field.TITLE.name));
				obj.setContent(rs.getString(SurvivalGuideHistDao.Field.CONTENT.name));
				obj.setParentId(Util.getNullableInt(rs, SurvivalGuideHistDao.Field.PARENT_ID.name));
				obj.setPosition(rs.getInt(SurvivalGuideHistDao.Field.POSITION.name));
				obj.setCreatedAt(rs.getTimestamp(SurvivalGuideHistDao.Field.CREATED_AT.name).toInstant());
				obj.setOwnerId(rs.getInt(SurvivalGuideHistDao.Field.OWNER_ID.name));
				obj.setAction(rs.getString(SurvivalGuideHistDao.Field.ACTION.name));
				obj.setActionDate(rs.getTimestamp(SurvivalGuideHistDao.Field.ACTION_DATE.name).toInstant());

				return obj;
			}
		};
		return rm;
	}

}