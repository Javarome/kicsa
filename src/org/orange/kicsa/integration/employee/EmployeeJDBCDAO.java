package org.orange.kicsa.integration.employee;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import org.orange.kicsa.EmployeeNotFoundException;
import org.orange.kicsa.business.Editable.Status;
import org.orange.kicsa.business.employee.Employee;
import org.orange.kicsa.business.employee.EmployeeImpl;
import org.orange.kicsa.business.employee.EmployeeKey;
import org.orange.util.Assert;
import org.orange.util.integration.DAOException;
import org.orange.util.integration.JDBCDAO;

public class EmployeeJDBCDAO extends JDBCDAO implements EmployeeDAO {
  public static final String TABLE = "EMPLOYEE";
  public static final String KEY = "ID";
  public static final String FIRST_NAME = "FIRST_NAME";
  public static final String LAST_NAME = "LAST_NAME";
  public static final String END_DATE = "END_DATE";
  public static final String ENTITY_URI = "ENTITY_URI";
  public static final String URI = "URI";
  public static final String ENTITY_NAME = "ENTITY_NAME";
  public static final String FIELDS = "ID,FIRST_NAME,LAST_NAME,URI,ENTITY_URI,END_DATE,ENTITY_NAME";
  public static final String SELECT_BY_KEY = "SELECT ID,FIRST_NAME,LAST_NAME,URI,ENTITY_URI,END_DATE,ENTITY_NAME FROM EMPLOYEE WHERE ID=?";
  public static final String SELECT_NEW_KEY = "SELECT MAX(ID) FROM EMPLOYEE";
  public static final String INSERT = "INSERT INTO EMPLOYEE (ID,FIRST_NAME,LAST_NAME,URI,ENTITY_URI,END_DATE,ENTITY_NAME) VALUES (?, ?, ?, ?, ?, ?, ?)";
  public static final String SELECT_ALL = "SELECT ID,FIRST_NAME,LAST_NAME FROM EMPLOYEE ORDER BY LAST_NAME";
  EmployeeMemoryDAO cache;
  public static final String REMOVE = "DELETE FROM EMPLOYEE WHERE ID=?";
  public static final String EMPLOYEE_UPDATE = "UPDATE EMPLOYEE SET FIRST_NAME=?,LAST_NAME=?,URI=?,ENTITY_URI=?,END_DATE=?,ENTITY_NAME=? WHERE ID=?";
  private static EmployeeJDBCDAO instance;

  public EmployeeJDBCDAO() throws DAOException {
  }

  public Employee getByPrimaryKey(EmployeeKey someKey) throws DAOException, EmployeeNotFoundException {
    if (super.log.isDebugEnabled()) {
      super.log.debug("getByPrimaryKey (" + someKey + ")");
    }

    Assert.notNullArgument(someKey, "Impossible de récupérer un employé de clé nulle");
    Employee employee = null;
    if (this.isCacheEnabled()) {
      employee = this.cache.getByPrimaryKey(someKey);
    }

    if (employee == null) {
      employee = this.readByPrimaryKey(someKey);
      if (this.isCacheEnabled()) {
        this.cache.setByPrimaryKey(someKey, employee);
      }
    }

    return employee;
  }

  public Employee readByPrimaryKey(EmployeeKey someKey) throws DAOException, EmployeeNotFoundException {
    Assert.notNullArgument(someKey, "Key of employee to read must not be null");

    try {
      Connection connection = this.getConnection();

      Employee var6;
      try {
        PreparedStatement selectByRefStatement = connection.prepareStatement("SELECT ID,FIRST_NAME,LAST_NAME,URI,ENTITY_URI,END_DATE,ENTITY_NAME FROM EMPLOYEE WHERE ID=?");
        selectByRefStatement.setInt(1, someKey.getId());
        Employee getByRef = null;
        ResultSet employeesSet = selectByRefStatement.executeQuery();
        if (!employeesSet.next()) {
          throw new EmployeeNotFoundException(someKey);
        }

        getByRef = this.rowToEmployee(employeesSet);
        var6 = getByRef;
      } finally {
        connection.close();
      }

      return var6;
    } catch (SQLException var12) {
      throw new DAOException("Impossible de lire l'employé avec la clé " + someKey, var12);
    }
  }

