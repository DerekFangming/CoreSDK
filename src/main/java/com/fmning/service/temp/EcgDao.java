package com.fmning.service.temp;

import java.util.Arrays;
import java.util.List;

import com.fmning.service.dao.CommonDao;
import com.fmning.service.dao.DaoFieldEnum;
import com.fmning.service.dao.impl.QueryTerm;
import com.fmning.service.dao.impl.RelationalOpType;
import com.fmning.service.temp.Ecg;
import com.fmning.util.Pair;

public interface EcgDao extends CommonDao<Ecg>{
	enum Field implements DaoFieldEnum{
		ID(true),
		MID,
		ED;

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
		new Pair<Enum<?>, String>(Field.MID, "INTEGER"),
		new Pair<Enum<?>, String>(Field.ED, "INTEGER"));

}

