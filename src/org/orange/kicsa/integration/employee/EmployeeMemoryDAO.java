
package org.orange.kicsa.integration.employee;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import org.orange.kicsa.EmployeeNotFoundException;
import org.orange.kicsa.business.ability.Ability;
import org.orange.kicsa.business.employee.Employee;
import org.orange.kicsa.business.employee.EmployeeKey;
import org.orange.util.integration.DAOException;

public class EmployeeMemoryDAO implements EmployeeDAO {
  private Map abilitiesCache = new HashMap();
  private static EmployeeMemoryDAO instance;
  private static Map allEmployeesCache;
  private static Map keyCache = new HashMap();

  public EmployeeMemoryDAO() throws DAOException {
  }

  public Employee getByPrimaryKey(EmployeeKey someKey) throws DAOException, EmployeeNotFoundException {
    Employee employee = (Employee)keyCache.get(someKey);
    return employee;
  }

  public void setByPrimaryKey(EmployeeKey someKey, Employee someEmployee) throws DAOException {
    keyCache.put(someKey, someEmployee);
  }

  public Employee create(Employee someEmployee) throws DAOException {
    int newId = keyCache.size();
    someEmployee.setId(newId);
    keyCache.put(someEmployee.getKey(), someEmployee);
    return someEmployee;
  }

  public Map getAll() throws DAOException {
    return allEmployeesCache;
  }

  public void setAll(Map someAll) {
    allEmployeesCache = someAll;
  }

  public static EmployeeDAO getInstance() throws DAOException {
    if (instance == null) {
      Class var0 = class$org$orange$kicsa$integration$employee$EmployeeMemoryDAO == null ? (class$org$orange$kicsa$integration$employee$EmployeeMemoryDAO = class$("org.orange.kicsa.integration.employee.EmployeeMemoryDAO")) : class$org$orange$kicsa$integration$employee$EmployeeMemoryDAO;
      synchronized(var0) {
        instance = new EmployeeMemoryDAO();
      }
    }

    return instance;
  }

  public Collection getAbilities(EmployeeKey someKey) {
    Collection abilities = (Collection)this.abilitiesCache.get(someKey);
    if (abilities == null) {
      abilities = new ArrayList();
    }

    return (Collection)abilities;
  }

  public void remove(EmployeeKey someKey) throws DAOException {
    keyCache.remove(someKey);
  }

  public Ability create(Ability someAbility) throws DAOException {
    throw new RuntimeException("Non implémenté");
  }

  public Employee update(Employee someEmployee) throws DAOException {
    keyCache.put(someEmployee.getKey(), someEmployee);
    return someEmployee;
  }
}
