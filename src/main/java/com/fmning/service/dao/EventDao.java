package com.fmning.service.dao;

import java.util.Arrays;
import java.util.List;

import com.fmning.service.dao.impl.QueryTerm;
import com.fmning.service.dao.impl.RelationalOpType;
import com.fmning.service.domain.Event;
import com.fmning.util.Pair;

public interface EventDao extends CommonDao<Event>{
	enum Field implements DaoFieldEnum{
		ID(true),
		TYPE,
		MAPPING_ID,
		TITLE,
		DESCRIPTION,
		START_TIME,
		END_TIME,
		LOCATION,
		FEE,
		OWNER_ID,
		CREATED_AT,
		TICKET_TEMPLATE_ID,
		ACTIVE,
		MESSAGE,
		TICKET_BALANCE;

		public boolean isPK = false;
		public String name;

		Field(boolean isPK) {
			this.isPK = isPK;
			this.name = this.name().toLowerCase();
		}

		Field() {
			this(false);
		}

		@Override
		public QueryTerm getQueryTerm(Object value) {
			return new QueryTerm(this.name, value);
		}

		@Override
		public QueryTerm getQueryTerm(RelationalOpType op, Object value) {
			return new QueryTerm(this.name, op, value);
		}
	}

	List<Pair<Enum<?>, String>> FieldTypes = Arrays.asList(
		new Pair<Enum<?>, String>(Field.ID, "SERIAL NOT NULL"),
		new Pair<Enum<?>, String>(Field.TYPE, "TEXT"),
		new Pair<Enum<?>, String>(Field.MAPPING_ID, "INTEGER"),
		new Pair<Enum<?>, String>(Field.TITLE, "TEXT"),
		new Pair<Enum<?>, String>(Field.DESCRIPTION, "TEXT"),
		new Pair<Enum<?>, String>(Field.START_TIME, "TIMESTAMP WITHOUT TIME ZONE"),
		new Pair<Enum<?>, String>(Field.END_TIME, "TIMESTAMP WITHOUT TIME ZONE"),
		new Pair<Enum<?>, String>(Field.LOCATION, "TEXT"),
		new Pair<Enum<?>, String>(Field.FEE, "DECIMAL"),
		new Pair<Enum<?>, String>(Field.OWNER_ID, "INTEGER NOT NULL"),
		new Pair<Enum<?>, String>(Field.CREATED_AT, "TIMESTAMP WITHOUT TIME ZONE NOT NULL"),
		new Pair<Enum<?>, String>(Field.TICKET_TEMPLATE_ID, "INTEGER"),
		new Pair<Enum<?>, String>(Field.ACTIVE, "BOOLEAN NOT NULL DEFAULT TRUE"),
		new Pair<Enum<?>, String>(Field.MESSAGE, "TEXT"),
		new Pair<Enum<?>, String>(Field.TICKET_BALANCE, "INTEGER NOT NULL DEFAULT 0"));

}
