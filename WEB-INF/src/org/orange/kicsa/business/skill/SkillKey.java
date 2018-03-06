package org.orange.kicsa.business.skill;

import java.io.Serializable;
import org.apache.log4j.Logger;

public class SkillKey implements Serializable {
  static final Logger log;
  private int id;

  public SkillKey(int someId) {
    this.id = someId;
  }

  public SkillKey(String someStringId) {
    this.id = Integer.parseInt(someStringId);
  }

  public int getId() {
    return this.id;
  }

  public void setId(int someId) {
    this.id = someId;
  }

  public boolean equals(Object someObject) {
    boolean equal = someObject instanceof SkillKey;
    if (equal) {
      SkillKey otherSkillKey = (SkillKey)someObject;
      equal = this.id == otherSkillKey.getId();
    }

    return equal;
  }

  public int hashCode() {
    return this.id;
  }

  public String toString() {
    return String.valueOf(this.id);
  }

  static {
    log = Logger.getLogger(class$org$orange$kicsa$business$skill$SkillKey == null ? (class$org$orange$kicsa$business$skill$SkillKey = class$("org.orange.kicsa.business.skill.SkillKey")) : class$org$orange$kicsa$business$skill$SkillKey);
  }
}
