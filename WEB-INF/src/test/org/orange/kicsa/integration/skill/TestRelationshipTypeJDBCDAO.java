package test.org.orange.kicsa.integration.skill;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import junit.framework.Assert;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.orange.kicsa.business.skill.RelationshipType;
import org.orange.kicsa.business.skill.RelationshipTypeImpl;
import org.orange.kicsa.integration.skill.RelationshipTypeJDBCDAO;
import org.orange.util.Preferences;
import org.orange.util.integration.DAOException;
import org.orange.util.integration.JDBCDAO;

public class TestRelationshipTypeJDBCDAO extends TestCase {
  RelationshipTypeJDBCDAO dao;

  public TestRelationshipTypeJDBCDAO(String name) {
    super(name);
  }

  protected void setUp() throws DAOException {
    Logger.getRootLogger().setLevel(Level.DEBUG);
    Preferences preferences = Preferences.systemNodeForPackage(class$org$orange$kicsa$integration$skill$SkillJDBCDAO == null ? (class$org$orange$kicsa$integration$skill$SkillJDBCDAO = class$("org.orange.kicsa.integration.skill.SkillJDBCDAO")) : class$org$orange$kicsa$integration$skill$SkillJDBCDAO);
    preferences.put(JDBCDAO.CONNECTION_FACTORY_CLASS_NAME, (class$org$orange$util$integration$DriverJDBCConnectionFactory == null ? (class$org$orange$util$integration$DriverJDBCConnectionFactory = class$("org.orange.util.integration.DriverJDBCConnectionFactory")) : class$org$orange$util$integration$DriverJDBCConnectionFactory).getName());
    preferences = Preferences.systemNodeForPackage(class$org$orange$kicsa$integration$skill$RelationshipTypeJDBCDAO == null ? (class$org$orange$kicsa$integration$skill$RelationshipTypeJDBCDAO = class$("org.orange.kicsa.integration.skill.RelationshipTypeJDBCDAO")) : class$org$orange$kicsa$integration$skill$RelationshipTypeJDBCDAO);
    preferences.put(JDBCDAO.CONNECTION_FACTORY_CLASS_NAME, (class$org$orange$util$integration$DriverJDBCConnectionFactory == null ? (class$org$orange$util$integration$DriverJDBCConnectionFactory = class$("org.orange.util.integration.DriverJDBCConnectionFactory")) : class$org$orange$util$integration$DriverJDBCConnectionFactory).getName());
    this.dao = RelationshipTypeJDBCDAO.getInstance();
  }

  public static Test suite() {
    TestSuite suite = new TestSuite();
    suite.addTest(new TestRelationshipTypeJDBCDAO("testCreate"));
    suite.addTest(new TestRelationshipTypeJDBCDAO("testGetAll"));
    suite.addTest(new TestRelationshipTypeJDBCDAO("testGetParentTypes"));
    suite.addTest(new TestRelationshipTypeJDBCDAO("testRemove"));
    return suite;
  }

  protected void tearDown() {
  }

  public void testCreate() {
    try {
      for(int i = 0; i < 3; ++i) {
        RelationshipType relationshipType = new RelationshipTypeImpl("Test " + i, i % 2 == 0);
        this.dao.create(relationshipType);
      }
    } catch (DAOException var3) {
      Assert.fail(var3.toString());
    }

  }

  public void testGetAll() {
    try {
      Map parents = this.dao.getAll();
      Set valuesCollection = parents.keySet();
      Iterator parentsIterator = valuesCollection.iterator();
      System.out.println("Types de relations:");

      while(parentsIterator.hasNext()) {
        Object key = parentsIterator.next();
        System.out.println(key + "=" + parents.get(key));
      }
    } catch (DAOException var5) {
      Assert.fail(var5.toString());
    }

  }

  public void testGetParentTypes() {
    try {
      Collection parents = this.dao.getParentTypes();
      Iterator parentsIterator = parents.iterator();
      System.out.println("Relations parentes:");

      while(parentsIterator.hasNext()) {
        System.out.println(parentsIterator.next());
      }
    } catch (DAOException var3) {
      Assert.fail(var3.toString());
    }

  }

  public void testRemove() {
    try {
      for(int i = 0; i < 3; ++i) {
        this.dao.remove("Test " + i);
      }
    } catch (DAOException var2) {
      Assert.fail(var2.toString());
    }

  }
}
