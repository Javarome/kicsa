package test.org.orange.kicsa.integration.employee;

import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import junit.framework.Assert;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.orange.kicsa.business.employee.Employee;
import org.orange.kicsa.business.employee.EmployeeImpl;
import org.orange.kicsa.integration.employee.EmployeeDAO;
import org.orange.kicsa.integration.employee.EmployeeJDBCDAO;
import org.orange.util.Preferences;
import org.orange.util.integration.DAOException;
import org.orange.util.integration.JDBCDAO;

public class TestEmployeeJDBCDAO extends TestCase {
  EmployeeDAO dao;
  static Employee createdEmployee;

  public TestEmployeeJDBCDAO(String name) {
    super(name);
  }

  public static Test suite() {
    TestSuite suite = new TestSuite();
    suite.addTest(new TestEmployeeJDBCDAO("testCreate"));
    suite.addTest(new TestEmployeeJDBCDAO("testUpdate"));
    suite.addTest(new TestEmployeeJDBCDAO("testGetByPrimaryKey"));
    suite.addTest(new TestEmployeeJDBCDAO("testGetAll"));
    suite.addTest(new TestEmployeeJDBCDAO("testRemove"));
    return suite;
  }

  protected void setUp() {
    try {
      Preferences preferences = Preferences.systemNodeForPackage(class$org$orange$kicsa$integration$employee$EmployeeJDBCDAO == null ? (class$org$orange$kicsa$integration$employee$EmployeeJDBCDAO = class$("org.orange.kicsa.integration.employee.EmployeeJDBCDAO")) : class$org$orange$kicsa$integration$employee$EmployeeJDBCDAO);
      preferences.put(JDBCDAO.CONNECTION_FACTORY_CLASS_NAME, (class$org$orange$util$integration$DriverJDBCConnectionFactory == null ? (class$org$orange$util$integration$DriverJDBCConnectionFactory = class$("org.orange.util.integration.DriverJDBCConnectionFactory")) : class$org$orange$util$integration$DriverJDBCConnectionFactory).getName());
      this.dao = EmployeeJDBCDAO.getInstance();
    } catch (DAOException var2) {
      Assert.fail(var2.toString());
    }

  }

  protected void tearDown() {
  }

  public void testGetByPrimaryKey() {
    Assert.assertNotNull("L'employé créé est nul", createdEmployee);

    try {
      createdEmployee = this.dao.getByPrimaryKey(createdEmployee.getKey());
      System.out.println("Trouvé: " + createdEmployee);
    } catch (Exception var2) {
      Assert.fail(var2.toString());
    }

  }

  public void testCreate() {
    String firstName = "Jérôme";
    String lastName = "Beau";
    Date endDate = new Date();
    String URI = "mailto:traite";
    String entityURI = "http://ace";
    String entityName = "RIEN";

    try {
      createdEmployee = new EmployeeImpl(firstName, lastName, endDate, URI, entityURI, entityName);
      this.dao.create(createdEmployee);
      System.out.println("Employé créé= " + createdEmployee);
    } catch (DAOException var8) {
      Assert.fail(var8.toString());
    }

  }

  public void testUpdate() {
    Assert.assertNotNull("L'employé créé est nul", createdEmployee);
    createdEmployee.setFirstName("First name updated");
    createdEmployee.setLastName("Last name updated");
    createdEmployee.setEndDate(new Date());
    createdEmployee.setURI("maulto:him");
    createdEmployee.setEntityURI("http:ace");
    createdEmployee.setEntityName("RIEN updated");

    try {
      createdEmployee = this.dao.update(createdEmployee);
      System.out.println("Employé modifié = " + createdEmployee);
    } catch (DAOException var2) {
      Assert.fail(var2.toString());
    }

  }

  public void testGetAll() {
    try {
      Map all = this.dao.getAll();
      System.out.println("Found " + all.size() + " employees");
      Set keySet = all.keySet();
      Iterator allIterator = keySet.iterator();

      while(allIterator.hasNext()) {
        Object key = allIterator.next();
        System.out.println(" \"" + key + "\"=\"" + all.get(key) + "\"");
      }
    } catch (DAOException var5) {
      Assert.fail(var5.toString());
    }

  }

  public void testRemove() {
    Assert.assertNotNull("L'employé créé est nul", createdEmployee);

    try {
      this.dao.remove(createdEmployee.getKey());
      System.out.println(createdEmployee + " a été supprimé");
    } catch (DAOException var2) {
      Assert.fail(var2.toString());
    }

  }
}
