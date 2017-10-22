package com.fmning.service.dao.impl.jdbc;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.fmning.service.dao.TicketTemplateDao;
import com.fmning.service.dao.impl.CoreTableType;
import com.fmning.service.dao.impl.NVPairList;
import com.fmning.service.domain.TicketTemplate;

@Repository
@Jdbc
public class JdbcTicketTemplateDao extends JdbcBaseDao<TicketTemplate> implements TicketTemplateDao{
	public JdbcTicketTemplateDao()
	  {
	    super(CoreTableType.TICKET_TEMPLATES);
	  }
	
	@Override
	  protected NVPairList getNVPairs(TicketTemplate obj)
	  {
	    NVPairList params = new NVPairList();
	    
		params.addValue(TicketTemplateDao.Field.LOCATION.name, obj.getLocation());
		params.addValue(TicketTemplateDao.Field.SERIAL_NUMBER.name, obj.getSerialNumber());
		params.addValue(TicketTemplateDao.Field.DESCRIPTION.name, obj.getDescription());
	    params.addValue(TicketTemplateDao.Field.LOGO_TEXT.name, obj.getLogoText());
	    params.addValue(TicketTemplateDao.Field.BG_COLOR.name, obj.getBgColor());
	    params.addValue(TicketTemplateDao.Field.OWNER_ID.name, obj.getOwnerId());
	    params.addValue(TicketTemplateDao.Field.CREATED_AT.name, Date.from(obj.getCreatedAt()));
	        
	    return params;
	  }

	  @Override
	  protected RowMapper<TicketTemplate> getRowMapper( )
	  {
	    RowMapper<TicketTemplate> rm = new RowMapper<TicketTemplate>()
	    {
	      @Override
	      public TicketTemplate mapRow(ResultSet rs, int row) throws SQLException
	      {
	    	  TicketTemplate obj = new TicketTemplate();
	    	  obj.setId(rs.getInt(TicketTemplateDao.Field.ID.name));
	    	  obj.setLocation(rs.getString(TicketTemplateDao.Field.LOCATION.name));
	    	  obj.setSerialNumber(rs.getInt(TicketTemplateDao.Field.SERIAL_NUMBER.name));
	    	  obj.setDescription(rs.getString(TicketTemplateDao.Field.DESCRIPTION.name));
	    	  obj.setLogoText(rs.getString(TicketTemplateDao.Field.LOGO_TEXT.name));
	    	  obj.setBgColor(rs.getString(TicketTemplateDao.Field.BG_COLOR.name));
	    	  obj.setOwnerId(rs.getInt(TicketTemplateDao.Field.OWNER_ID.name));
	    	  obj.setCreatedAt(rs.getTimestamp(TicketTemplateDao.Field.CREATED_AT.name).toInstant());
	        
	        return obj;
	      }
	    };
	    
	    return rm;
	  }

}
