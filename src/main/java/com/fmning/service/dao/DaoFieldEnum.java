package com.fmning.service.dao;

import com.fmning.service.dao.impl.QueryTerm;
import com.fmning.service.dao.impl.RelationalOpType;

public interface DaoFieldEnum
{
  QueryTerm getQueryTerm(Object value);
  
  QueryTerm getQueryTerm(RelationalOpType op, Object value);  
}
