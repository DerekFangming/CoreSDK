package com.fmning.service.dao;

import com.fmning.service.dao.impl.QueryTerm;
import com.fmning.service.dao.impl.RelationalOpType;

public interface DaoFieldEnum
{
  public enum OnPersist {
    OPTIONAL, 
    REQUIRED;
  }
  
  default 
  OnPersist getOnPersist()
  {
    return OnPersist.REQUIRED;
  }
  
  QueryTerm getQueryTerm(Object value);
  
  QueryTerm getQueryTerm(RelationalOpType op, Object value);  
}
