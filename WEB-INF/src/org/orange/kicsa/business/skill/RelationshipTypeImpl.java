package org.orange.kicsa.business.skill;

import java.io.Serializable;
import org.apache.log4j.Logger;
import org.orange.kicsa.business.Editable.Status;

public class RelationshipTypeImpl implements RelationshipType, Serializable {
  private static final Logger log;
  private Status status;
  private String name;
  private boolean parent;

  public RelationshipTypeImpl(String someName, boolean someParentState, Status someStatus) {
    this.parent = someParentState;
    this.name = someName;
    this.status = (Status)someStatus.clone();
  }

  public RelationshipTypeImpl(String someName, boolean someParentState) {
    this(someName, someParentState, Status.NEW);
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

  public String getName() {
    return this.name;
  }

  public boolean isParent() {
    return this.parent;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setParent(boolean parent) {
    this.parent = parent;
  }

  public String toString() {
    return "RelationshipTypeImpl {name=" + this.name + ", parent=" + this.parent + "}";
  }

  static {
    log = Logger.getLogger(class$org$orange$kicsa$business$skill$RelationshipImpl == null ? (class$org$orange$kicsa$business$skill$RelationshipImpl = class$("org.orange.kicsa.business.skill.RelationshipImpl")) : class$org$orange$kicsa$business$skill$RelationshipImpl);
  }
}
