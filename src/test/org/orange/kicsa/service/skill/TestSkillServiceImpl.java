package test.org.orange.kicsa.service.skill;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import junit.framework.Assert;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.orange.kicsa.Application;
import org.orange.kicsa.business.Editable.Status;
import org.orange.kicsa.business.employee.Employee;
import org.orange.kicsa.business.employee.EmployeeImpl;
import org.orange.kicsa.business.skill.Relationship;
import org.orange.kicsa.business.skill.RelationshipImpl;
import org.orange.kicsa.business.skill.RelationshipType;
import org.orange.kicsa.business.skill.RelationshipTypeImpl;
import org.orange.kicsa.business.skill.Skill;
import org.orange.kicsa.business.skill.SkillImpl;
import org.orange.kicsa.business.skill.SkillKey;
import org.orange.kicsa.business.skill.SkillLevelImpl;
import org.orange.kicsa.service.skill.SkillService;
import org.orange.kicsa.service.skill.SkillServicePool;
import org.orange.util.Preferences;
import org.orange.util.integration.JDBCDAO;

public class TestSkillServiceImpl extends TestCase {
  private static SkillServicePool skillServicePool;
  private static Skill createdSkill;
  private static List createdRelationshipTypes = new ArrayList();
  static final RelationshipType[] RELATIONSHIP_TYPES = new RelationshipType[]{new RelationshipTypeImpl("Test 1", true), new RelationshipTypeImpl("Test 2", false), new RelationshipTypeImpl("Test 3", false)};

  public TestSkillServiceImpl(String name) {
    super(name);
  }

  public static Test suite() {
    TestSuite suite = new TestSuite();
    suite.addTest(new TestSkillServiceImpl("testCreateRelationshipTypes"));
    suite.addTest(new TestSkillServiceImpl("testFindRelationshipTypes"));
    suite.addTest(new TestSkillServiceImpl("testCreate"));
    suite.addTest(new TestSkillServiceImpl("testUpdate"));
    suite.addTest(new TestSkillServiceImpl("testFindByPrimaryKey"));
    suite.addTest(new TestSkillServiceImpl("testRemove"));
    suite.addTest(new TestSkillServiceImpl("testRemoveRelationshipTypes"));
    return suite;
  }

  protected void setUp() {
    try {
      Preferences preferences = Preferences.systemNodeForPackage(class$org$orange$kicsa$integration$skill$SkillJDBCDAO == null ? (class$org$orange$kicsa$integration$skill$SkillJDBCDAO = class$("org.orange.kicsa.integration.skill.SkillJDBCDAO")) : class$org$orange$kicsa$integration$skill$SkillJDBCDAO);
      preferences.put(JDBCDAO.CONNECTION_FACTORY_CLASS_NAME, (class$org$orange$util$integration$DriverJDBCConnectionFactory == null ? (class$org$orange$util$integration$DriverJDBCConnectionFactory = class$("org.orange.util.integration.DriverJDBCConnectionFactory")) : class$org$orange$util$integration$DriverJDBCConnectionFactory).getName());
      preferences = Preferences.systemNodeForPackage(class$org$orange$kicsa$integration$skill$RelationshipTypeJDBCDAO == null ? (class$org$orange$kicsa$integration$skill$RelationshipTypeJDBCDAO = class$("org.orange.kicsa.integration.skill.RelationshipTypeJDBCDAO")) : class$org$orange$kicsa$integration$skill$RelationshipTypeJDBCDAO);
      preferences.put(JDBCDAO.CONNECTION_FACTORY_CLASS_NAME, (class$org$orange$util$integration$DriverJDBCConnectionFactory == null ? (class$org$orange$util$integration$DriverJDBCConnectionFactory = class$("org.orange.util.integration.DriverJDBCConnectionFactory")) : class$org$orange$util$integration$DriverJDBCConnectionFactory).getName());
      Application.init((URL)null);
      skillServicePool = SkillServicePool.getInstance();
    } catch (Throwable var2) {
      Assert.fail(var2.toString());
    }

  }

  protected void tearDown() {
  }

  public void testCreate() {
    try {
      SkillService skillService = (SkillService)skillServicePool.borrowObject();

      try {
        createdSkill = new SkillImpl("testShortName", "test long name", "description de test", (SortedMap)null);
        int maxLevel = 4;
        SortedMap levels = new TreeMap();

        for(int level = 1; level < maxLevel; ++level) {
          String levelName = "Niveau " + level;
          String levelDescription = "Description du niveau " + level;
          levels.put(String.valueOf(level), new SkillLevelImpl(level, levelName, levelDescription));
        }

        createdSkill.setLevels(levels);
        List relationships = new ArrayList();
        int skillId = true;
        String skillName = "44's name";
        String relationshipType = "Parent";
        Relationship onlyRelationship = new RelationshipImpl(createdSkill, relationshipType, new SkillKey(1), skillName);
        relationships.add(onlyRelationship);
        List owners = new ArrayList();
        int ownerId = 1;
        String ownerFirstName = "Jérôme";
        String ownerLastName = "Beau";
        Date ownerEndDate = new Date();
        String URI = "mailto:traite";
        String entityURI = "http://ace";
        String entityName = "RIEN2";
        Employee onlyOwner = new EmployeeImpl(ownerId, ownerFirstName, ownerLastName, ownerEndDate, URI, entityURI, entityName, Status.SYNC);
        owners.add(onlyOwner);
        Skill createdSkill = skillService.create(createdSkill, relationships, owners);
        System.out.println(createdSkill + " créé");
      } finally {
        skillServicePool.returnObject(skillService);
      }
    } catch (Throwable var25) {
      Assert.fail(var25.toString());
    }

  }

