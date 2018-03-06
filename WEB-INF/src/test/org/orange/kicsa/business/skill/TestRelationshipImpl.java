package test.org.orange.kicsa.business.skill;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import junit.framework.Assert;
import junit.framework.TestCase;
import org.orange.kicsa.business.skill.Relationship;
import org.orange.kicsa.business.skill.RelationshipImpl;
import org.orange.kicsa.business.skill.Skill;
import org.orange.kicsa.business.skill.SkillImpl;
import org.orange.kicsa.business.skill.SkillKey;

public class TestRelationshipImpl extends TestCase {
  static Relationship createdRelationship;

  public TestRelationshipImpl(String name) {
    super(name);
  }

  protected void setUp() {
  }

  protected void tearDown() {
  }

  public void testInit() {
    Skill fromSkill = new SkillImpl("testShortName", "testLongName", "testComments", (SortedMap)null);
    SkillKey destSkillKey = new SkillKey(0);
    String destSkillName = "dummy skill destination";
    String relationshipType = "Parent";
    createdRelationship = new RelationshipImpl(fromSkill, relationshipType, destSkillKey, destSkillName);
    destSkillKey = new SkillKey(1);
    destSkillName = "dummy 2 skill destination";
    relationshipType = "Use";
    Relationship createdRelationship2 = new RelationshipImpl(fromSkill, relationshipType, destSkillKey, destSkillName);
    List relationships = new ArrayList();
    relationships.add(createdRelationship);
    relationships.add(createdRelationship2);
    createdRelationship2.setDeleted(true);
    System.out.println(createdRelationship);
    Assert.assertTrue("Editable state is not NEW", createdRelationship.isNew());
    System.out.println(createdRelationship2);
    Assert.assertTrue("Editable state is not DELETED", createdRelationship2.isDeleted());
  }

  public void testToString() {
    Assert.assertNotNull("Created relationship is null", createdRelationship);
    System.out.println(createdRelationship);
  }
}
