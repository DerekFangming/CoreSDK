package com.fmning.service.dao.impl.jdbc;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.fmning.service.dao.WcReportDao;
import com.fmning.service.dao.impl.CoreTableType;
import com.fmning.service.dao.impl.NVPairList;
import com.fmning.service.domain.WcReport;
import com.fmning.util.Util;

@Repository
@Jdbc
public class JdbcWcReportDao extends JdbcBaseDao<WcReport> implements WcReportDao{
	public JdbcWcReportDao()
	  {
	    super(CoreTableType.WC_REPORTS);
	  }
	
	@Override
	  protected NVPairList getNVPairs(WcReport obj)
	  {
	    NVPairList params = new NVPairList();
	    params.addValue(WcReportDao.Field.MENU_ID.name, obj.getMenuId());
	    params.addNullableNumValue(WcReportDao.Field.USER_ID.name, obj.getUserId());
	    params.addValue(WcReportDao.Field.EMAIL.name, obj.getEmail());
	    params.addValue(WcReportDao.Field.REPORT.name, obj.getReport());
	    params.addValue(WcReportDao.Field.CREATED_AT.name, Date.from(obj.getCreatedAt()));
	        
	    return params;
	  }

	  @Override
	  protected RowMapper<WcReport> getRowMapper( )
	  {
	    RowMapper<WcReport> rm = new RowMapper<WcReport>()
	    {
	      @Override
	      public WcReport mapRow(ResultSet rs, int row) throws SQLException
	      {
	    	  WcReport obj = new WcReport();
	    	  obj.setId(rs.getInt(WcReportDao.Field.ID.name));
	    	  obj.setUserId(Util.getNullableInt(rs, WcReportDao.Field.USER_ID.name));
	    	  obj.setMenuId(rs.getInt(WcReportDao.Field.MENU_ID.name));
	    	  obj.setEmail(rs.getString(WcReportDao.Field.EMAIL.name));
	    	  obj.setReport(rs.getString(WcReportDao.Field.REPORT.name));
	    	  obj.setCreatedAt(rs.getTimestamp(WcReportDao.Field.CREATED_AT.name).toInstant());
	        
	        return obj;
	      }
	    };
	    
	    return rm;
	  }

}
