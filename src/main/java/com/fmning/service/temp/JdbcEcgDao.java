package com.fmning.service.temp;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.fmning.service.temp.EcgDao;
import com.fmning.service.dao.impl.CoreTableType;
import com.fmning.service.dao.impl.NVPairList;
import com.fmning.service.dao.impl.jdbc.Jdbc;
import com.fmning.service.dao.impl.jdbc.JdbcBaseDao;
import com.fmning.service.temp.Ecg;
import com.fmning.util.Util;

@Repository
@Jdbc
public class JdbcEcgDao extends JdbcBaseDao<Ecg> implements EcgDao{
	public JdbcEcgDao() {
		super(CoreTableType.ECG);
	}

	@Override
	protected NVPairList getNVPairs(Ecg obj) {
		NVPairList params = new NVPairList();

		params.addNullableNumValue(EcgDao.Field.MID.name, obj.getMid());
		params.addValue(EcgDao.Field.ED.name, obj.getEd());

		return params;
	}

	@Override
	protected RowMapper<Ecg> getRowMapper() {
		RowMapper<Ecg> rm = new RowMapper<Ecg>() {
			@Override
			public Ecg mapRow(ResultSet rs, int row) throws SQLException {
				Ecg obj = new Ecg();

				obj.setId(rs.getInt(EcgDao.Field.ID.name));
				obj.setMid(Util.getNullableInt(rs, EcgDao.Field.MID.name));
				obj.setEd(Util.getNullableInt(rs, EcgDao.Field.ED.name));

				return obj;
			}
		};
		return rm;
	}

}
