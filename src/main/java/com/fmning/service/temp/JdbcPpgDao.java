package com.fmning.service.temp;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.fmning.service.temp.PpgDao;
import com.fmning.service.dao.impl.CoreTableType;
import com.fmning.service.dao.impl.NVPairList;
import com.fmning.service.dao.impl.jdbc.Jdbc;
import com.fmning.service.dao.impl.jdbc.JdbcBaseDao;
import com.fmning.service.temp.Ppg;
import com.fmning.util.Util;

@Repository
@Jdbc
public class JdbcPpgDao extends JdbcBaseDao<Ppg> implements PpgDao{
	public JdbcPpgDao() {
		super(CoreTableType.PPG);
	}

	@Override
	protected NVPairList getNVPairs(Ppg obj) {
		NVPairList params = new NVPairList();

		params.addNullableNumValue(PpgDao.Field.MID.name, obj.getMid());
		params.addNullableNumValue(PpgDao.Field.IRD.name, obj.getIrd());
		params.addValue(PpgDao.Field.RD.name, obj.getRd());

		return params;
	}

	@Override
	protected RowMapper<Ppg> getRowMapper() {
		RowMapper<Ppg> rm = new RowMapper<Ppg>() {
			@Override
			public Ppg mapRow(ResultSet rs, int row) throws SQLException {
				Ppg obj = new Ppg();

				obj.setId(rs.getInt(PpgDao.Field.ID.name));
				obj.setMid(Util.getNullableInt(rs, PpgDao.Field.MID.name));
				obj.setIrd(Util.getNullableInt(rs, PpgDao.Field.IRD.name));
				obj.setRd(Util.getNullableInt(rs, PpgDao.Field.RD.name));

				return obj;
			}
		};
		return rm;
	}

}
