package org.orange.kicsa.service.admin;

import java.util.Map;
import org.orange.kicsa.business.skill.RelationshipType;
import org.orange.kicsa.business.skill.Skill;
import org.orange.kicsa.business.skill.SkillKey;

public interface AdminService {
  void update(Skill var1, Map var2);

  RelationshipType createRelationshipType(RelationshipType var1);

  void createSkillLevel(SkillKey var1, String var2, String var3, int var4);
}
