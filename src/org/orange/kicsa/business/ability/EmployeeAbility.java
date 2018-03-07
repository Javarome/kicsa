package org.orange.kicsa.business.ability;

import org.orange.kicsa.business.employee.EmployeeKey;

public interface EmployeeAbility extends Ability {
  EmployeeKey getEmployeeKey();

  void setEmployeeKey(EmployeeKey var1);

  String getEmployeeFirstName();

  void setEmployeeFirstName(String var1);

  String getEmployeeLastName();

  void setEmployeeLastName(String var1);
}
