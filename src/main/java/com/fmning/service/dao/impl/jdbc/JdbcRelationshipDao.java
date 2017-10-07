package com.fmning.service.dao.impl.jdbc;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.fmning.service.dao.RelationshipDao;
import com.fmning.service.dao.impl.CoreTableType;
import com.fmning.service.dao.impl.NVPairList;
import com.fmning.service.domain.Relationship;
import com.fmning.util.Util;

@Repository
@Jdbc
public class JdbcRelationshipDao extends JdbcBaseDao<Relationship> implements RelationshipDao{
	public JdbcRelationshipDao()
	  {
	    super(CoreTableType.RELATIONSHIPS);
	  }
	
	@Override
	  protected NVPairList getNVPairs(Relationship obj)
	  {
	    NVPairList params = new NVPairList();
	    
	    params.addValue(RelationshipDao.Field.SENDER_ID.name, obj.getSenderId());
	    params.addValue(RelationshipDao.Field.RECEIVER_ID.name, obj.getReceiverId());
	    params.addValue(RelationshipDao.Field.CONFIRMED.name, obj.getConfirmed());
		params.addValue(RelationshipDao.Field.TYPE.name, obj.getType());
	    params.addValue(RelationshipDao.Field.CREATED_AT.name, Date.from(obj.getCreatedAt()));
	        
	    return params;
	  }

	  @Override
	  protected RowMapper<Relationship> getRowMapper( )
	  {
	    RowMapper<Relationship> rm = new RowMapper<Relationship>()
	    {
	      @Override
	      public Relationship mapRow(ResultSet rs, int row) throws SQLException
	      {
	    	  Relationship obj = new Relationship();
	    	  obj.setId(rs.getInt(RelationshipDao.Field.ID.name));
	    	  obj.setSenderId(rs.getInt(RelationshipDao.Field.SENDER_ID.name));
	    	  obj.setReceiverId(rs.getInt(RelationshipDao.Field.RECEIVER_ID.name));
	    	  obj.setConfirmed(rs.getBoolean(RelationshipDao.Field.CONFIRMED.name));
			  obj.setType(rs.getString(RelationshipDao.Field.TYPE.name));
	    	  obj.setCreatedAt(Util.parseTimestamp(rs.getTimestamp(RelationshipDao.Field.CREATED_AT.name)));
	        
	        return obj;
	      }
	    };
	    
	    return rm;
	  }

}
