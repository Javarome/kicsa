package org.orange.kicsa.service.skill;

import java.util.Collection;
import java.util.Map;
import org.orange.kicsa.SkillNotFoundException;
import org.orange.kicsa.business.skill.RelationshipType;
import org.orange.kicsa.business.skill.Skill;
import org.orange.kicsa.business.skill.SkillKey;

public interface SkillService {
  Skill findByPrimaryKey(SkillKey var1) throws SkillNotFoundException;

  Skill create(Skill var1, Collection var2, Collection var3);

  Skill update(Skill var1, Collection var2, Collection var3);

  int remove(SkillKey var1);

  Skill createRoot(Skill var1);

  Collection findChilds(Skill var1) throws SkillNotFoundException;

  Collection findParents(Skill var1) throws SkillNotFoundException;

  void link(Skill var1, String var2, SkillKey var3);

  Collection findDestinations(Skill var1) throws SkillNotFoundException;

  Collection findDestinations(Skill var1, String var2) throws SkillNotFoundException;

  Collection findSources(Skill var1, String var2) throws SkillNotFoundException;

  Map findAll();

  Map findRelationshipTypes();

  RelationshipType createRelationshipType(RelationshipType var1);

  void removeRelationshipType(String var1);
}
