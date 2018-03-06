package test.org.orange.kicsa.integration.ability;

import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.SortedMap;
import junit.framework.Assert;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.orange.kicsa.business.Editable.Status;
import org.orange.kicsa.business.ability.EmployeeAbility;
import org.orange.kicsa.business.ability.EmployeeAbilityImpl;
import org.orange.kicsa.business.employee.Employee;
import org.orange.kicsa.business.employee.EmployeeImpl;
import org.orange.kicsa.business.employee.EmployeeKey;
import org.orange.kicsa.business.skill.Skill;
import org.orange.kicsa.business.skill.SkillImpl;
import org.orange.kicsa.business.skill.SkillKey;
import org.orange.kicsa.integration.ability.AbilityDAO;
import org.orange.kicsa.integration.ability.AbilityJDBCDAO;
import org.orange.util.Preferences;
import org.orange.util.integration.DAOException;
import org.orange.util.integration.JDBCDAO;

public class TestAbilityJDBCDAO extends TestCase {
  AbilityDAO abilityDAO;
  static Employee createdEmployee;
  final EmployeeAbility[] ABILITIES;

  public static Test suite() {
    TestSuite suite = new TestSuite();
    suite.addTest(new TestAbilityJDBCDAO("testCreate"));
    suite.addTest(new TestAbilityJDBCDAO("testGetByEmployee"));
    suite.addTest(new TestAbilityJDBCDAO("testGetAbleEmployeesBySkill"));
    suite.addTest(new TestAbilityJDBCDAO("testGetAbleEntitiesBySkill"));
    suite.addTest(new TestAbilityJDBCDAO("testRemove"));
    suite.addTest(new TestAbilityJDBCDAO("testRemoveByEmployee"));
    return suite;
  }

  public TestAbilityJDBCDAO(String name) {
    super(name);
    this.ABILITIES = new EmployeeAbility[]{new EmployeeAbilityImpl(createdEmployee.getKey(), createdEmployee.getFirstName(), createdEmployee.getLastName(), new SkillKey(26), "capacité test 1", 3), new EmployeeAbilityImpl(createdEmployee.getKey(), createdEmployee.getFirstName(), createdEmployee.getLastName(), new SkillKey(11), "capacité test 2", 2), new EmployeeAbilityImpl(createdEmployee.getKey(), createdEmployee.getFirstName(), createdEmployee.getLastName(), new SkillKey(9), "capacité test 3", 1)};
  }

  protected void setUp() {
    try {
      Preferences preferences = Preferences.systemNodeForPackage(class$org$orange$kicsa$integration$employee$EmployeeJDBCDAO == null ? (class$org$orange$kicsa$integration$employee$EmployeeJDBCDAO = class$("org.orange.kicsa.integration.employee.EmployeeJDBCDAO")) : class$org$orange$kicsa$integration$employee$EmployeeJDBCDAO);
      preferences.put(JDBCDAO.CONNECTION_FACTORY_CLASS_NAME, (class$org$orange$util$integration$DriverJDBCConnectionFactory == null ? (class$org$orange$util$integration$DriverJDBCConnectionFactory = class$("org.orange.util.integration.DriverJDBCConnectionFactory")) : class$org$orange$util$integration$DriverJDBCConnectionFactory).getName());
      preferences = Preferences.systemNodeForPackage(class$org$orange$kicsa$integration$skill$SkillJDBCDAO == null ? (class$org$orange$kicsa$integration$skill$SkillJDBCDAO = class$("org.orange.kicsa.integration.skill.SkillJDBCDAO")) : class$org$orange$kicsa$integration$skill$SkillJDBCDAO);
      preferences.put(JDBCDAO.CONNECTION_FACTORY_CLASS_NAME, (class$org$orange$util$integration$DriverJDBCConnectionFactory == null ? (class$org$orange$util$integration$DriverJDBCConnectionFactory = class$("org.orange.util.integration.DriverJDBCConnectionFactory")) : class$org$orange$util$integration$DriverJDBCConnectionFactory).getName());
      preferences = Preferences.systemNodeForPackage(class$org$orange$kicsa$integration$skill$RelationshipTypeJDBCDAO == null ? (class$org$orange$kicsa$integration$skill$RelationshipTypeJDBCDAO = class$("org.orange.kicsa.integration.skill.RelationshipTypeJDBCDAO")) : class$org$orange$kicsa$integration$skill$RelationshipTypeJDBCDAO);
      preferences.put(JDBCDAO.CONNECTION_FACTORY_CLASS_NAME, (class$org$orange$util$integration$DriverJDBCConnectionFactory == null ? (class$org$orange$util$integration$DriverJDBCConnectionFactory = class$("org.orange.util.integration.DriverJDBCConnectionFactory")) : class$org$orange$util$integration$DriverJDBCConnectionFactory).getName());
      this.abilityDAO = AbilityJDBCDAO.getInstance();
      int id = 0;
      String firstName = "Test firstName";
      String lastName = "Test lastName";
      Date endDate = new Date();
      String URI = "mailto:javarome@javarome.net";
      String entityURI = "http://kicsa.sourceforge.net";
      String entityName = "RIEN";
      createdEmployee = new EmployeeImpl(id, firstName, lastName, endDate, URI, entityURI, entityName, Status.SYNC);
    } catch (DAOException var9) {
      Assert.fail(var9.toString());
    }

  }

