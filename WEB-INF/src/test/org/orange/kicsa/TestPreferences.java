package test.org.orange.kicsa;

import junit.framework.Assert;
import junit.framework.TestCase;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.orange.kicsa.service.skill.SkillServicePool;
import org.orange.util.Preferences;

public class TestPreferences extends TestCase {
  public TestPreferences(String name) {
    super(name);
  }

  protected void setUp() {
    Logger.getRootLogger().setLevel(Level.DEBUG);
  }

  protected void tearDown() {
  }

  public void testSystemNodeForPackage() {
    Preferences prefs = Preferences.systemNodeForPackage(class$org$orange$kicsa$service$skill$SkillServicePool == null ? (class$org$orange$kicsa$service$skill$SkillServicePool = class$("org.orange.kicsa.service.skill.SkillServicePool")) : class$org$orange$kicsa$service$skill$SkillServicePool);
    System.out.println(prefs.getInt("initCount", 1));
    System.out.println(prefs.getInt("maxCount", 1));
    System.out.println(prefs.get("poolableObjectFactory", SkillServicePool.POOLABLE_OBJECT_FACTORY_CLASS_NAME_DEFAULT));
    String EXPECTED = "OkDefault";
    Assert.assertEquals(prefs.get("unknown", "OkDefault"), "OkDefault");
  }
}
