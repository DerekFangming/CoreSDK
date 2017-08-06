package com.fmning.service;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class ApplicationContextProvider implements ApplicationContextAware
{
  private static ApplicationContext context;

  public static ApplicationContext getApplicationContext( )
  {
    return context;
  }

  @Override
  public void setApplicationContext(ApplicationContext ac)
    throws BeansException
  {
    context = ac;
  }
  
  /**
   * Returns the Spring-managed instance for the specified class.
   * <p>
   * Usage example: <code>SomeManager sm = (SomeManager) ApplicationContextProvider.getManagedBean(SomeManager.class);</code>
   * <p>
   * That is equivalent to:
   * <code>
   * SomeManager sm = ApplicationContextProvider.getContext.getBean(SomeManager.class);
   * </code>
   * @param clazz Interface for which you want the concrete implementation
   *   
   * @return The concrete implementation of the specified interface
   */
  public static Object getManagedBean(Class<?> clazz)
  {
    return context.getBean(clazz);
  }
}
