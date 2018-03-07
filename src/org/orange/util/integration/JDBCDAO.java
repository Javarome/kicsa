package org.orange.util.integration;

import java.lang.reflect.Constructor;
import java.sql.Connection;
import java.sql.SQLException;
import org.apache.log4j.Logger;
import org.orange.util.Preferences;

public abstract class JDBCDAO {
  public static final String CONNECTION_FACTORY_CLASS_NAME;
  public static final String CONNECTION_FACTORY_CLASS_NAME_DEFAULT;
  public static final String SELECT = "SELECT ";
  public static final String DISTINCT = " DISTINCT ";
  public static final String INSERT_INTO = "INSERT INTO ";
  public static final String DELETE_FROM = "DELETE FROM ";
  public static final String UPDATE = "UPDATE ";
  public static final String SET = " SET ";
  public static final String FROM = " FROM ";
  public static final String VALUES = " VALUES ";
  public static final String WHERE = " WHERE ";
  public static final String AND = " AND ";
  public static final String LIKE = " LIKE ";
  public static final String IN = " IN ";
  public static final String NOT = " NOT ";
  public static final String OR = " OR ";
  public static final String EQUAL_PARAMETER = "=?";
  public static final String ORDER_BY = " ORDER BY ";
  protected Logger log;
  private JDBCConnectionFactory connectionFactory;

  protected JDBCDAO() throws DAOException {
    try {
      this.log = Logger.getLogger(this.getClass());
      Preferences preferences = Preferences.systemNodeForPackage(this.getClass());
      String connectionFactoryClassName = preferences.get(CONNECTION_FACTORY_CLASS_NAME, CONNECTION_FACTORY_CLASS_NAME_DEFAULT);
      this.log.info("Loading " + connectionFactoryClassName);
      Class connectionFactoryClass = Class.forName(connectionFactoryClassName);
      Constructor connectionFactoryConstructor = connectionFactoryClass.getConstructor(class$org$orange$util$Preferences == null ? (class$org$orange$util$Preferences = class$("org.orange.util.Preferences")) : class$org$orange$util$Preferences);
      this.log.info("Calling " + connectionFactoryConstructor);
      this.connectionFactory = (JDBCConnectionFactory)connectionFactoryConstructor.newInstance(preferences);
    } catch (Exception var5) {
      var5.printStackTrace();
      throw new DAOException(var5);
    }
  }

  public Connection getConnection() throws SQLException {
    return this.connectionFactory.getConnection();
  }

  static {
    CONNECTION_FACTORY_CLASS_NAME = (class$org$orange$util$integration$JDBCDAO == null ? (class$org$orange$util$integration$JDBCDAO = class$("org.orange.util.integration.JDBCDAO")) : class$org$orange$util$integration$JDBCDAO).getName() + ".connectionFactoryClassName";
    CONNECTION_FACTORY_CLASS_NAME_DEFAULT = (class$org$orange$util$integration$DriverJDBCConnectionFactory == null ? (class$org$orange$util$integration$DriverJDBCConnectionFactory = class$("org.orange.util.integration.DriverJDBCConnectionFactory")) : class$org$orange$util$integration$DriverJDBCConnectionFactory).getName();
  }
}
