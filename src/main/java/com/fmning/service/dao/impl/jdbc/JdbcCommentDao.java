package com.fmning.service.dao.impl.jdbc;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.fmning.service.dao.CommentDao;
import com.fmning.service.dao.impl.CoreTableType;
import com.fmning.service.dao.impl.NVPairList;
import com.fmning.service.domain.Comment;
import com.fmning.util.Util;

@Repository
@Jdbc
public class JdbcCommentDao extends JdbcBaseDao<Comment> implements CommentDao{
	public JdbcCommentDao()
	  {
	    super(CoreTableType.COMMENTS);
	  }
	
	@Override
	  protected NVPairList getNVPairs(Comment obj)
	  {
	    NVPairList params = new NVPairList();
	    params.addValue(CommentDao.Field.BODY.name, obj.getBody());
	    params.addNullableNumValue(CommentDao.Field.MENTIONED_USER_ID.name, obj.getMentionedUserId());
	    params.addValue(CommentDao.Field.OWNER_ID.name, obj.getOwnerId());
	    params.addValue(CommentDao.Field.TYPE.name, obj.getType());
	    params.addNullableNumValue(CommentDao.Field.TYPE_MAPPING_ID.name, obj.getTypeMappingId());
	    params.addValue(CommentDao.Field.ENABLED.name, obj.getEnabled());
	    params.addValue(CommentDao.Field.CREATED_AT.name, Date.from(obj.getCreatedAt()));
	        
	    return params;
	  }

	  @Override
	  protected RowMapper<Comment> getRowMapper( )
	  {
	    RowMapper<Comment> rm = new RowMapper<Comment>()
	    {
	      @Override
	      public Comment mapRow(ResultSet rs, int row) throws SQLException
	      {
	    	  Comment obj = new Comment();
	    	  obj.setId(rs.getInt(CommentDao.Field.ID.name));
	    	  obj.setBody(rs.getString(CommentDao.Field.BODY.name));
	    	  obj.setMentionedUserId(Util.getNullableInt(rs, CommentDao.Field.MENTIONED_USER_ID.name));
	    	  obj.setType(rs.getString(CommentDao.Field.TYPE.name));
	    	  obj.setTypeMappingId(Util.getNullableInt(rs, CommentDao.Field.TYPE_MAPPING_ID.name));
	    	  obj.setOwnerId(rs.getInt(CommentDao.Field.OWNER_ID.name));
	    	  obj.setCreatedAt(rs.getTimestamp(CommentDao.Field.CREATED_AT.name).toInstant());
	    	  obj.setEnabled(rs.getBoolean(CommentDao.Field.ENABLED.name));
	        
	        return obj;
	      }
	    };
	    
	    return rm;
	  }

}
