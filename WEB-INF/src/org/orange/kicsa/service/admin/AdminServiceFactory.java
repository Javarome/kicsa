package org.orange.kicsa.service.admin;

import org.apache.commons.pool.BasePoolableObjectFactory;
import org.orange.util.integration.DAOException;

public class AdminServiceFactory extends BasePoolableObjectFactory {
  public AdminServiceFactory() {
  }

  public Object makeObject() throws DAOException {
    return AdminServiceImpl.getInstance();
  }
}
