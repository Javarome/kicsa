package org.orange.kicsa.business.skill;

import java.util.SortedMap;
import org.orange.kicsa.business.Editable;

public interface Skill extends Editable {
  void setLevels(SortedMap var1);

  int getId();

  void setId(int var1);

  String getShortName();

  String getLongName();

  String getComments();

  String toString();

  SortedMap getLevels();

  void setShortName(String var1);

  void setLongName(String var1);

  SkillKey getKey();
}
