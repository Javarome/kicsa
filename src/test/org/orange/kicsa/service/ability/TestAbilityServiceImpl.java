package test.org.orange.kicsa.service.ability;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.SortedMap;
import java.util.TreeMap;
import junit.framework.Assert;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.orange.kicsa.Application;
import org.orange.kicsa.business.Editable.Status;
import org.orange.kicsa.business.ability.Ability;
import org.orange.kicsa.business.ability.EmployeeAbilityImpl;
import org.orange.kicsa.business.employee.Employee;
import org.orange.kicsa.business.employee.EmployeeImpl;
import org.orange.kicsa.business.skill.Skill;
import org.orange.kicsa.business.skill.SkillImpl;
import org.orange.kicsa.business.skill.SkillKey;
import org.orange.kicsa.business.skill.SkillLevelImpl;
import org.orange.kicsa.service.ability.AbilityService;
import org.orange.kicsa.service.ability.AbilityServicePool;
import org.orange.util.Preferences;
import org.orange.util.integration.JDBCDAO;

public class TestAbilityServiceImpl extends TestCase {
  private static Employee createdEmployee;
  private static AbilityServicePool abilityServicePool;

  public static Test suite() {
    TestSuite suite = new TestSuite();
    suite.addTest(new TestAbilityServiceImpl("testCreateEmployee"));
    suite.addTest(new TestAbilityServiceImpl("testUpdateEmployee"));
    suite.addTest(new TestAbilityServiceImpl("testFindByEmployee"));
    suite.addTest(new TestAbilityServiceImpl("testFindAbleEmployeesBySkill"));
    suite.addTest(new TestAbilityServiceImpl("testFindAbleEntitiesBySkill"));
    suite.addTest(new TestAbilityServiceImpl("testRemoveEmployee"));
    return suite;
  }

  public TestAbilityServiceImpl(String name) {
    super(name);
  }

  protected void setUp() {
    try {
      Preferences preferences = Preferences.systemNodeForPackage(class$org$orange$util$integration$JDBCDAO == null ? (class$org$orange$util$integration$JDBCDAO = class$("org.orange.util.integration.JDBCDAO")) : class$org$orange$util$integration$JDBCDAO);
      preferences.put(JDBCDAO.CONNECTION_FACTORY_CLASS_NAME, (class$org$orange$util$integration$DriverJDBCConnectionFactory == null ? (class$org$orange$util$integration$DriverJDBCConnectionFactory = class$("org.orange.util.integration.DriverJDBCConnectionFactory")) : class$org$orange$util$integration$DriverJDBCConnectionFactory).getName());
      Application.init((URL)null);
      abilityServicePool = AbilityServicePool.getInstance();
    } catch (Throwable var2) {
      Assert.fail(var2.toString());
    }

  }

  protected void tearDown() {
  }

  public void testCreateEmployee() {
    String firstName = "Test prénom";
    String lastName = "Test nom";
    Date endDate = new Date();
    String URI = "mailto:traite";
    String entityURI = "http://ace";
    String entityName = "RIEN";
    createdEmployee = new EmployeeImpl(firstName, lastName, endDate, URI, entityURI, entityName);
    Ability[] ABILITIES = new Ability[]{new EmployeeAbilityImpl(createdEmployee.getKey(), createdEmployee.getFirstName(), createdEmployee.getLastName(), new SkillKey(13), "J2EE", 3), new EmployeeAbilityImpl(createdEmployee.getKey(), createdEmployee.getFirstName(), createdEmployee.getLastName(), new SkillKey(11), "Java 1.2", 2), new EmployeeAbilityImpl(createdEmployee.getKey(), createdEmployee.getFirstName(), createdEmployee.getLastName(), new SkillKey(9), "Java", 1)};
    Collection abilities = new ArrayList();

    for(int i = 0; i < ABILITIES.length; ++i) {
      abilities.add(ABILITIES[i]);
    }

    Object rights = null;

    try {
      AbilityService abilityService = (AbilityService)abilityServicePool.borrowObject();

      try {
        createdEmployee = abilityService.createEmployee(createdEmployee, abilities, (Collection)rights);
        System.out.println("Employé " + createdEmployee + " créé avec les capacités " + abilities);
      } finally {
        abilityServicePool.returnObject(abilityService);
      }
    } catch (Throwable var17) {
      Assert.fail(var17.toString());
    }

  }

