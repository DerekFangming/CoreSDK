package com.fmning.service.dao;

/**
 * CoreDataSourceType specifies the concrete data sources available through the SDK.  
 */
public enum SdkDataSourceType implements DataSourceType
{
  /**
   * Represents the core ASSISTments Service database available through the SDK.
   */
  CORE,
  
  /**
   * Represents the legacy database used primarily through assistments.org.
   */
  LEGACY,
  
  /**
   * Represents the logging database for recording data such as errors and
   * requests. 
   */
  LOGGING,
  
  /**
   * Represents the content database.
   */
  CONTENT;

  public String getNickname()
  {
    return this.toString();
  }
}
