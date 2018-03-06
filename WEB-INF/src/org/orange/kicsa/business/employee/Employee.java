package org.orange.kicsa.business.employee;

import java.util.Date;
import org.orange.kicsa.business.Editable;

public interface Employee extends Editable {
  String getLastName();

  void setFirstName(String var1);

  int getId();

  void setId(int var1);

  String getURI();

  void setURI(String var1);

  String getEntityURI();

  void setEntityURI(String var1);

  String getFirstName();

  void setEndDate(Date var1);

  void setLastName(String var1);

  Date getEndDate();

  String toString();

  EmployeeKey getKey();

  String getEntityName();

  void setEntityName(String var1);
}
