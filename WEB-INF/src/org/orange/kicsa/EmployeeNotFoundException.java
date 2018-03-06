package org.orange.kicsa;

import org.orange.kicsa.business.employee.EmployeeKey;

public class EmployeeNotFoundException extends Exception {
  private EmployeeKey key;

  public EmployeeNotFoundException(EmployeeKey someKey) {
    this.setKey(someKey);
  }

  public EmployeeKey getKey() {
    return this.key;
  }

  private void setKey(EmployeeKey someKey) {
    this.key = someKey;
  }

  public String toString() {
    return "L'employé " + this.getKey() + " n'a pas été trouvé";
  }
}
