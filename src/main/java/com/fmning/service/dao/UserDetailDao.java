package com.fmning.service.dao;

import java.util.Arrays;
import java.util.List;

import com.fmning.service.dao.impl.QueryTerm;
import com.fmning.service.dao.impl.RelationalOpType;
import com.fmning.service.domain.UserDetail;
import com.fmning.util.Pair;

public interface UserDetailDao extends CommonDao<UserDetail>{
	enum Field implements DaoFieldEnum{
		ID(true),
	    USER_ID,
	    NAME,
	    NICKNAME,
	    AGE,
	    GENDER,
	    LOCATION,
	    WHATS_UP,
	    BIRTHDAY,
	    YEAR,
	    MAJOR;
		
		public boolean isPK = false;
	    public String name;
	  
	    Field(boolean isPK)
	    {
	      this.isPK = isPK;
	      this.name = this.name().toLowerCase();
	    }
	  
	    Field()
	    {
	      this(false);
	    }
	    
	    @Override
	    public QueryTerm getQueryTerm(Object value)
	    {
	      return new QueryTerm(this.name, value);
	    }

	    @Override
	    public QueryTerm getQueryTerm(RelationalOpType op, Object value)
	    {
	      return new QueryTerm(this.name, op, value);
	    }
	}
	
	List<Pair<Enum<?>, String>> FieldTypes = Arrays.asList(
		    new Pair<Enum<?>, String>(Field.ID, "SERIAL NOT NULL"),
		    new Pair<Enum<?>, String>(Field.USER_ID, "INTEGER NOT NULL"),
		    new Pair<Enum<?>, String>(Field.NAME, "TEXT"),
		    new Pair<Enum<?>, String>(Field.NICKNAME, "TEXT"),
		    new Pair<Enum<?>, String>(Field.AGE, "INTEGER"),
		    new Pair<Enum<?>, String>(Field.GENDER, "TEXT"),
		    new Pair<Enum<?>, String>(Field.LOCATION, "TEXT"),
		    new Pair<Enum<?>, String>(Field.WHATS_UP, "TEXT"),
		    new Pair<Enum<?>, String>(Field.BIRTHDAY, "TEXT"),
		    new Pair<Enum<?>, String>(Field.YEAR, "TEXT"),
		    new Pair<Enum<?>, String>(Field.MAJOR, "TEXT"));

}
