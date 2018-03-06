package org.orange.kicsa.business.skill;

import java.io.Serializable;
import org.apache.log4j.Logger;
import org.orange.kicsa.business.Editable.Status;

public class RelationshipImpl implements Relationship, Serializable {
  private static final Logger log;
  private Status status;
  private String type;
  private Skill source;
  private SkillKey destinationKey;
  private String destinationName;

  public RelationshipImpl(Skill someSourceSkill, String someType, SkillKey someDestinationKey, String someDestinationName, Status someStatus) {
    this.destinationKey = someDestinationKey;
    this.destinationName = someDestinationName;
    this.type = someType;
    this.source = someSourceSkill;
    this.status = (Status)someStatus.clone();
  }

  public RelationshipImpl(Skill someSourceSkill, String someType, SkillKey someDestinationKey, String someDestinationName) {
    this(someSourceSkill, someType, someDestinationKey, someDestinationName, Status.NEW);
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
    if (log.isDebugEnabled()) {
      log.debug("setDeleted(" + someDeletedState + ")");
    }

    this.status.setDeleted(someDeletedState);
  }

  public String getType() {
    return this.type;
  }

  public SkillKey getDestinationKey() {
    if (log.isDebugEnabled()) {
      log.debug("getDestinationKey ()=" + this.destinationKey);
    }

    return this.destinationKey;
  }

  public Skill getSource() {
    return this.source;
  }

  public String getDestinationName() {
    return this.destinationName;
  }

  public void setType(String someType) {
    if (log.isDebugEnabled()) {
      log.debug("setType (" + someType + ")");
    }

    this.type = someType;
  }

  public void setDestinationKey(SkillKey someDestinationKey) {
    if (log.isDebugEnabled()) {
      log.debug("setDestinationKey (" + someDestinationKey + ")");
    }

    this.destinationKey = someDestinationKey;
  }

  public void setDestinationName(String someDestinationName) {
    if (log.isDebugEnabled()) {
      log.debug("setDestinationName (" + someDestinationName + ")");
    }

    this.destinationName = someDestinationName;
  }

  public String toString() {
    return "RelationshipImpl {source=" + (this.source == null ? "null" : this.source.getShortName()) + ", type=" + this.type + ", destinationKey=" + this.destinationKey + ", destinationName=" + this.destinationName + ", status=" + this.status + "}";
  }

  static {
    log = Logger.getLogger(class$org$orange$kicsa$business$skill$RelationshipImpl == null ? (class$org$orange$kicsa$business$skill$RelationshipImpl = class$("org.orange.kicsa.business.skill.RelationshipImpl")) : class$org$orange$kicsa$business$skill$RelationshipImpl);
  }
}
