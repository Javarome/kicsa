package org.orange.util.integration;

import java.sql.Connection;
import java.sql.SQLException;
import org.apache.log4j.Logger;
import org.orange.util.Preferences;

public abstract class JDBCConnectionFactory {
  protected Logger log = Logger.getLogger(this.getClass());

  public JDBCConnectionFactory(Preferences somePreferences) {
  }

  abstract Connection getConnection() throws SQLException;
}
