package org.orange.kicsa.business.skill;

import org.orange.util.BackingStoreException;

public interface SkillFactory {
  Object makeObject();

  int getRootId();

  Skill getRoot();

  void setRoot(Skill var1) throws BackingStoreException;

  SkillTree getTree();

  SkillKey getRootKey();
}
