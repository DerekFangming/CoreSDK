package com.fmning.service.temp;

import java.util.Arrays;
import java.util.List;

import com.fmning.service.dao.CommonDao;
import com.fmning.service.dao.DaoFieldEnum;
import com.fmning.service.dao.impl.QueryTerm;
import com.fmning.service.dao.impl.RelationalOpType;
import com.fmning.service.temp.Cmain;
import com.fmning.util.Pair;

public interface CmainDao extends CommonDao<Cmain>{
	enum Field implements DaoFieldEnum{
		ID(true),
		EHR,
		PHR,
		TEMP,
		SPO2,
		CREATED_AT;

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
		new Pair<Enum<?>, String>(Field.EHR, "INTEGER"),
		new Pair<Enum<?>, String>(Field.PHR, "INTEGER"),
		new Pair<Enum<?>, String>(Field.TEMP, "DECIMAL"),
		new Pair<Enum<?>, String>(Field.SPO2, "INTEGER"),
		new Pair<Enum<?>, String>(Field.CREATED_AT, "TIMESTAMP WITHOUT TIME ZONE NOT NULL"));

}