  protected Employee rowToEmployee(ResultSet employeeSet) throws SQLException, DAOException {
    int employeeId = employeeSet.getInt("ID");
    EmployeeKey key = new EmployeeKey(employeeId);
    Date employeeEndDate = employeeSet.getDate("END_DATE");
    Employee employee = new EmployeeImpl(employeeId, employeeSet.getString("FIRST_NAME"), employeeSet.getString("LAST_NAME"), employeeEndDate == null ? null : new java.util.Date(employeeEndDate.getTime()), employeeSet.getString("URI"), employeeSet.getString("ENTITY_URI"), employeeSet.getString("ENTITY_NAME"), Status.SYNC);
    if (this.isCacheEnabled()) {
      this.cache.setByPrimaryKey(key, employee);
    }

    if (super.log.isDebugEnabled()) {
      super.log.debug("lu " + employee);
    }

    return employee;
  }

  private int getNextId(Connection someConnection) throws SQLException {
    PreparedStatement selectMaxStatement = someConnection.prepareStatement("SELECT MAX(ID) FROM EMPLOYEE");
    ResultSet selectMaxResultSet = selectMaxStatement.executeQuery();
    if (!selectMaxResultSet.next()) {
      throw new SQLException("Impossible de récupérer la clé maximum avec SELECT MAX(ID) FROM EMPLOYEE");
    } else {
      int max = selectMaxResultSet.getInt(1);
      int nextId = max + 1;
      if (super.log.isDebugEnabled()) {
        super.log.debug("getNextId: return " + nextId);
      }

      return nextId;
    }
  }

  public Employee create(Employee someEmployee) throws DAOException {
    Assert.notNullArgument(someEmployee, "Impossible de créer un employé nul");

    try {
      Connection connection = this.getConnection();

      Employee var7;
      try {
        connection.setTransactionIsolation(8);
        int id = this.getNextId(connection);
        PreparedStatement insertStatement = connection.prepareStatement("INSERT INTO EMPLOYEE (ID,FIRST_NAME,LAST_NAME,URI,ENTITY_URI,END_DATE,ENTITY_NAME) VALUES (?, ?, ?, ?, ?, ?, ?)");
        insertStatement.setInt(1, id);
        insertStatement.setString(2, someEmployee.getFirstName());
        insertStatement.setString(3, someEmployee.getLastName());
        insertStatement.setString(4, someEmployee.getURI());
        insertStatement.setString(5, someEmployee.getEntityURI());
        java.util.Date endDate = someEmployee.getEndDate();
        if (endDate != null) {
          insertStatement.setDate(6, new Date(endDate.getTime()));
        } else {
          insertStatement.setDate(6, (Date)null);
        }

        insertStatement.setString(7, someEmployee.getEntityName());
        int affectedRows = insertStatement.executeUpdate();
        if (affectedRows != 1) {
          throw new DAOException("La requête INSERT INTO EMPLOYEE (ID,FIRST_NAME,LAST_NAME,URI,ENTITY_URI,END_DATE,ENTITY_NAME) VALUES (?, ?, ?, ?, ?, ?, ?) a affecté " + affectedRows + " enregistrements au lieu de 1");
        }

        someEmployee.setId(id);
        someEmployee.setSync(true);
        if (this.isCacheEnabled()) {
          this.cache.create(someEmployee);
        }

        if (super.log.isDebugEnabled()) {
          super.log.debug("Employé " + someEmployee + " créée");
        }

        var7 = someEmployee;
      } finally {
        connection.close();
      }

      return var7;
    } catch (SQLException var13) {
      throw new DAOException("Impossible de créer l'employé " + someEmployee, var13);
    }
  }

  public Map getAll() throws DAOException {
    Map all = null;
    if (this.isCacheEnabled()) {
      all = this.cache.getAll();
    }

    if (all == null) {
      all = this.readAll();
      if (this.isCacheEnabled()) {
        this.cache.setAll(all);
      }
    }

    return all;
  }

  public boolean isCacheEnabled() {
    return this.cache != null;
  }

