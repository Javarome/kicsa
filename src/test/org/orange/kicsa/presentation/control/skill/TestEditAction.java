package test.org.orange.kicsa.presentation.control.skill;

import junit.framework.Assert;
import junit.framework.Test;
import junit.framework.TestSuite;
import servletunit.struts.MockStrutsTestCase;

public class TestEditAction extends MockStrutsTestCase {
  public TestEditAction(String name) {
    super(name);
  }

  public static Test suite() {
    TestSuite suite = new TestSuite();
    suite.addTest(new TestEditAction("testUpdate"));
    return suite;
  }

  public void setUp() {
    try {
      super.setUp();
    } catch (Exception var2) {
      Assert.fail(var2.toString());
    }

  }

  public void tearDown() {
    try {
      super.tearDown();
    } catch (Exception var2) {
      Assert.fail(var2.toString());
    }

  }

  public void testCreate() {
    this.setRequestPathInfo("/SkillEdit");
    this.addRequestParameter("create", "true");
    this.actionPerform();
    this.verifyForward("CREATED");
  }

  public void testUpdate() {
    this.setRequestPathInfo("/SkillEdit");
    this.addRequestParameter("id", "5");
    this.actionPerform();
    this.verifyForward("UPDATED");
  }
}
