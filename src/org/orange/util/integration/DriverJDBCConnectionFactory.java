package org.orange.util.integration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import org.orange.util.Assert;
import org.orange.util.Preferences;

public class DriverJDBCConnectionFactory extends JDBCConnectionFactory {
  public static final String USER = "user";
  public static final String USER_DEFAULT = "";
  public static final String PASSWORD = "password";
  public static final String PASSWORD_DEFAULT = "";
  public static final String DATABASE_URL;
  public static final String JDBC_DRIVER_CLASS_NAME;
  private Properties properties;
  private String databaseURL;

  public DriverJDBCConnectionFactory(Preferences somePreferences) throws DAOException {
    super(somePreferences);
    String jdbcDriverClassName = somePreferences.get(JDBC_DRIVER_CLASS_NAME, (String)null);
    Assert.notNullArgument(jdbcDriverClassName, JDBC_DRIVER_CLASS_NAME + " cannot be null");

    try {
      super.log.info("Loading " + jdbcDriverClassName);
      Class.forName(jdbcDriverClassName);
    } catch (Exception var5) {
      throw new DAOException(var5);
    }

    this.databaseURL = somePreferences.get(DATABASE_URL, (String)null);
    Assert.notNullArgument(this.databaseURL, DATABASE_URL + " cannot be null");
    super.log.info(DATABASE_URL + "=" + this.databaseURL);
    this.properties = new Properties();
    String[] preferenceKeys = somePreferences.keys();

    for(int i = 0; i < preferenceKeys.length; ++i) {
      this.properties.put(preferenceKeys[i], somePreferences.get(preferenceKeys[i], (String)null));
    }

    super.log.info("JDBC connection properties=" + this.properties);
  }

  public Connection getConnection() throws SQLException {
    Connection getConnection = DriverManager.getConnection(this.databaseURL, this.properties);
    return getConnection;
  }

  static {
    DATABASE_URL = (class$org$orange$util$integration$DriverJDBCConnectionFactory == null ? (class$org$orange$util$integration$DriverJDBCConnectionFactory = class$("org.orange.util.integration.DriverJDBCConnectionFactory")) : class$org$orange$util$integration$DriverJDBCConnectionFactory).getName() + ".databaseURL";
    JDBC_DRIVER_CLASS_NAME = (class$org$orange$util$integration$DriverJDBCConnectionFactory == null ? (class$org$orange$util$integration$DriverJDBCConnectionFactory = class$("org.orange.util.integration.DriverJDBCConnectionFactory")) : class$org$orange$util$integration$DriverJDBCConnectionFactory).getName() + ".jdbcDriverClassName";
  }
}