  public Map readAll() throws DAOException {
    try {
      Connection connection = this.getConnection();

      try {
        PreparedStatement selectAllStatement = connection.prepareStatement("SELECT ID,FIRST_NAME,LAST_NAME FROM EMPLOYEE ORDER BY LAST_NAME");
        ResultSet resultSet = selectAllStatement.executeQuery();
        HashMap allEmployees = new HashMap();

        while(resultSet.next()) {
          EmployeeKey employeeKey = new EmployeeKey(resultSet.getInt("ID"));
          StringBuffer employeeBuffer = new StringBuffer();
          employeeBuffer.append(resultSet.getString("LAST_NAME"));
          employeeBuffer.append(", ").append(resultSet.getString("FIRST_NAME"));
          allEmployees.put(employeeKey, employeeBuffer.toString());
        }

        HashMap var13 = allEmployees;
        return var13;
      } finally {
        connection.close();
      }
    } catch (SQLException var12) {
      throw new DAOException("Impossible de récupérer l'ensemble des employés", var12);
    }
  }

  public static EmployeeDAO getInstance() throws DAOException {
    if (instance == null) {
      Class var0 = class$org$orange$kicsa$integration$employee$EmployeeJDBCDAO == null ? (class$org$orange$kicsa$integration$employee$EmployeeJDBCDAO = class$("org.orange.kicsa.integration.employee.EmployeeJDBCDAO")) : class$org$orange$kicsa$integration$employee$EmployeeJDBCDAO;
      synchronized(var0) {
        instance = new EmployeeJDBCDAO();
      }
    }

    return instance;
  }

  public void remove(EmployeeKey someEmployeeKey) throws DAOException {
    try {
      Connection connection = this.getConnection();

      try {
        PreparedStatement insertStatement = connection.prepareStatement("DELETE FROM EMPLOYEE WHERE ID=?");
        insertStatement.setInt(1, someEmployeeKey.getId());
        if (super.log.isDebugEnabled()) {
          super.log.debug("Exécution de DELETE FROM EMPLOYEE WHERE ID=?");
        }

        int rowsAffected = insertStatement.executeUpdate();
        if (rowsAffected != 1) {
          throw new DAOException("La requête DELETE FROM EMPLOYEE WHERE ID=? a affecté " + rowsAffected + " enregistrements au lieu de 1");
        }

        if (this.isCacheEnabled()) {
          this.cache.remove(someEmployeeKey);
        }

        if (super.log.isDebugEnabled()) {
          super.log.debug("Employé de clé " + someEmployeeKey + " supprimé de la base");
        }
      } finally {
        connection.close();
      }

    } catch (Throwable var10) {
      throw new DAOException("Impossible de supprimer la compétence ", var10);
    }
  }

  public Employee update(Employee someEmployee) throws DAOException {
    Assert.notNullArgument(someEmployee, "Impossible d'enregistrer les modifications d'un employé nul");

    try {
      Connection connection = this.getConnection();

      Employee var6;
      try {
        PreparedStatement insertStatement = connection.prepareStatement("UPDATE EMPLOYEE SET FIRST_NAME=?,LAST_NAME=?,URI=?,ENTITY_URI=?,END_DATE=?,ENTITY_NAME=? WHERE ID=?");
        insertStatement.setString(1, someEmployee.getFirstName());
        insertStatement.setString(2, someEmployee.getLastName());
        insertStatement.setString(3, someEmployee.getURI());
        insertStatement.setString(4, someEmployee.getEntityURI());
        java.util.Date employeeEndDate = someEmployee.getEndDate();
        insertStatement.setDate(5, employeeEndDate == null ? null : new Date(employeeEndDate.getTime()));
        insertStatement.setString(6, someEmployee.getEntityName());
        insertStatement.setInt(7, someEmployee.getId());
        int affectedRows = insertStatement.executeUpdate();
        if (affectedRows != 1) {
          throw new DAOException("La mise à jour de l'employé " + someEmployee + " a échoué (n'existe pas dans la base ?)");
        }

        someEmployee.setSync(true);
        if (this.isCacheEnabled()) {
          this.cache.update(someEmployee);
        }

        if (super.log.isDebugEnabled()) {
          super.log.debug("Employé " + someEmployee + " modifié");
        }

        var6 = someEmployee;
      } finally {
        connection.close();
      }

      return var6;
    } catch (SQLException var12) {
      throw new DAOException("Impossible de modifier l'employé " + someEmployee, var12);
    }
  }
}
