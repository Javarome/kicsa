package org.orange.kicsa.business.employee;

import java.io.Serializable;

public class EmployeeKey implements Serializable {
  private int id;

  public EmployeeKey(int someId) {
    this.id = someId;
  }

  public int getId() {
    return this.id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public boolean equals(Object someObject) {
    boolean equal = super.equals(someObject) && someObject instanceof EmployeeKey;
    if (equal) {
      EmployeeKey otherKey = (EmployeeKey)someObject;
      equal = this.id == otherKey.getId();
    }

    return equal;
  }

  public int hashCode() {
    return this.id;
  }

  public String toString() {
    return String.valueOf(this.id);
  }
}
