package org.orange.kicsa.integration.employee;

import java.util.Map;
import org.orange.kicsa.EmployeeNotFoundException;
import org.orange.kicsa.business.employee.Employee;
import org.orange.kicsa.business.employee.EmployeeKey;
import org.orange.util.integration.DAOException;

public interface EmployeeDAO {
  Map getAll() throws DAOException;

  Employee getByPrimaryKey(EmployeeKey var1) throws DAOException, EmployeeNotFoundException;

  Employee create(Employee var1) throws DAOException;

  Employee update(Employee var1) throws DAOException;

  void remove(EmployeeKey var1) throws DAOException;
}
