package org.orange.util.integration;

import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NameNotFoundException;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.sql.DataSource;
import org.orange.util.Assert;
import org.orange.util.Preferences;

public class J2EEJDBCConnectionFactory extends JDBCConnectionFactory {
  public static final String DATASOURCE_NAME;
  Context namingContext;
  private static DataSource dataSource;
  private ServletContext servletContext;
  private static J2EEJDBCConnectionFactory instance;

  public J2EEJDBCConnectionFactory(Preferences somePreferences) throws DAOException {
    super(somePreferences);
    String dataSourceName = somePreferences.get(DATASOURCE_NAME, (String)null);
    Assert.notNullArgument(dataSourceName, DATASOURCE_NAME + " cannot be null");

    try {
      dataSourceName = "java:comp/env/jdbc/" + dataSourceName;
      InitialContext namingContext = new InitialContext();
      dataSource = (DataSource)namingContext.lookup(dataSourceName);
      super.log.info("DataSource found for " + dataSourceName);
    } catch (NameNotFoundException var5) {
      super.log.error("DataSource not found for " + dataSourceName);
      throw new DAOException("DataSource not found for " + dataSourceName);
    } catch (Exception var6) {
      super.log.error(var6.toString());
      throw new DAOException(var6);
    }
  }

  public Connection getConnection() throws SQLException {
    Connection getConnection = dataSource.getConnection();
    if (super.log.isDebugEnabled()) {
      super.log.debug("Got connection=" + getConnection);
    }

    return getConnection;
  }

  protected Context getInitialContext() throws NamingException {
    if (this.namingContext == null) {
      this.namingContext = new InitialContext();
    }

    return this.namingContext;
  }

  static {
    DATASOURCE_NAME = (class$org$orange$util$integration$J2EEJDBCConnectionFactory == null ? (class$org$orange$util$integration$J2EEJDBCConnectionFactory = class$("org.orange.util.integration.J2EEJDBCConnectionFactory")) : class$org$orange$util$integration$J2EEJDBCConnectionFactory).getName() + ".dataSourceName";
  }
}
