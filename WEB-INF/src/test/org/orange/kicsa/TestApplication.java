package test.org.orange.kicsa;

import java.net.URL;
import junit.framework.Assert;
import junit.framework.TestCase;
import org.orange.kicsa.Application;
import org.orange.util.Preferences;
import org.orange.util.integration.JDBCDAO;

public class TestApplication extends TestCase {
  public TestApplication(String name) {
    super(name);
  }

  protected void setUp() {
    Preferences preferences = Preferences.systemNodeForPackage(class$org$orange$kicsa$integration$skill$SkillJDBCDAO == null ? (class$org$orange$kicsa$integration$skill$SkillJDBCDAO = class$("org.orange.kicsa.integration.skill.SkillJDBCDAO")) : class$org$orange$kicsa$integration$skill$SkillJDBCDAO);
    preferences.put(JDBCDAO.CONNECTION_FACTORY_CLASS_NAME, (class$org$orange$util$integration$DriverJDBCConnectionFactory == null ? (class$org$orange$util$integration$DriverJDBCConnectionFactory = class$("org.orange.util.integration.DriverJDBCConnectionFactory")) : class$org$orange$util$integration$DriverJDBCConnectionFactory).getName());
    preferences = Preferences.systemNodeForPackage(class$org$orange$kicsa$integration$skill$RelationshipTypeJDBCDAO == null ? (class$org$orange$kicsa$integration$skill$RelationshipTypeJDBCDAO = class$("org.orange.kicsa.integration.skill.RelationshipTypeJDBCDAO")) : class$org$orange$kicsa$integration$skill$RelationshipTypeJDBCDAO);
    preferences.put(JDBCDAO.CONNECTION_FACTORY_CLASS_NAME, (class$org$orange$util$integration$DriverJDBCConnectionFactory == null ? (class$org$orange$util$integration$DriverJDBCConnectionFactory = class$("org.orange.util.integration.DriverJDBCConnectionFactory")) : class$org$orange$util$integration$DriverJDBCConnectionFactory).getName());
  }

  protected void tearDown() {
  }

  public void testInit() {
    try {
      Application.init((URL)null);
      System.out.println("Application.getDAOFactory()=" + Application.getDAOFactory());
      System.out.println("Application.getMBeanServer()=" + Application.getMBeanServer());
      System.out.println("Application.getSkillFactory()=" + Application.getSkillFactory());
    } catch (Throwable var2) {
      var2.printStackTrace();
      Assert.fail(var2.toString());
    }

  }
}
