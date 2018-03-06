package org.orange.kicsa.service.employee;

import org.apache.commons.pool.BasePoolableObjectFactory;

public class EmployeeServiceFactory extends BasePoolableObjectFactory {
  public EmployeeServiceFactory() {
  }

  public Object makeObject() {
    return EmployeeServiceImpl.getInstance();
  }
}
