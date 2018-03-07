package org.orange.kicsa.service.employee;

import java.util.Map;
import org.orange.kicsa.EmployeeNotFoundException;
import org.orange.kicsa.business.employee.Employee;
import org.orange.kicsa.business.employee.EmployeeKey;

public interface EmployeeService {
  Employee findByPrimaryKey(EmployeeKey var1) throws EmployeeNotFoundException;

  Map findAll();

  Employee create(Employee var1);

  Employee update(Employee var1);

  void remove(EmployeeKey var1);
}
