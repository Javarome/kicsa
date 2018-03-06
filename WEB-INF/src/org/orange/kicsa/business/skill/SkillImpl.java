package org.orange.kicsa.business.skill;

import java.io.Serializable;
import java.util.SortedMap;
import org.orange.kicsa.business.Editable.Status;

public class SkillImpl implements Skill, Serializable {
  private Status status;
  private String shortName;
  private String longName;
  private String comments;
  private SkillKey key;
  private int ownerId;
  private SortedMap levels;

  public SkillImpl(int someId, String someShortName, String someLongName, String someComments, SortedMap someLevels, Status someStatus) {
    this.key = new SkillKey(someId);
    this.setShortName(someShortName);
    this.setLongName(someLongName);
    this.comments = someComments;
    this.setLevels(someLevels);
    this.status = (Status)someStatus.clone();
  }

  public SkillImpl(int someId, String someShortName, String someLongName, String someComments, SortedMap someLevels) {
    this(someId, someShortName, someLongName, someComments, someLevels, Status.NEW);
  }

  public SkillImpl(String someShortName, String someLongName, String someComments, SortedMap someLevels) {
    this(0, someShortName, someLongName, someComments, someLevels);
  }

  public String getShortName() {
    return this.shortName;
  }

  public void setShortName(String shortName) {
    this.shortName = shortName;
  }

  public String getLongName() {
    return this.longName;
  }

  public void setLongName(String longName) {
    this.longName = longName;
  }

  public String getComments() {
    return this.comments;
  }

  public void setComments(String comments) {
    this.comments = comments;
  }

  public int getId() {
    return this.key.getId();
  }

  public String toString() {
    return "SkillImpl {id=" + this.getId() + ", shortName=" + this.getShortName() + ", longName=" + this.getLongName() + ", levels=" + this.getLevels() + ", comments=" + this.getComments() + " }";
  }

  public boolean equals(Object other) {
    boolean equal = other instanceof Skill;
    if (equal) {
      Skill otherSkill = (Skill)other;
      equal = this.key.getId() == otherSkill.getId();
    }

    return equal;
  }

  public int hashCode() {
    return this.key.getId();
  }

  public SortedMap getLevels() {
    return this.levels;
  }

  public void setLevels(SortedMap someLevels) {
    this.levels = someLevels;
  }

  public SkillKey getKey() {
    return this.key;
  }

  public void setId(int someId) {
    this.key.setId(someId);
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
