package com.fmning.service.dao;

import java.util.Arrays;
import java.util.List;

import com.fmning.service.dao.impl.QueryTerm;
import com.fmning.service.dao.impl.RelationalOpType;
import com.fmning.service.domain.Ticket;
import com.fmning.util.Pair;

public interface TicketDao extends CommonDao<Ticket>{
	enum Field implements DaoFieldEnum{
		ID(true),
		TEMPLATE_ID,
		TYPE,
	    MAPPING_ID,
	    LOCATION,
	    OWNER_ID,
	    CREATED_AT;
		
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
		    new Pair<Enum<?>, String>(Field.TEMPLATE_ID, "INTEGER NOT NULL"),
		    new Pair<Enum<?>, String>(Field.TYPE, "TEXT"),
		    new Pair<Enum<?>, String>(Field.MAPPING_ID, "INTEGER"),
		    new Pair<Enum<?>, String>(Field.LOCATION, "TEXT NOT NULL"),
		    new Pair<Enum<?>, String>(Field.OWNER_ID, "INTEGER NOT NULL"),
		    new Pair<Enum<?>, String>(Field.CREATED_AT, "TIMESTAMP WITHOUT TIME ZONE NOT NULL"));

}
