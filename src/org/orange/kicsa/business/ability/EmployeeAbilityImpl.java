package org.orange.kicsa.business.ability;

import java.io.Serializable;

import org.orange.kicsa.business.Editable.Status;
import org.orange.kicsa.business.employee.EmployeeKey;
import org.orange.kicsa.business.skill.SkillKey;

public class EmployeeAbilityImpl extends AbstractAbility implements EmployeeAbility, Serializable {
  private EmployeeKey employeeKey;
  private String employeeFirstName;
  private String employeeLastName;

  public EmployeeAbilityImpl(EmployeeKey someEmployeeKey, String someEmployeeFirstName, String someEmployeeLastName, SkillKey someSkillKey, String someSkillName, int someLevel, Status someStatus) {
    super(someSkillKey, someSkillName, someLevel, someStatus);
    this.employeeKey = someEmployeeKey;
    this.employeeFirstName = someEmployeeFirstName;
    this.employeeLastName = someEmployeeLastName;
  }

  public EmployeeAbilityImpl(EmployeeKey someEmployeeKey, String someEmployeeFirstName, String someEmployeeLastName, SkillKey someSkillKey, String someSkillName, int someLevel) {
    this(someEmployeeKey, someEmployeeFirstName, someEmployeeLastName, someSkillKey, someSkillName, someLevel, Status.NEW);
  }

  public EmployeeKey getEmployeeKey() {
    return this.employeeKey;
  }

  public void setEmployeeKey(EmployeeKey employeeKey) {
    this.employeeKey = employeeKey;
  }

  public String toString() {
    return "EmployeeAbilityImpl { " + super.toString() + ", " + this.employeeKey + ", " + this.employeeFirstName + ", " + this.employeeLastName + "}";
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
}
