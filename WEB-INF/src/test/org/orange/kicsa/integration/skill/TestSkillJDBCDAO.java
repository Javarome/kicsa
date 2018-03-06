package test.org.orange.kicsa.integration.skill;

import java.net.URL;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import junit.framework.Assert;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.orange.kicsa.Application;
import org.orange.kicsa.SkillNotFoundException;
import org.orange.kicsa.business.skill.Skill;
import org.orange.kicsa.business.skill.SkillImpl;
import org.orange.kicsa.business.skill.SkillKey;
import org.orange.kicsa.business.skill.SkillLevel;
import org.orange.kicsa.business.skill.SkillLevelImpl;
import org.orange.kicsa.integration.skill.SkillJDBCDAO;
import org.orange.util.Preferences;
import org.orange.util.integration.DAOException;
import org.orange.util.integration.JDBCDAO;

public class TestSkillJDBCDAO extends TestCase {
  SkillJDBCDAO dao;
  Logger log;
  private static Skill createdParentSkill;
  private static Skill createdChildSkill;

  public TestSkillJDBCDAO(String name) {
    super(name);
  }

  protected void setUp() {
    try {
      Preferences preferences = Preferences.systemNodeForPackage(class$org$orange$kicsa$integration$skill$SkillJDBCDAO == null ? (class$org$orange$kicsa$integration$skill$SkillJDBCDAO = class$("org.orange.kicsa.integration.skill.SkillJDBCDAO")) : class$org$orange$kicsa$integration$skill$SkillJDBCDAO);
      preferences.put(JDBCDAO.CONNECTION_FACTORY_CLASS_NAME, (class$org$orange$util$integration$DriverJDBCConnectionFactory == null ? (class$org$orange$util$integration$DriverJDBCConnectionFactory = class$("org.orange.util.integration.DriverJDBCConnectionFactory")) : class$org$orange$util$integration$DriverJDBCConnectionFactory).getName());
      preferences = Preferences.systemNodeForPackage(class$org$orange$kicsa$integration$skill$RelationshipTypeJDBCDAO == null ? (class$org$orange$kicsa$integration$skill$RelationshipTypeJDBCDAO = class$("org.orange.kicsa.integration.skill.RelationshipTypeJDBCDAO")) : class$org$orange$kicsa$integration$skill$RelationshipTypeJDBCDAO);
      preferences.put(JDBCDAO.CONNECTION_FACTORY_CLASS_NAME, (class$org$orange$util$integration$DriverJDBCConnectionFactory == null ? (class$org$orange$util$integration$DriverJDBCConnectionFactory = class$("org.orange.util.integration.DriverJDBCConnectionFactory")) : class$org$orange$util$integration$DriverJDBCConnectionFactory).getName());
      this.dao = (SkillJDBCDAO)SkillJDBCDAO.getInstance();
      this.log = Logger.getLogger(this.getClass());
      this.log.setLevel(Level.DEBUG);
      Application.init((URL)null);
    } catch (Throwable var2) {
      Assert.fail(var2.toString());
    }

  }

  public static Test suite() {
    TestSuite suite = new TestSuite();
    suite.addTest(new TestSkillJDBCDAO("testCreate"));
    suite.addTest(new TestSkillJDBCDAO("testUpdate"));
    suite.addTest(new TestSkillJDBCDAO("testGetByPrimaryKey"));
    suite.addTest(new TestSkillJDBCDAO("testGetChilds"));
    suite.addTest(new TestSkillJDBCDAO("testGetAllDestinations"));
    suite.addTest(new TestSkillJDBCDAO("testGetAll"));
    suite.addTest(new TestSkillJDBCDAO("testGetParents"));
    suite.addTest(new TestSkillJDBCDAO("testRemove"));
    return suite;
  }

  protected void tearDown() {
  }

  public void testRemove() {
    try {
      int removedParentRelationshipsCount = this.dao.remove(createdParentSkill.getKey());
      System.out.println("La compétence " + createdParentSkill + " a été supprimée ainsi que ses " + removedParentRelationshipsCount + " relations");
      int removedChildRelationshipsCount = this.dao.remove(createdChildSkill.getKey());
      System.out.println("La compétence " + createdChildSkill + " a été supprimée ainsi que ses " + removedChildRelationshipsCount + " relations");

      try {
        this.dao.readByPrimaryKey(createdParentSkill.getKey());
      } catch (Exception var5) {
        Assert.assertTrue("La compétence parent est supprimee", var5.getClass().isInstance(new SkillNotFoundException(createdParentSkill.getKey())));
      }

      try {
        this.dao.readByPrimaryKey(createdChildSkill.getKey());
      } catch (Exception var4) {
        Assert.assertTrue("La compétence fille est supprimee", var4.getClass().isInstance(new SkillNotFoundException(createdChildSkill.getKey())));
      }

      Assert.assertEquals("La relation est probablement suprimee ", removedParentRelationshipsCount, 1);
    } catch (DAOException var6) {
      Assert.fail(var6.toString());
    }

  }

