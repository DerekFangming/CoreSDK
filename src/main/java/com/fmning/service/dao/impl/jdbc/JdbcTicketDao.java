package com.fmning.service.dao.impl.jdbc;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.fmning.service.dao.TicketDao;
import com.fmning.service.dao.impl.CoreTableType;
import com.fmning.service.dao.impl.NVPairList;
import com.fmning.service.domain.Ticket;
import com.fmning.util.Util;

@Repository
@Jdbc
public class JdbcTicketDao extends JdbcBaseDao<Ticket> implements TicketDao{
	public JdbcTicketDao() {
		super(CoreTableType.TICKETS);
	}

	@Override
	protected NVPairList getNVPairs(Ticket obj) {
		NVPairList params = new NVPairList();

		params.addValue(TicketDao.Field.TEMPLATE_ID.name, obj.getTemplateId());
		params.addValue(TicketDao.Field.TYPE.name, obj.getType());
		params.addNullableNumValue(TicketDao.Field.MAPPING_ID.name, obj.getMappingId());
		params.addValue(TicketDao.Field.LOCATION.name, obj.getLocation());
		params.addValue(TicketDao.Field.OWNER_ID.name, obj.getOwnerId());
		params.addValue(TicketDao.Field.CREATED_AT.name, Date.from(obj.getCreatedAt()));

		return params;
	}

	@Override
	protected RowMapper<Ticket> getRowMapper() {
		RowMapper<Ticket> rm = new RowMapper<Ticket>() {
			@Override
			public Ticket mapRow(ResultSet rs, int row) throws SQLException {
				Ticket obj = new Ticket();

				obj.setId(rs.getInt(TicketDao.Field.ID.name));
				obj.setTemplateId(rs.getInt(TicketDao.Field.TEMPLATE_ID.name));
				obj.setType(rs.getString(TicketDao.Field.TYPE.name));
				obj.setMappingId(Util.getNullableInt(rs, TicketDao.Field.MAPPING_ID.name));
				obj.setLocation(rs.getString(TicketDao.Field.LOCATION.name));
				obj.setOwnerId(rs.getInt(TicketDao.Field.OWNER_ID.name));
				obj.setCreatedAt(rs.getTimestamp(TicketDao.Field.CREATED_AT.name).toInstant());

				return obj;
			}
		};
		return rm;
	}

}