  public void testUpdateEmployee() {
    Assert.assertNotNull("L'employé créé est nul", createdEmployee);
    createdEmployee.setFirstName("First name updated");
    createdEmployee.setLastName("Last name updated");
    createdEmployee.setEndDate(new Date());
    createdEmployee.setURI("mailto:me@home.ent");
    createdEmployee.setEntityURI("http://ace");
    createdEmployee.setEntityName("RIEN updated");
    Ability[] ABILITIES = new Ability[]{new EmployeeAbilityImpl(createdEmployee.getKey(), createdEmployee.getFirstName(), createdEmployee.getLastName(), new SkillKey(14), "Bidibile", 3, Status.NEW), new EmployeeAbilityImpl(createdEmployee.getKey(), createdEmployee.getFirstName(), createdEmployee.getLastName(), new SkillKey(11), "Java 1.2", 2, Status.DELETED), new EmployeeAbilityImpl(createdEmployee.getKey(), createdEmployee.getFirstName(), createdEmployee.getLastName(), new SkillKey(9), "Java", 1, Status.SYNC)};
    Collection abilities = new ArrayList();

    Ability rights;
    for(int i = 0; i < ABILITIES.length; ++i) {
      rights = ABILITIES[i];
      abilities.add(rights);
    }

    rights = null;

    try {
      AbilityService abilityService = (AbilityService)abilityServicePool.borrowObject();

      try {
        createdEmployee = abilityService.updateEmployee(createdEmployee, abilities, rights);
        System.out.println(createdEmployee + " updated with " + abilities);
      } finally {
        abilityServicePool.returnObject(abilityService);
      }
    } catch (Throwable var11) {
      Assert.fail(var11.toString());
    }

  }

  public void testFindByEmployee() {
    Assert.assertNotNull(createdEmployee);

    try {
      AbilityService abilityService = (AbilityService)abilityServicePool.borrowObject();

      try {
        Collection abilities = abilityService.findByEmployee(createdEmployee);
        System.out.println("Les capacités de l'employé " + createdEmployee + " sont : " + abilities);
      } finally {
        abilityServicePool.returnObject(abilityService);
      }
    } catch (Throwable var8) {
      Assert.fail(var8.toString());
    }

  }

  public void testFindAbleEmployeesBySkill() {
    Skill skill = new SkillImpl(26, "Dessert", "Douceurs", "description de test", (SortedMap)null);
    int maxLevel = 4;
    SortedMap levels = new TreeMap();

    for(int level = 1; level < maxLevel; ++level) {
      String levelName = "Niveau " + level;
      String levelDescription = "Description for level " + level;
      levels.put(String.valueOf(level), new SkillLevelImpl(level, levelName, levelDescription));
    }

    skill.setLevels(levels);

    try {
      AbilityService abilityService = (AbilityService)abilityServicePool.borrowObject();

      try {
        SortedMap abilities = abilityService.findAbleEmployeesBySkill(skill);
        System.out.println("Abilities linked to " + skill + " are " + abilities);
      } finally {
        abilityServicePool.returnObject(abilityService);
      }
    } catch (Throwable var12) {
      Assert.fail(var12.toString());
    }

  }

  public void testFindAbleEntitiesBySkill() {
    Skill skill = new SkillImpl(26, "Dessert", "Douceurs", "description de test", (SortedMap)null);
    int maxLevel = 4;
    SortedMap levels = new TreeMap();

    for(int level = 1; level < maxLevel; ++level) {
      String levelName = "Niveau " + level;
      String levelDescription = "Description for level " + level;
      levels.put(String.valueOf(level), new SkillLevelImpl(level, levelName, levelDescription));
    }

    skill.setLevels(levels);

    try {
      AbilityService abilityService = (AbilityService)abilityServicePool.borrowObject();

      try {
        SortedMap abilities = abilityService.findAbleEntitiesBySkill(skill);
        System.out.println("Abilities linked to " + skill + " are " + abilities);
      } finally {
        abilityServicePool.returnObject(abilityService);
      }
    } catch (Throwable var12) {
      Assert.fail(var12.toString());
    }

  }

  public void testRemoveEmployee() {
    Assert.assertNotNull("this.createdEmployee est nul", createdEmployee);

    try {
      AbilityService abilityService = (AbilityService)abilityServicePool.borrowObject();

      try {
        createdEmployee.setDeleted(true);
        int capabilitiesCount = abilityService.removeEmployee(createdEmployee.getKey());
        System.out.println(createdEmployee + " supprimé ainsi que ses " + capabilitiesCount + " capacités");
      } finally {
        abilityServicePool.returnObject(abilityService);
      }
    } catch (Throwable var8) {
      Assert.fail(var8.toString());
    }

  }
}
