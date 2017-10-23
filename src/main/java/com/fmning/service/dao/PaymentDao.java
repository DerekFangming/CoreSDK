package com.fmning.service.dao;

import java.util.Arrays;
import java.util.List;

import com.fmning.service.dao.impl.QueryTerm;
import com.fmning.service.dao.impl.RelationalOpType;
import com.fmning.service.domain.Payment;
import com.fmning.util.Pair;

public interface PaymentDao extends CommonDao<Payment>{
	enum Field implements DaoFieldEnum{
		ID(true),
		TYPE,
		MAPPING_ID,
		AMOUNT,
		STATUS,
		MESSAGE,
		PAYER_ID,
		RECEIVER_ID,
		METHOD,
		NONCE,
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
		    new Pair<Enum<?>, String>(Field.TYPE, "TEXT"),
		    new Pair<Enum<?>, String>(Field.MAPPING_ID, "INTEGER"),
		    new Pair<Enum<?>, String>(Field.AMOUNT, "DECIMAL NOT NULL"),
		    new Pair<Enum<?>, String>(Field.STATUS, "TEXT NOT NULL"),
		    new Pair<Enum<?>, String>(Field.MESSAGE, "TEXT"),
		    new Pair<Enum<?>, String>(Field.PAYER_ID, "INTEGER NOT NULL"),
		    new Pair<Enum<?>, String>(Field.RECEIVER_ID, "INTEGER NOT NULL"),
		    new Pair<Enum<?>, String>(Field.METHOD, "TEXT"),
		    new Pair<Enum<?>, String>(Field.NONCE, "ITEXT"),
		    new Pair<Enum<?>, String>(Field.CREATED_AT, "TIMESTAMP WITHOUT TIME ZONE NOT NULL"));

}
