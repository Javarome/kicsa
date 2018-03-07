package org.orange.kicsa;

import org.orange.kicsa.business.skill.SkillKey;

public class SkillNotFoundException extends Exception {
  private SkillKey key;

  public SkillNotFoundException(SkillKey someKey) {
    this.setKey(someKey);
  }

  public SkillKey getKey() {
    return this.key;
  }

  private void setKey(SkillKey someKey) {
    this.key = someKey;
  }

  public String toString() {
    return "La compétence " + this.getKey() + " n'a pas été trouvée";
  }
}
