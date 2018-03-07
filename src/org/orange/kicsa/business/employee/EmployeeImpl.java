package org.orange.kicsa.business.employee;

import java.io.Serializable;
import java.util.Date;
import org.orange.kicsa.business.Editable.Status;

public class EmployeeImpl implements Employee, Serializable {
  private String entityURI;
  private EmployeeKey key;
  private String firstName;
  private String lastName;
  private Date endDate;
  private int addPersonalSkill;
  private int addGeneralSkill;
  private int addEmployee;
  private int seeEmployees;
  private int changeRights;
  private Status status;
  private String entityName;
  private String URI;

  public EmployeeImpl(int someId, String someFirstName, String someLastName, Date someEndDate, String someURI, String someEntityURI, String someEntityName, Status someStatus) {
    this.key = new EmployeeKey(someId);
    this.firstName = someFirstName;
    this.lastName = someLastName;
    this.endDate = someEndDate;
    this.URI = someURI;
    this.entityURI = someEntityURI;
    this.entityName = someEntityName;
    this.status = (Status)someStatus.clone();
  }

  public EmployeeImpl(String someFirstName, String someLastName, Date someEndDate, String someURI, String someEntityURI, String someEntityName) {
    this(-1, someFirstName, someLastName, someEndDate, someURI, someEntityURI, someEntityName, Status.NEW);
  }

  public EmployeeKey getKey() {
    return this.key;
  }

  public int getId() {
    return this.key.getId();
  }

  public void setId(int someId) {
    this.key.setId(someId);
  }

  public String getFirstName() {
    return this.firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return this.lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public Date getEndDate() {
    return this.endDate;
  }

  public void setEndDate(Date endDate) {
    this.endDate = endDate;
  }

  public String toString() {
    return "EmployeeImpl { " + this.key + ", " + this.firstName + ", " + this.lastName + ", " + this.URI + ", " + this.entityURI + ", " + this.entityName + " }";
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

  public String getEntityURI() {
    return this.entityURI;
  }

  public void setEntityURI(String entityURI) {
    this.entityURI = entityURI;
  }

  public String getEntityName() {
    return this.entityName;
  }

  public void setEntityName(String entityName) {
    this.entityName = entityName;
  }

  public String getURI() {
    return this.URI;
  }

  public void setURI(String URI) {
    this.URI = URI;
  }
}
