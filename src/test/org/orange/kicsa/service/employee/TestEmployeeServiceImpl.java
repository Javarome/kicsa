package test.org.orange.kicsa.service.employee;

import java.net.URL;
import java.util.Date;
import junit.framework.Assert;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.orange.kicsa.Application;
import org.orange.kicsa.business.employee.Employee;
import org.orange.kicsa.business.employee.EmployeeImpl;
import org.orange.kicsa.service.employee.EmployeeService;
import org.orange.kicsa.service.employee.EmployeeServicePool;
import org.orange.util.Preferences;
import org.orange.util.integration.JDBCDAO;

public class TestEmployeeServiceImpl extends TestCase {
  private static EmployeeServicePool employeeServicePool;
  private static Employee createdEmployee;

  public TestEmployeeServiceImpl(String name) {
    super(name);
  }

  public static Test suite() {
    TestSuite suite = new TestSuite();
    suite.addTest(new TestEmployeeServiceImpl("testCreate"));
    suite.addTest(new TestEmployeeServiceImpl("testUpdate"));
    suite.addTest(new TestEmployeeServiceImpl("testRemove"));
    return suite;
  }

  protected void setUp() {
    try {
      Preferences preferences = Preferences.systemNodeForPackage(class$org$orange$util$integration$JDBCDAO == null ? (class$org$orange$util$integration$JDBCDAO = class$("org.orange.util.integration.JDBCDAO")) : class$org$orange$util$integration$JDBCDAO);
      preferences.put(JDBCDAO.CONNECTION_FACTORY_CLASS_NAME, (class$org$orange$util$integration$DriverJDBCConnectionFactory == null ? (class$org$orange$util$integration$DriverJDBCConnectionFactory = class$("org.orange.util.integration.DriverJDBCConnectionFactory")) : class$org$orange$util$integration$DriverJDBCConnectionFactory).getName());
      Application.init((URL)null);
      employeeServicePool = EmployeeServicePool.getInstance();
    } catch (Throwable var2) {
      Assert.fail(var2.toString());
    }

  }

  protected void tearDown() {
  }

  public void testCreate() {
    String firstName = "Test prénom";
    String lastName = "Test nom";
    Date endDate = new Date();
    String URI = "mailto:traite";
    String entityURI = "http://ace";
    String entityName = "RIEN";
    createdEmployee = new EmployeeImpl(firstName, lastName, endDate, URI, entityURI, entityName);

    try {
      EmployeeService employeeService = (EmployeeService)employeeServicePool.borrowObject();

      try {
        createdEmployee = employeeService.create(createdEmployee);
        System.out.println("Employé créé = " + createdEmployee);
      } finally {
        employeeServicePool.returnObject(employeeService);
      }
    } catch (Throwable var13) {
      Assert.fail(var13.toString());
    }

  }

  public void testUpdate() {
    Assert.assertNotNull("L'employé créé est nul", createdEmployee);
    createdEmployee.setFirstName("First name updated");
    createdEmployee.setLastName("Last name updated");
    createdEmployee.setEndDate(new Date());
    createdEmployee.setURI("mailto:me@home.ent");
    createdEmployee.setEntityURI("http://ace");
    createdEmployee.setEntityName("RIEN updated");

    try {
      EmployeeService employeeService = (EmployeeService)employeeServicePool.borrowObject();

      try {
        createdEmployee = employeeService.update(createdEmployee);
        System.out.println("Employé modifié = " + createdEmployee);
      } finally {
        employeeServicePool.returnObject(employeeService);
      }
    } catch (Throwable var7) {
      Assert.fail(var7.toString());
    }

  }

  public void testRemove() {
    Assert.assertNotNull("this.createdEmployee est nul", createdEmployee);

    try {
      EmployeeService employeeService = (EmployeeService)employeeServicePool.borrowObject();

      try {
        createdEmployee.setDeleted(true);
        employeeService.remove(createdEmployee.getKey());
        System.out.println(createdEmployee + " supprimé");
      } finally {
        employeeServicePool.returnObject(employeeService);
      }
    } catch (Throwable var7) {
      Assert.fail(var7.toString());
    }

  }
}
