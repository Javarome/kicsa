package org.orange.kicsa.integration;

import javax.jdo.PersistenceManager;
import javax.resource.cci.Connection;
import org.orange.util.integration.DAOException;

public interface JDOContext {
  PersistenceManager getPersistenceManager() throws DAOException;

  Connection getConnection() throws DAOException;

  String getProperty(String var1);
}
