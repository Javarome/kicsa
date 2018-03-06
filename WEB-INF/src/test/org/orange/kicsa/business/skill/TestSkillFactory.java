package test.org.orange.kicsa.business.skill;

import java.net.URL;
import junit.framework.Assert;
import junit.framework.TestCase;
import org.orange.kicsa.Application;

public class TestSkillFactory extends TestCase {
  public TestSkillFactory(String name) {
    super(name);
  }

  protected void setUp() {
    try {
      Application.init((URL)null);
    } catch (Throwable var2) {
      Assert.fail(var2.toString());
    }

  }

  protected void tearDown() {
  }

  public void testGetRootId() {
    try {
      System.out.println("getRootId=" + Application.getSkillFactory().getRootId());
    } catch (Throwable var2) {
      Assert.fail(var2.toString());
    }

  }

  public void testGetRoot() {
    try {
      System.out.println("getRoot=" + Application.getSkillFactory().getRoot());
    } catch (Throwable var2) {
      Assert.fail(var2.toString());
    }

  }
}
