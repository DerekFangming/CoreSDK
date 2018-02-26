package com.fmning.service.dao.impl.jdbc;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.fmning.service.dao.EditingQueueDao;
import com.fmning.service.dao.impl.CoreTableType;
import com.fmning.service.dao.impl.NVPairList;
import com.fmning.service.domain.EditingQueue;
import com.fmning.util.Util;

@Repository
@Jdbc
public class JdbcEditingQueueDao extends JdbcBaseDao<EditingQueue> implements EditingQueueDao{
	public JdbcEditingQueueDao() {
		super(CoreTableType.EDITING_QUEUES);
	}

	@Override
	protected NVPairList getNVPairs(EditingQueue obj) {
		NVPairList params = new NVPairList();

		params.addValue(EditingQueueDao.Field.TYPE.name, obj.getType());
		params.addNullableNumValue(EditingQueueDao.Field.MAPPING_ID.name, obj.getMappingId());
		params.addValue(EditingQueueDao.Field.OWNER_ID.name, obj.getOwnerId());
		params.addValue(EditingQueueDao.Field.CREATED_AT.name, Date.from(obj.getCreatedAt()));

		return params;
	}

	@Override
	protected RowMapper<EditingQueue> getRowMapper() {
		RowMapper<EditingQueue> rm = new RowMapper<EditingQueue>() {
			@Override
			public EditingQueue mapRow(ResultSet rs, int row) throws SQLException {
				EditingQueue obj = new EditingQueue();

				obj.setId(rs.getInt(EditingQueueDao.Field.ID.name));
				obj.setType(rs.getString(EditingQueueDao.Field.TYPE.name));
				obj.setMappingId(Util.getNullableInt(rs, EditingQueueDao.Field.MAPPING_ID.name));
				obj.setOwnerId(rs.getInt(EditingQueueDao.Field.OWNER_ID.name));
				obj.setCreatedAt(rs.getTimestamp(EditingQueueDao.Field.CREATED_AT.name).toInstant());

				return obj;
			}
		};
		return rm;
	}

}