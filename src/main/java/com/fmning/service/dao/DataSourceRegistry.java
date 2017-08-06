package com.fmning.service.dao;

import com.fmning.service.dao.impl.SdkDataSourceImpl;

public interface DataSourceRegistry 
{
   int getDatabaseCount( );
  
   SdkDataSource putDataSource(SdkDataSource ds);

   SdkDataSource getDataSource(String nickname);

   String getDbName(SdkDataSourceImpl ds);   
}
