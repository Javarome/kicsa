package org.orange.kicsa.util;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;
import org.orange.kicsa.Application;
import org.orange.kicsa.business.employee.Employee;
import org.orange.kicsa.business.employee.EmployeeImpl;
import org.orange.kicsa.service.employee.EmployeeService;
import org.orange.kicsa.service.employee.EmployeeServicePool;
import org.orange.util.Preferences;
import org.orange.util.integration.DAOException;
import org.orange.util.integration.JDBCDAO;

public class Populate implements Runnable {
  public static final String EMPLOYEE_URI_PREFIX = "http://mobirec.ftm.francetelecom.fr/annuaire/recherche/personne.asp?numero=";
  public static final String ENTITY_URI_PREFIX = "http://mobirec.ftm.francetelecom.fr/annuaire/recherche/ListePersEntite.asp?numero=";
  private List entityMasks = new ArrayList();
  public static final String ENTITY_ABBREVIATION_MASK = "entityMask";
  public static final String ENTITY_ABBREVIATION_MASK_DEFAULT = "ORANGEFRANCE/DTRS/DCE/IT/EASY";

  public static void main(String[] someArgs) throws Throwable {
    Preferences preferences = Preferences.systemNodeForPackage(class$org$orange$kicsa$integration$employee$EmployeeJDBCDAO == null ? (class$org$orange$kicsa$integration$employee$EmployeeJDBCDAO = class$("org.orange.kicsa.integration.employee.EmployeeJDBCDAO")) : class$org$orange$kicsa$integration$employee$EmployeeJDBCDAO);
    preferences.put(JDBCDAO.CONNECTION_FACTORY_CLASS_NAME, (class$org$orange$util$integration$DriverJDBCConnectionFactory == null ? (class$org$orange$util$integration$DriverJDBCConnectionFactory = class$("org.orange.util.integration.DriverJDBCConnectionFactory")) : class$org$orange$util$integration$DriverJDBCConnectionFactory).getName());
    preferences = Preferences.systemNodeForPackage(class$org$orange$kicsa$integration$skill$SkillJDBCDAO == null ? (class$org$orange$kicsa$integration$skill$SkillJDBCDAO = class$("org.orange.kicsa.integration.skill.SkillJDBCDAO")) : class$org$orange$kicsa$integration$skill$SkillJDBCDAO);
    preferences.put(JDBCDAO.CONNECTION_FACTORY_CLASS_NAME, (class$org$orange$util$integration$DriverJDBCConnectionFactory == null ? (class$org$orange$util$integration$DriverJDBCConnectionFactory = class$("org.orange.util.integration.DriverJDBCConnectionFactory")) : class$org$orange$util$integration$DriverJDBCConnectionFactory).getName());
    preferences = Preferences.systemNodeForPackage(class$org$orange$kicsa$integration$skill$RelationshipTypeJDBCDAO == null ? (class$org$orange$kicsa$integration$skill$RelationshipTypeJDBCDAO = class$("org.orange.kicsa.integration.skill.RelationshipTypeJDBCDAO")) : class$org$orange$kicsa$integration$skill$RelationshipTypeJDBCDAO);
    preferences.put(JDBCDAO.CONNECTION_FACTORY_CLASS_NAME, (class$org$orange$util$integration$DriverJDBCConnectionFactory == null ? (class$org$orange$util$integration$DriverJDBCConnectionFactory = class$("org.orange.util.integration.DriverJDBCConnectionFactory")) : class$org$orange$util$integration$DriverJDBCConnectionFactory).getName());
    Application.init((URL)null);
    (new Thread(new Populate())).start();
  }

  public Populate() {
    Preferences preferences = Preferences.systemNodeForPackage(this.getClass());
    String entityMasksString = preferences.get("entityMask", "ORANGEFRANCE/DTRS/DCE/IT/EASY");
    StringTokenizer entityMasksTokenizer = new StringTokenizer(entityMasksString, ", \t");

    while(entityMasksTokenizer.hasMoreTokens()) {
      this.entityMasks.add(entityMasksTokenizer.nextToken());
    }

  }

  public void run() {
    EmployeeServicePool employeeServicePool = EmployeeServicePool.getInstance();

    try {
      EmployeeService employeeService = (EmployeeService)employeeServicePool.borrowObject();
      Populate.ArianeDAOImpl arianeDAO = new Populate.ArianeDAOImpl();

      try {
        Iterator entityMaskIterator = this.entityMasks.iterator();

        while(entityMaskIterator.hasNext()) {
          String entityMask = (String)entityMaskIterator.next();
          Collection entities = arianeDAO.getEntities(entityMask);
          Iterator entityIterator = entities.iterator();

          while(entityIterator.hasNext()) {
            Populate.Entity entity = (Populate.Entity)entityIterator.next();
            Collection employees = arianeDAO.getEmployees(entity);
            Iterator employeeIterator = employees.iterator();

            while(employeeIterator.hasNext()) {
              Employee employee = (Employee)employeeIterator.next();
              employee = employeeService.create(employee);
              System.out.println("Employé créé = " + employee);
            }
          }
        }
      } finally {
        employeeServicePool.returnObject(employeeService);
      }
    } catch (Throwable var17) {
      var17.printStackTrace();
    }

  }

