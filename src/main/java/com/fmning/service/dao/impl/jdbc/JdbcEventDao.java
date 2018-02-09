package com.fmning.service.dao.impl.jdbc;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.fmning.service.dao.EventDao;
import com.fmning.service.dao.impl.CoreTableType;
import com.fmning.service.dao.impl.NVPairList;
import com.fmning.service.domain.Event;
import com.fmning.util.Util;

@Repository
@Jdbc
public class JdbcEventDao extends JdbcBaseDao<Event> implements EventDao{
	public JdbcEventDao() {
		super(CoreTableType.EVENTS);
	}

	@Override
	protected NVPairList getNVPairs(Event obj) {
		NVPairList params = new NVPairList();

		params.addValue(EventDao.Field.TYPE.name, obj.getType());
		params.addNullableNumValue(EventDao.Field.MAPPING_ID.name, obj.getMappingId());
		params.addValue(EventDao.Field.TITLE.name, obj.getTitle());
		params.addValue(EventDao.Field.DESCRIPTION.name, obj.getDescription());
		params.addValue(EventDao.Field.START_TIME.name, Util.parseDate(obj.getStartTime()));
		params.addValue(EventDao.Field.END_TIME.name, Util.parseDate(obj.getEndTime()));
		params.addValue(EventDao.Field.LOCATION.name, obj.getLocation());
		params.addValue(EventDao.Field.FEE.name, obj.getFee());
		params.addValue(EventDao.Field.OWNER_ID.name, obj.getOwnerId());
		params.addValue(EventDao.Field.CREATED_AT.name, Date.from(obj.getCreatedAt()));
		params.addNullableNumValue(EventDao.Field.TICKET_TEMPLATE_ID.name, obj.getTicketTemplateId());
		params.addValue(EventDao.Field.ACTIVE.name, obj.getActive());
		params.addValue(EventDao.Field.MESSAGE.name, obj.getMessage());
		params.addValue(EventDao.Field.TICKET_BALANCE.name, obj.getTicketBalance());

		return params;
	}

	@Override
	protected RowMapper<Event> getRowMapper() {
		RowMapper<Event> rm = new RowMapper<Event>() {
			@Override
			public Event mapRow(ResultSet rs, int row) throws SQLException {
				Event obj = new Event();

				obj.setId(rs.getInt(EventDao.Field.ID.name));
				obj.setType(rs.getString(EventDao.Field.TYPE.name));
				obj.setMappingId(Util.getNullableInt(rs, EventDao.Field.MAPPING_ID.name));
				obj.setTitle(rs.getString(EventDao.Field.TITLE.name));
				obj.setDescription(rs.getString(EventDao.Field.DESCRIPTION.name));
				obj.setStartTime(Util.parseTimestamp(rs.getTimestamp(EventDao.Field.START_TIME.name)));
				obj.setEndTime(Util.parseTimestamp(rs.getTimestamp(EventDao.Field.END_TIME.name)));
				obj.setLocation(rs.getString(EventDao.Field.LOCATION.name));
				obj.setFee(Util.getNullableDouble(rs, EventDao.Field.FEE.name));
				obj.setOwnerId(rs.getInt(EventDao.Field.OWNER_ID.name));
				obj.setCreatedAt(rs.getTimestamp(EventDao.Field.CREATED_AT.name).toInstant());
				obj.setTicketTemplateId(Util.getNullableInt(rs, EventDao.Field.TICKET_TEMPLATE_ID.name));
				obj.setActive(rs.getBoolean(EventDao.Field.ACTIVE.name));
				obj.setMessage(rs.getString(EventDao.Field.MESSAGE.name));
				obj.setTicketBalance(rs.getInt(EventDao.Field.TICKET_BALANCE.name));

				return obj;
			}
		};
		return rm;
	}

}