  public void testGetByPrimaryKey() {
    SkillKey parentKey = createdParentSkill.getKey();
    SkillKey var2 = createdParentSkill.getKey();

    try {
      Skill parentSkill = this.dao.getByPrimaryKey(parentKey);
      System.out.println("La compétence de clé " + parentKey + " est " + parentSkill);
      Assert.assertTrue(parentSkill.getId() == parentKey.getId());
      Assert.assertTrue(parentSkill.getId() == parentKey.getId());
    } catch (SkillNotFoundException var5) {
      Assert.fail(var5.toString());
    } catch (DAOException var6) {
      Assert.fail(var6.toString());
    }

  }

  public void testGetParents() {
    try {
      Collection listParents = this.dao.getParents(createdChildSkill);
      System.out.println(listParents.getClass());
      System.out.println(createdChildSkill + "'s parents are:" + listParents);
      Assert.assertTrue(listParents.contains(createdParentSkill));
    } catch (DAOException var2) {
      Assert.fail(var2.toString());
    }

  }

  public void testGetAllDestinations() {
    try {
      Collection relationsList = this.dao.getDestinations(createdParentSkill);
      System.out.println("List of relationships from this skill " + createdParentSkill + ":");
      Iterator relationsListIterator = relationsList.iterator();

      while(relationsListIterator.hasNext()) {
        Object o = relationsListIterator.next();
        System.out.println(o);
      }
    } catch (DAOException var4) {
      Assert.fail(var4.toString());
    }

  }

  public void testCreate() {
    createdParentSkill = new SkillImpl(0, "Parent", "Parent de test", "Ceci est un parent de test", (SortedMap)null);
    createdChildSkill = new SkillImpl(0, "Fils", "Fils de test", "Ceci est un fils de test", (SortedMap)null);
    SkillLevel[] SKILL_LEVELS = new SkillLevel[]{new SkillLevelImpl(1, "Niveau 1", "Description niveau 1"), new SkillLevelImpl(2, "", ""), new SkillLevelImpl(3, "", "")};
    SortedMap levels = new TreeMap();

    for(int level = 0; level < SKILL_LEVELS.length; ++level) {
      levels.put(String.valueOf(SKILL_LEVELS[level]), SKILL_LEVELS[level]);
    }

    createdParentSkill.setLevels(levels);
    createdChildSkill.setLevels(levels);

    try {
      createdParentSkill = this.dao.create(createdParentSkill);
      System.out.println("createdParentSkill=" + createdParentSkill);
      createdChildSkill = this.dao.create(createdChildSkill);
      System.out.println("createdChildSkill=" + createdChildSkill);
      this.dao.link(createdChildSkill, "Parent", createdParentSkill.getKey());
      Assert.assertTrue("Skill parent cree", createdParentSkill.getId() > 1);
      Assert.assertTrue("Skill fils cree", createdChildSkill.getId() > 1);
    } catch (DAOException var5) {
      Assert.fail(var5.toString());
    }

  }

  public void testUpdate() {
    Assert.assertNotNull("this.createdSkill est nul", createdParentSkill);
    createdParentSkill.setShortName("Updated shortName");
    createdParentSkill.setLongName("Updated longName");

    try {
      createdParentSkill = this.dao.update(createdParentSkill);
      System.out.println("updated createdParentSkill=" + createdParentSkill);
    } catch (DAOException var2) {
      Assert.fail(var2.toString());
    }

  }

  public void testGetAll() {
    try {
      Map skillList = this.dao.getAll();
      System.out.println("Liste des compétences");
      Collection skillKeys = skillList.keySet();
      Iterator skillKeysIterator = skillKeys.iterator();

      while(skillKeysIterator.hasNext()) {
        Object key = skillKeysIterator.next();
        System.out.println(key + "=" + skillList.get(key));
      }
    } catch (DAOException var5) {
      Assert.fail(var5.toString());
    }

  }

  public void testGetLevels() {
    SkillKey skillKey = new SkillKey(0);

    try {
      Map skillLevelsByIndex = this.dao.getLevels(skillKey);
      System.out.println("Niveaux pour la compétence de clé " + skillKey);
      Collection skillLevels = skillLevelsByIndex.keySet();
      Iterator skillLevelsIterator = skillLevels.iterator();

      while(skillLevelsIterator.hasNext()) {
        Object key = skillLevelsIterator.next();
        System.out.println(key + "=" + skillLevelsByIndex.get(key));
      }
    } catch (SkillNotFoundException var6) {
      Assert.fail(var6.toString());
    } catch (DAOException var7) {
      Assert.fail(var7.toString());
    }

  }

  public void testGetChilds() {
    Skill parentSkill = Application.getSkillFactory().getRoot();
    Assert.assertNotNull("La compétence parente est nulle", parentSkill);

    try {
      Collection skillList = this.dao.getChilds(parentSkill);
      System.out.println("Compétences filles de " + parentSkill + " :");
      Iterator skillKeysIterator = skillList.iterator();

      while(skillKeysIterator.hasNext()) {
        Object key = skillKeysIterator.next();
        System.out.println(key);
      }
    } catch (DAOException var5) {
      Assert.fail(var5.toString());
    }

  }

  public void testClearCache() {
    Assert.fail("not implemented");
  }

  public void testCreateRelationship() {
    int firstId = true;
    byte secondId = 45;

    try {
      this.dao.link(createdParentSkill, "Parent", new SkillKey(secondId));
    } catch (DAOException var4) {
      Assert.fail(var4.toString());
    }

  }
}
