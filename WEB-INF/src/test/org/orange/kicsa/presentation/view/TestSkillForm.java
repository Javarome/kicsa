package test.org.orange.kicsa.presentation.view;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.SortedMap;
import junit.framework.Assert;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.apache.commons.beanutils.PropertyUtils;
import org.orange.kicsa.Application;
import org.orange.kicsa.business.employee.Employee;
import org.orange.kicsa.business.employee.EmployeeImpl;
import org.orange.kicsa.business.skill.Relationship;
import org.orange.kicsa.business.skill.RelationshipImpl;
import org.orange.kicsa.business.skill.Skill;
import org.orange.kicsa.business.skill.SkillImpl;
import org.orange.kicsa.business.skill.SkillKey;
import org.orange.kicsa.presentation.view.SkillForm;
import org.orange.util.Preferences;
import org.orange.util.integration.JDBCDAO;

public class TestSkillForm extends TestCase {
  static SkillForm form;

  public TestSkillForm(String name) {
    super(name);
  }

  public static Test suite() {
    TestSuite suite = new TestSuite();
    suite.addTest(new TestSkillForm("testInit"));
    suite.addTest(new TestSkillForm("testToString"));
    suite.addTest(new TestSkillForm("testProperties"));
    return suite;
  }

  protected void setUp() {
    try {
      Preferences preferences = Preferences.systemNodeForPackage(class$org$orange$kicsa$integration$skill$SkillJDBCDAO == null ? (class$org$orange$kicsa$integration$skill$SkillJDBCDAO = class$("org.orange.kicsa.integration.skill.SkillJDBCDAO")) : class$org$orange$kicsa$integration$skill$SkillJDBCDAO);
      preferences.put(JDBCDAO.CONNECTION_FACTORY_CLASS_NAME, (class$org$orange$util$integration$DriverJDBCConnectionFactory == null ? (class$org$orange$util$integration$DriverJDBCConnectionFactory = class$("org.orange.util.integration.DriverJDBCConnectionFactory")) : class$org$orange$util$integration$DriverJDBCConnectionFactory).getName());
      preferences = Preferences.systemNodeForPackage(class$org$orange$kicsa$integration$skill$RelationshipTypeJDBCDAO == null ? (class$org$orange$kicsa$integration$skill$RelationshipTypeJDBCDAO = class$("org.orange.kicsa.integration.skill.RelationshipTypeJDBCDAO")) : class$org$orange$kicsa$integration$skill$RelationshipTypeJDBCDAO);
      preferences.put(JDBCDAO.CONNECTION_FACTORY_CLASS_NAME, (class$org$orange$util$integration$DriverJDBCConnectionFactory == null ? (class$org$orange$util$integration$DriverJDBCConnectionFactory = class$("org.orange.util.integration.DriverJDBCConnectionFactory")) : class$org$orange$util$integration$DriverJDBCConnectionFactory).getName());
      Application.init((URL)null);
    } catch (Throwable var2) {
      Assert.fail(var2.toString());
    }

  }

  protected void tearDown() {
  }

  public void testInit() {
    try {
      form = (SkillForm)(class$org$orange$kicsa$presentation$view$SkillForm == null ? (class$org$orange$kicsa$presentation$view$SkillForm = class$("org.orange.kicsa.presentation.view.SkillForm")) : class$org$orange$kicsa$presentation$view$SkillForm).newInstance();
      Assert.assertNotNull("Le formulaire est nul", form);
      Skill skill = new SkillImpl((String)null, (String)null, (String)null, (SortedMap)null);
      form.setSkill(skill);
      Employee newOwner = new EmployeeImpl("", "", (Date)null, "", "", "");
      Collection owners = new ArrayList();
      owners.add(newOwner);
      form.setOwners(owners);
      Collection relationships = new ArrayList();
      Relationship newRelationship = new RelationshipImpl((Skill)null, "", new SkillKey(0), "");
      relationships.add(newRelationship);
      form.setRelationships(relationships);
      System.out.println("Relationships:" + form.getRelationships());
      System.out.println("Owners:" + form.getOwners());
    } catch (Throwable var6) {
      Assert.fail(var6.toString());
    }

  }

  public void testToString() {
    System.out.println(form);
  }

  public void testProperties() {
    Assert.assertNotNull("The form is null", form);

    try {
      Object[][] properties = new Object[][]{{"skill.shortName", "someShortName"}, {"skill.longName", "someLongName"}};

      for(int i = 0; i < properties.length; ++i) {
        System.out.println("setProperty (" + properties[i][0] + ", " + properties[i][1] + ")");
        PropertyUtils.setProperty(form, (String)properties[i][0], properties[i][1]);
      }

      for(int i = 0; i < properties.length; ++i) {
        Object value = PropertyUtils.getProperty(form, (String)properties[i][0]);
        System.out.println(properties[i][0] + "=" + value);
        Assert.assertEquals(value, properties[i][1]);
      }
    } catch (Throwable var5) {
      Assert.fail(var5.toString());
    }

  }

  public void testValidate() {
    Assert.fail("not implemented");
  }
}
