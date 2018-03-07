package org.orange.util.integration;

import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.orange.util.Assert;
import org.orange.util.NotImplementedException;
import org.orange.util.Preferences;

public class StrutsJDBCConnectionFactory extends JDBCConnectionFactory {
  public static final String DATASOURCE_NAME;
  private static StrutsJDBCConnectionFactory instance;
  private static DataSource dataSource;

  public Connection getConnection() throws SQLException {
    Connection getConnection = dataSource.getConnection();
    return getConnection;
  }

  public StrutsJDBCConnectionFactory(Preferences somePreferences) throws DAOException {
    super(somePreferences);
    String dataSourceName = somePreferences.get(DATASOURCE_NAME, (String)null);
    Assert.notNullArgument(dataSourceName, "Le nom de la datasource ne peut être nul");
    throw new NotImplementedException();
  }

  static {
    DATASOURCE_NAME = (class$org$orange$util$integration$StrutsJDBCConnectionFactory == null ? (class$org$orange$util$integration$StrutsJDBCConnectionFactory = class$("org.orange.util.integration.StrutsJDBCConnectionFactory")) : class$org$orange$util$integration$StrutsJDBCConnectionFactory).getName() + ".dataSourceName";
    instance = null;
  }
}