  public void testUpdate() {
    try {
      SkillService skillService = (SkillService)skillServicePool.borrowObject();

      try {
        createdSkill.setShortName("testShortName updated");
        createdSkill.setLongName("test long name updated");
        createdSkill.setDirty(true);
        int maxLevel = 4;
        SortedMap levels = new TreeMap();

        for(int level = 1; level < maxLevel; ++level) {
          String levelName = "Niveau " + level;
          String levelDescription = "Description du niveau " + level;
          levels.put(String.valueOf(level), new SkillLevelImpl(level, levelName, levelDescription));
        }

        createdSkill.setLevels(levels);
        List relationships = new ArrayList();
        int skillId = true;
        String skillName = "44's name";
        String relationshipType = "Parent";
        Relationship onlyRelationship = new RelationshipImpl(createdSkill, relationshipType, new SkillKey(1), skillName);
        relationships.add(onlyRelationship);
        List owners = new ArrayList();
        int ownerId = 1;
        String ownerFirstName = "Jérôme";
        String ownerLastName = "Beau";
        Date ownerEndDate = new Date();
        String URI = "mailto:traite";
        String entityURI = "http://ace";
        String entityName = "RIEN2";
        Employee onlyOwner = new EmployeeImpl(ownerId, ownerFirstName, ownerLastName, ownerEndDate, URI, entityURI, entityName, Status.DIRTY);
        owners.add(onlyOwner);
        Skill createdSkill = skillService.update(createdSkill, relationships, owners);
        System.out.println(createdSkill + " updated");
      } finally {
        skillServicePool.returnObject(skillService);
      }
    } catch (Throwable var25) {
      Assert.fail(var25.toString());
    }

  }

  public void testFindByPrimaryKey() {
    Assert.assertNotNull("this.createdSkill est nul", createdSkill);
    SkillKey skillKey = new SkillKey(26);

    try {
      SkillService skillService = (SkillService)skillServicePool.borrowObject();

      try {
        Skill foundSkill = skillService.findByPrimaryKey(skillKey);
        System.out.println("La compétence de clé " + skillKey + " est " + foundSkill);
      } finally {
        skillServicePool.returnObject(skillService);
      }
    } catch (Throwable var9) {
      Assert.fail(var9.toString());
    }

  }

  public void testRemove() {
    Assert.assertNotNull("this.createdSkill est nul", createdSkill);

    try {
      SkillService skillService = (SkillService)skillServicePool.borrowObject();

      try {
        createdSkill.setDeleted(true);
        List relationships = null;
        List owners = null;
        int var4 = skillService.remove(createdSkill.getKey());
      } finally {
        skillServicePool.returnObject(skillService);
      }
    } catch (Throwable var10) {
      Assert.fail(var10.toString());
    }

  }

  public void testFindRelationshipTypes() {
    Assert.assertNotNull(createdRelationshipTypes);
    Assert.assertTrue(createdRelationshipTypes.size() > 0);

    try {
      SkillService skillService = (SkillService)skillServicePool.borrowObject();

      try {
        Map relationshipTypes = skillService.findRelationshipTypes();
        System.out.println("relationshipTypes=" + relationshipTypes);
      } finally {
        skillServicePool.returnObject(skillService);
      }
    } catch (Throwable var8) {
      Assert.fail(var8.toString());
    }

  }

  public void testCreateRelationshipTypes() {
    try {
      SkillService skillService = (SkillService)skillServicePool.borrowObject();

      try {
        for(int i = 0; i < 3; ++i) {
          RelationshipType relationshipType = skillService.createRelationshipType(RELATIONSHIP_TYPES[i]);
          createdRelationshipTypes.add(relationshipType);
        }
      } finally {
        skillServicePool.returnObject(skillService);
      }
    } catch (Throwable var9) {
      Assert.fail(var9.toString());
    }

  }

  public void testRemoveRelationshipTypes() {
    Assert.assertNotNull(createdRelationshipTypes);
    Assert.assertTrue(createdRelationshipTypes.size() > 0);

    try {
      SkillService skillService = (SkillService)skillServicePool.borrowObject();

      try {
        Iterator i = createdRelationshipTypes.iterator();

        while(i.hasNext()) {
          String relationshipTypeName = (String)i.next();
          skillService.removeRelationshipType(relationshipTypeName);
        }
      } finally {
        skillServicePool.returnObject(skillService);
      }
    } catch (Throwable var9) {
      Assert.fail(var9.toString());
    }

  }
}
