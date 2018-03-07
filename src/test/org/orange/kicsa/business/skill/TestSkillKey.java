package test.org.orange.kicsa.business.skill;

import junit.framework.Assert;
import junit.framework.TestCase;
import org.orange.kicsa.business.skill.SkillKey;

public class TestSkillKey extends TestCase {
  public TestSkillKey(String name) {
    super(name);
  }

  protected void setUp() {
  }

  protected void tearDown() {
  }

  public void testEquals() {
    SkillKey firstKey = new SkillKey(0);
    SkillKey secondKey = new SkillKey(1);
    SkillKey thirdKey = new SkillKey(0);
    Assert.assertEquals(firstKey, thirdKey);
    Assert.assertTrue(!firstKey.equals(secondKey));
  }
}
