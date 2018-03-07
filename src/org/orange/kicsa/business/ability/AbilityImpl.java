package org.orange.kicsa.business.ability;

import java.io.Serializable;

import org.orange.kicsa.business.Editable.Status;
import org.orange.kicsa.business.employee.EmployeeKey;
import org.orange.kicsa.business.skill.SkillKey;

public class AbilityImpl implements Ability, Serializable {
  private String skillName;
  private EmployeeKey employeeKey;
  private SkillKey skillKey;
  private String employeeFirstName;
  private int level;
  private String employeeLastName;
  private Status status;

  public AbilityImpl(EmployeeKey someEmployeeKey, String someEmployeeFirstName, String someEmployeeLastName, SkillKey someSkillKey, String someSkillName, int someLevel, Status someStatus) {
    this.employeeKey = someEmployeeKey;
    this.employeeFirstName = someEmployeeFirstName;
    this.employeeLastName = someEmployeeLastName;
    this.skillKey = someSkillKey;
    this.skillName = someSkillName;
    this.level = someLevel;
    this.status = (Status) someStatus.clone();
  }

  public AbilityImpl(EmployeeKey someEmployeeKey, String someEmployeeFirstName, String someEmployeeLastName, SkillKey someSkillKey, String someSkillName, int someLevel) {
    this(someEmployeeKey, someEmployeeFirstName, someEmployeeLastName, someSkillKey, someSkillName, someLevel, Status.NEW);
  }

  public String getSkillName() {
    return this.skillName;
  }

  public void setSkillName(String skillName) {
    this.skillName = skillName;
  }

  public EmployeeKey getEmployeeKey() {
    return this.employeeKey;
  }

  public void setEmployeeKey(EmployeeKey employeeKey) {
    this.employeeKey = employeeKey;
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
    return "AbilityImpl { " + this.employeeKey + ", " + this.employeeFirstName + ", " + this.employeeLastName + ", " + this.skillKey + ", " + this.skillName + ", " + this.level + " }";
  }

  public String getEmployeeFirstName() {
    return this.employeeFirstName;
  }

  public void setEmployeeFirstName(String employeeFirstName) {
    this.employeeFirstName = employeeFirstName;
  }

  public String getEmployeeLastName() {
    return this.employeeLastName;
  }

  public void setEmployeeLastName(String employeeLastName) {
    this.employeeLastName = employeeLastName;
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
