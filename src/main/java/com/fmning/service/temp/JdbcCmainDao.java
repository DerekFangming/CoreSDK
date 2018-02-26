package com.fmning.service.temp;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.fmning.service.temp.CmainDao;
import com.fmning.service.dao.impl.CoreTableType;
import com.fmning.service.dao.impl.NVPairList;
import com.fmning.service.dao.impl.jdbc.Jdbc;
import com.fmning.service.dao.impl.jdbc.JdbcBaseDao;
import com.fmning.service.temp.Cmain;
import com.fmning.util.Util;

@Repository
@Jdbc
public class JdbcCmainDao extends JdbcBaseDao<Cmain> implements CmainDao{
	public JdbcCmainDao() {
		super(CoreTableType.CMAIN);
	}

	@Override
	protected NVPairList getNVPairs(Cmain obj) {
		NVPairList params = new NVPairList();

		params.addNullableNumValue(CmainDao.Field.EHR.name, obj.getEhr());
		params.addNullableNumValue(CmainDao.Field.PHR.name, obj.getPhr());
		params.addNullableNumValue(CmainDao.Field.TEMP.name, obj.getTemp());
		params.addNullableNumValue(CmainDao.Field.SPO2.name, obj.getSpo2());
		params.addValue(CmainDao.Field.CREATED_AT.name, Date.from(obj.getCreatedAt()));

		return params;
	}

	@Override
	protected RowMapper<Cmain> getRowMapper() {
		RowMapper<Cmain> rm = new RowMapper<Cmain>() {
			@Override
			public Cmain mapRow(ResultSet rs, int row) throws SQLException {
				Cmain obj = new Cmain();

				obj.setId(rs.getInt(CmainDao.Field.ID.name));
				obj.setEhr(Util.getNullableInt(rs, CmainDao.Field.EHR.name));
				obj.setPhr(Util.getNullableInt(rs, CmainDao.Field.PHR.name));
				obj.setTemp(Util.getNullableDouble(rs, CmainDao.Field.TEMP.name));
				obj.setSpo2(Util.getNullableInt(rs, CmainDao.Field.SPO2.name));
				obj.setCreatedAt(rs.getTimestamp(CmainDao.Field.CREATED_AT.name).toInstant());

				return obj;
			}
		};
		return rm;
	}

}