  protected void tearDown() {
  }

  public void testRemoveByEmployee() {
    Assert.assertNotNull("L'employé créé est nul", createdEmployee);
    EmployeeKey employeeKey = createdEmployee.getKey();

    try {
      int abilitiesRemoved = this.abilityDAO.removeByEmployee(employeeKey);
      System.out.println("Les " + abilitiesRemoved + " capacités de l'employé de clé " + employeeKey + " ont été supprimées");
    } catch (DAOException var3) {
      Assert.fail(var3.toString());
    }

  }

  public void testRemove() {
    Assert.assertNotNull("Created employee cannot be null", createdEmployee);
    EmployeeAbility abilityToRemove = this.ABILITIES[0];

    try {
      this.abilityDAO.remove(abilityToRemove);
      System.out.println("Removed " + abilityToRemove);
    } catch (DAOException var3) {
      Assert.fail(var3.toString());
    }

  }

  public void testCreate() {
    Assert.assertNotNull("Created employee cannot be null", createdEmployee);

    try {
      for(int i = 0; i < this.ABILITIES.length; ++i) {
        this.abilityDAO.create(this.ABILITIES[i]);
        System.out.println("Created " + this.ABILITIES[i]);
      }
    } catch (DAOException var2) {
      Assert.fail(var2.toString());
    }

  }

  public void testGetByEmployee() {
    Assert.assertNotNull("Created employee cannot be null", createdEmployee);
    EmployeeKey employeeKey = createdEmployee.getKey();

    try {
      Collection abilities = this.abilityDAO.getByEmployee(employeeKey);
      System.out.println(abilities.size() + " abilities found for employee with key " + employeeKey);
      Iterator allIterator = abilities.iterator();

      while(allIterator.hasNext()) {
        Object ability = allIterator.next();
        System.out.println(ability);
      }
    } catch (DAOException var5) {
      Assert.fail(var5.toString());
    }

  }

  public void testGetAbleEmployeesBySkill() {
    Skill skill = new SkillImpl(16, "J2EE", "Java 2 Enterprise Edition", "Tedt description", (SortedMap)null);
    SkillKey skillKey = skill.getKey();

    try {
      Collection abilities = this.abilityDAO.getAbleEmployeesBySkill(skillKey);
      System.out.println(abilities.size() + " employee abilities found linked to skill of key " + skillKey);
      Iterator allIterator = abilities.iterator();

      while(allIterator.hasNext()) {
        Object ability = allIterator.next();
        System.out.println(ability);
      }
    } catch (DAOException var6) {
      Assert.fail(var6.toString());
    }

  }

  public void testGetAbleEntitiesBySkill() {
    Skill skill = new SkillImpl(26, "Dessert", "Douceurs", "description de test", (SortedMap)null);
    SkillKey skillKey = skill.getKey();

    try {
      Collection abilities = this.abilityDAO.getAbleEntitiesBySkill(skillKey);
      System.out.println(abilities.size() + " entity abilities found linked to skill of key " + skillKey);
      Iterator allIterator = abilities.iterator();

      while(allIterator.hasNext()) {
        Object ability = allIterator.next();
        System.out.println(ability);
      }
    } catch (DAOException var6) {
      Assert.fail(var6.toString());
    }

  }
}
