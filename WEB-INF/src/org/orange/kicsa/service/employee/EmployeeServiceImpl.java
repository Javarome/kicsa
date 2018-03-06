package org.orange.kicsa.service.employee;

import java.util.Map;
import org.apache.log4j.Logger;
import org.orange.kicsa.Application;
import org.orange.kicsa.EmployeeNotFoundException;
import org.orange.kicsa.business.employee.Employee;
import org.orange.kicsa.business.employee.EmployeeKey;
import org.orange.kicsa.integration.AbstractDAOFactory;
import org.orange.kicsa.integration.employee.EmployeeDAO;
import org.orange.kicsa.service.ServiceException;
import org.orange.util.Assert;
import org.orange.util.integration.DAOException;

public class EmployeeServiceImpl implements EmployeeService {
  private static EmployeeServiceImpl instance;
  protected Logger log = Logger.getLogger(this.getClass());
  protected EmployeeDAO employeeDAO;

  protected EmployeeServiceImpl() {
    try {
      AbstractDAOFactory daoFactory = Application.getDAOFactory();
      this.employeeDAO = daoFactory.createEmployeeDAO();
    } catch (Throwable var2) {
      throw new ServiceException(var2);
    }
  }

  public Map findAll() {
    try {
      return this.employeeDAO.getAll();
    } catch (DAOException var2) {
      throw new ServiceException(var2);
    }
  }

  public Employee findByPrimaryKey(EmployeeKey someKey) throws EmployeeNotFoundException {
    try {
      return this.employeeDAO.getByPrimaryKey(someKey);
    } catch (DAOException var3) {
      throw new ServiceException(var3);
    }
  }

  public static EmployeeServiceImpl getInstance() {
    if (instance == null) {
      Class var0 = class$org$orange$kicsa$service$employee$EmployeeServiceImpl == null ? (class$org$orange$kicsa$service$employee$EmployeeServiceImpl = class$("org.orange.kicsa.service.employee.EmployeeServiceImpl")) : class$org$orange$kicsa$service$employee$EmployeeServiceImpl;
      synchronized(var0) {
        if (instance == null) {
          instance = new EmployeeServiceImpl();
        }
      }
    }

    return instance;
  }

  public Employee create(Employee someEmployee) {
    Assert.notNullArgument(someEmployee, "L'employé à créer ne peut être nul");

    try {
      Employee newEmployee = this.employeeDAO.create(someEmployee);
      return newEmployee;
    } catch (DAOException var3) {
      throw new ServiceException("Impossible de créer l'employé " + someEmployee, var3);
    }
  }

  public Employee update(Employee someEmployee) {
    Assert.notNullArgument(someEmployee, "L'employé à modifier ne peut être nul");

    try {
      Employee newEmployee = this.employeeDAO.update(someEmployee);
      return newEmployee;
    } catch (DAOException var3) {
      throw new ServiceException("Impossible de modifier l'employé " + someEmployee, var3);
    }
  }

  public void remove(EmployeeKey someEmployeeKey) {
    Assert.notNull(this.employeeDAO, "Le DAO d'employés ne peut être nul");

    try {
      this.employeeDAO.remove(someEmployeeKey);
    } catch (DAOException var3) {
      throw new ServiceException(var3);
    }
  }
}