  class ArianeDAOImpl extends JDBCDAO implements Populate.ArianeDAO {
    public static final String ENTITY_TABLE = "ENTITE";
    public static final String ENTITY_KEY = "EN_ID";
    public static final String ENTITY_NAME = "EN_ABREVIATION";
    public static final String EMPLOYEE_TABLE = "PERSONNE";
    public static final String EMPLOYEE_KEY = "PE_ID";
    public static final String EMPLOYEE_ENTITY = "EN_ID";
    public static final String EMPLOYEE_FIRST_NAME = "PE_PRENOM";
    public static final String EMPLOYEE_LAST_NAME = "PE_NOM";
    public static final String EMPLOYEE_END_DATE = "PE_DATEDEPARTREELLE";
    public static final String SELECT_ENTITIES_LIKE_NAME = "SELECT EN_ID,EN_ABREVIATION FROM ENTITE WHERE EN_ABREVIATION LIKE ?";
    public static final String SELECT_EMPLOYEES_BY_ENTITY = "SELECT PE_ID,PE_PRENOM,PE_NOM,PE_DATEDEPARTREELLE FROM PERSONNE WHERE EN_ID=?";

    public ArianeDAOImpl() throws DAOException {
    }

    public Collection getEntities(String someEntityMask) throws DAOException {
      try {
        Connection connection = this.getConnection();

        try {
          PreparedStatement selectStatement = connection.prepareStatement("SELECT EN_ID,EN_ABREVIATION FROM ENTITE WHERE EN_ABREVIATION LIKE ?");
          selectStatement.setString(1, someEntityMask);
          if (super.log.isDebugEnabled()) {
            super.log.debug(selectStatement);
          }

          ResultSet entitySet = selectStatement.executeQuery();
          ArrayList entities = new ArrayList();

          while(entitySet.next()) {
            Populate.Entity entity = Populate.this.new EntityImpl(entitySet.getString("EN_ID"), entitySet.getString("EN_ABREVIATION"));
            entities.add(entity);
          }

          if (super.log.isDebugEnabled()) {
            super.log.debug("Lu " + entities + " correspondant au masque " + someEntityMask);
          }

          ArrayList var13 = entities;
          return var13;
        } finally {
          connection.close();
        }
      } catch (SQLException var12) {
        throw new DAOException("Impossible de lire les entités pour le masque " + someEntityMask, var12);
      }
    }

    public Collection getEmployees(Populate.Entity someEntity) throws DAOException {
      try {
        Connection connection = this.getConnection();

        ArrayList var14;
        try {
          PreparedStatement selectStatement = connection.prepareStatement("SELECT PE_ID,PE_PRENOM,PE_NOM,PE_DATEDEPARTREELLE FROM PERSONNE WHERE EN_ID=?");
          String entityId = someEntity.getId();
          selectStatement.setString(1, entityId);
          ResultSet employeeSet = selectStatement.executeQuery();
          ArrayList employees = new ArrayList();

          while(employeeSet.next()) {
            Employee employee = new EmployeeImpl(employeeSet.getString("PE_PRENOM"), employeeSet.getString("PE_NOM"), employeeSet.getDate("PE_DATEDEPARTREELLE"), "http://mobirec.ftm.francetelecom.fr/annuaire/recherche/personne.asp?numero=" + employeeSet.getString("PE_ID"), "http://mobirec.ftm.francetelecom.fr/annuaire/recherche/ListePersEntite.asp?numero=" + entityId, someEntity.getName());
            employees.add(employee);
          }

          if (super.log.isDebugEnabled()) {
            super.log.debug("Lu " + employees + " pour l'entité " + someEntity);
          }

          var14 = employees;
        } finally {
          connection.close();
        }

        return var14;
      } catch (SQLException var13) {
        var13.printStackTrace();
        var13.getNextException().printStackTrace();
        throw new DAOException("Impossible de lire les employés pour l'entité " + someEntity, var13);
      }
    }
  }

  interface ArianeDAO {
    Collection getEntities(String var1) throws DAOException;

    Collection getEmployees(Populate.Entity var1) throws DAOException;
  }

  class EntityImpl implements Populate.Entity {
    private String id;
    private String name;

    public EntityImpl(String someId, String someName) {
      this.id = someId;
      this.name = someName;
    }

    public String getId() {
      return this.id;
    }

    public String getName() {
      return this.name;
    }

    public String toString() {
      return this.name + "(" + this.id + ")";
    }
  }

  interface Entity {
    String getId();

    String getName();
  }
}
