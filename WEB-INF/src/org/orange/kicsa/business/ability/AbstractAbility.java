package org.orange.kicsa.business.ability;

import java.io.Serializable;
import org.orange.kicsa.business.Editable.Status;
import org.orange.kicsa.business.skill.SkillKey;

public class AbstractAbility implements Ability, Serializable {
  private String skillName;
  private SkillKey skillKey;
  private int level;
  private Status status;

  public AbstractAbility(SkillKey someSkillKey, String someSkillName, int someLevel, Status someStatus) {
    this.skillKey = someSkillKey;
    this.skillName = someSkillName;
    this.level = someLevel;
    this.status = (Status)someStatus.clone();
  }

  public AbstractAbility(SkillKey someSkillKey, String someSkillName, int someLevel) {
    this(someSkillKey, someSkillName, someLevel, Status.NEW);
  }

  public String getSkillName() {
    return this.skillName;
  }

  public void setSkillName(String skillName) {
    this.skillName = skillName;
  }

  public SkillKey getSkillKey() {
    return this.skillKey;
  }

  public void setSkillKey(SkillKey skillKey) {
    this.skillKey = skillKey;
  }

  public int getLevel() {
    return this.level;
  }

  public void setLevel(int level) {
    this.level = level;
  }

  public String toString() {
    return this.skillKey + ", " + this.skillName + ", " + this.level;
  }

  public boolean isSync() {
    return this.status.isSync();
  }

  public void setSync(boolean someSyncState) {
    this.status.setSync(someSyncState);
  }

  public boolean isNew() {
    return this.status.isNew();
  }

  public void setNew(boolean someNewState) {
    this.status.setNew(someNewState);
  }

  public boolean isDirty() {
    return this.status.isDirty();
  }

  public void setDirty(boolean someDirtyState) {
    this.status.setDirty(someDirtyState);
  }

  public boolean isDeleted() {
    return this.status.isDeleted();
  }

  public void setDeleted(boolean someDeletedState) {
    this.status.setDeleted(someDeletedState);
  }
}
