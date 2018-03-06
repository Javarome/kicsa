package org.orange.kicsa.business.skill;

import org.orange.kicsa.business.Editable;

public interface Relationship extends Editable {
  String getType();

  void setType(String var1);

  Skill getSource();

  SkillKey getDestinationKey();

  void setDestinationKey(SkillKey var1);

  String getDestinationName();

  void setDestinationName(String var1);
}
