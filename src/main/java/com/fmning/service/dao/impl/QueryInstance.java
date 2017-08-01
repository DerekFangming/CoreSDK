package com.fmning.service.dao.impl;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import com.fmning.util.Pair;

public class QueryInstance extends Pair<String, MapSqlParameterSource>
{

  QueryInstance(String f, MapSqlParameterSource s)
  {
    super(f, s);
  }

  public String getQueryStr()
  {
    return this.getFirst();
  }
  
  public MapSqlParameterSource getParams()
  {
    return this.getSecond();
  }
}
