package org.orange.kicsa.business.ability;

import org.orange.kicsa.business.Editable;
import org.orange.kicsa.business.skill.SkillKey;

public interface Ability extends Editable {
  int getLevel();

  void setLevel(int var1);

  SkillKey getSkillKey();

  void setSkillKey(SkillKey var1);

  String getSkillName();

  void setSkillName(String var1);
}
