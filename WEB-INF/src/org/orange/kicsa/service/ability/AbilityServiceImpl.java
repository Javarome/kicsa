package org.orange.kicsa.service.ability;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.transaction.UserTransaction;
import org.apache.log4j.Logger;
import org.orange.kicsa.Application;
import org.orange.kicsa.business.ability.Ability;
import org.orange.kicsa.business.ability.EmployeeAbility;
import org.orange.kicsa.business.employee.Employee;
import org.orange.kicsa.business.employee.EmployeeKey;
import org.orange.kicsa.business.skill.Skill;
import org.orange.kicsa.business.skill.SkillKey;
import org.orange.kicsa.integration.AbstractDAOFactory;
import org.orange.kicsa.integration.ability.AbilityDAO;
import org.orange.kicsa.service.ServiceException;
import org.orange.kicsa.service.employee.EmployeeService;
import org.orange.kicsa.service.employee.EmployeeServicePool;
import org.orange.kicsa.service.skill.SkillService;
import org.orange.kicsa.service.skill.SkillServicePool;
import org.orange.util.Assert;
import org.orange.util.integration.DAOException;

public class AbilityServiceImpl implements AbilityService {
  protected AbilityDAO abilityDAO;
  protected Logger log = Logger.getLogger(this.getClass());
  private static AbilityServiceImpl instance;
  EmployeeServicePool employeeServicePool;
  SkillServicePool skillServicePool;

  protected AbilityServiceImpl() {
    try {
      AbstractDAOFactory daoFactory = Application.getDAOFactory();
      this.abilityDAO = daoFactory.createAbilityDAO();
      this.employeeServicePool = EmployeeServicePool.getInstance();
      this.skillServicePool = SkillServicePool.getInstance();
    } catch (Throwable var2) {
      throw new ServiceException(var2);
    }
  }

  public static AbilityServiceImpl getInstance() {
    if (instance == null) {
      Class var0 = class$org$orange$kicsa$service$ability$AbilityServiceImpl == null ? (class$org$orange$kicsa$service$ability$AbilityServiceImpl = class$("org.orange.kicsa.service.ability.AbilityServiceImpl")) : class$org$orange$kicsa$service$ability$AbilityServiceImpl;
      synchronized(var0) {
        if (instance == null) {
          instance = new AbilityServiceImpl();
        }
      }
    }

    return instance;
  }

  public Collection findByEmployee(Employee someEmployee) {
    Assert.notNull(this.abilityDAO, "Le DAO d'employés ne peut être nul");

    try {
      Collection abilities = this.abilityDAO.getByEmployee(someEmployee.getKey());
      return abilities;
    } catch (DAOException var3) {
      throw new ServiceException(var3);
    }
  }

  public Ability create(EmployeeAbility someAbility) {
    try {
      return this.abilityDAO.create(someAbility);
    } catch (DAOException var3) {
      throw new ServiceException(var3);
    }
  }

  public void remove(EmployeeAbility someAbility) {
    try {
      this.abilityDAO.remove(someAbility);
    } catch (DAOException var3) {
      throw new ServiceException(var3);
    }
  }

  public Employee createEmployee(Employee someEmployee, Collection someAbilities, Collection someRights) {
    Assert.notNullArgument(someAbilities, "Employee's abilities cannot be null");
    Iterator abilitiesIterator = someAbilities.iterator();
    Assert.notEmptyArgument(abilitiesIterator, "The employee to create must have one ability at least");
    UserTransaction currentTransaction = null;

    try {
      InitialContext namingContext = new InitialContext();
      currentTransaction = (UserTransaction)namingContext.lookup("java:comp/UserTransaction");
      currentTransaction.begin();
      EmployeeService employeeService = (EmployeeService)this.employeeServicePool.borrowObject();

      Employee newEmployee;
      try {
        newEmployee = employeeService.create(someEmployee);
      } finally {
        this.employeeServicePool.returnObject(employeeService);
      }

      do {
        EmployeeAbility ability = (EmployeeAbility)abilitiesIterator.next();
        this.create(ability);
      } while(abilitiesIterator.hasNext());

      currentTransaction.commit();
      return newEmployee;
    } catch (NamingException var16) {
      this.log.error(var16);
      throw new ServiceException("Impossible de récupérer java:comp/UserTransaction dans le service de nommage", var16);
    } catch (DAOException var17) {
      this.log.error(var17);
      throw new ServiceException("Impossible de créer l'employe " + someEmployee, var17);
    } catch (Exception var18) {
      this.log.error(var18);
      throw new ServiceException(var18);
    }
  }

  public Employee updateEmployee(Employee someEmployee, Collection someAbilities, Collection someRights) {
    Assert.notNullArgument(someAbilities, "Employee's abilities cannot be null");
    Iterator abilitiesIterator = someAbilities.iterator();
    Assert.notEmptyArgument(abilitiesIterator, "The employee must have at least one ability");
    UserTransaction currentTransaction = null;

    try {
      InitialContext namingContext = new InitialContext();
      currentTransaction = (UserTransaction)namingContext.lookup("java:comp/UserTransaction");
      currentTransaction.begin();
      EmployeeService employeeService = (EmployeeService)this.employeeServicePool.borrowObject();

      Employee newEmployee;
      try {
        newEmployee = employeeService.update(someEmployee);
      } finally {
        this.employeeServicePool.returnObject(employeeService);
      }

      do {
        EmployeeAbility ability = (EmployeeAbility)abilitiesIterator.next();
        if (ability.isNew()) {
          this.create(ability);
        } else if (ability.isDeleted()) {
          this.remove(ability);
        } else if (ability.isDirty()) {
          this.abilityDAO.update(ability);
        } else if (ability.isSync()) {
          this.log.info("Nothing to do for " + ability);
        } else {
          this.log.error("Editable state not supported for " + ability);
        }
      } while(abilitiesIterator.hasNext());

      currentTransaction.commit();
      return newEmployee;
    } catch (NamingException var16) {
      this.log.error(var16);
      throw new ServiceException("Could not retrieve java:comp/UserTransaction from the naming service", var16);
    } catch (DAOException var17) {
      this.log.error(var17);
      throw new ServiceException("Could not update employee " + someEmployee, var17);
    } catch (Exception var18) {
      this.log.error(var18);
      throw new ServiceException(var18);
    }
  }

  public int removeEmployee(EmployeeKey someEmployeeKey) {
    UserTransaction currentTransaction = null;

    try {
      InitialContext namingContext = new InitialContext();
      currentTransaction = (UserTransaction)namingContext.lookup("java:comp/UserTransaction");
      currentTransaction.begin();
      EmployeeService employeeService = (EmployeeService)this.employeeServicePool.borrowObject();

      try {
        employeeService.remove(someEmployeeKey);
      } finally {
        this.employeeServicePool.returnObject(employeeService);
      }

      int removedAbilitiesCount = this.abilityDAO.removeByEmployee(someEmployeeKey);
      currentTransaction.commit();
      return removedAbilitiesCount;
    } catch (NamingException var12) {
      throw new ServiceException("Impossible de récupérer java:comp/UserTransaction dans le service de nommage", var12);
    } catch (DAOException var13) {
      throw new ServiceException("Impossible de supprimer l'employé de clé " + someEmployeeKey, var13);
    } catch (Exception var14) {
      throw new ServiceException(var14);
    }
  }

  public int removeSkill(SkillKey someSkillKey) {
    UserTransaction currentTransaction = null;

    try {
      InitialContext namingContext = new InitialContext();
      currentTransaction = (UserTransaction)namingContext.lookup("java:comp/UserTransaction");
      currentTransaction.begin();
      SkillService skillService = (SkillService)this.skillServicePool.borrowObject();

      try {
        skillService.remove(someSkillKey);
      } finally {
        this.skillServicePool.returnObject(skillService);
      }

      int removedAbilitiesCount = this.abilityDAO.removeBySkill(someSkillKey);
      currentTransaction.commit();
      return removedAbilitiesCount;
    } catch (NamingException var12) {
      throw new ServiceException("Impossible de récupérer java:comp/UserTransaction dans le service de nommage", var12);
    } catch (DAOException var13) {
      throw new ServiceException("Impossible de supprimer la compétence de clé " + someSkillKey, var13);
    } catch (Exception var14) {
      throw new ServiceException(var14);
    }
  }

  public SortedMap findAbleEmployeesBySkill(Skill someSkill) {
    Assert.notNullArgument(someSkill, "Cannot find abilities for a null skill");
    Assert.notNullArgument(someSkill.getLevels(), "Skill levels cannot be null");

    try {
      Collection abilities = this.abilityDAO.getAbleEmployeesBySkill(someSkill.getKey());
      SortedMap sortedAbilities = new TreeMap();

      Ability ability;
      Object levelKey;
      for(Iterator abilitiesIterator = abilities.iterator(); abilitiesIterator.hasNext(); ((Collection)levelKey).add(ability)) {
        ability = (Ability)abilitiesIterator.next();
        String levelKey = String.valueOf(ability.getLevel());
        levelKey = (Collection)sortedAbilities.get(levelKey);
        if (levelKey == null) {
          levelKey = new ArrayList();
          sortedAbilities.put(levelKey, levelKey);
        }
      }

      Map levels = someSkill.getLevels();
      Iterator levelKeysIterator = levels.keySet().iterator();

      while(levelKeysIterator.hasNext()) {
        levelKey = levelKeysIterator.next();
        Collection levelAbilities = (Collection)sortedAbilities.get(levelKey);
        if (levelAbilities == null) {
          Collection levelAbilities = new ArrayList();
          sortedAbilities.put(levelKey, levelAbilities);
        }
      }

      return sortedAbilities;
    } catch (DAOException var9) {
      throw new ServiceException(var9);
    }
  }

  public SortedMap findAbleEntitiesBySkill(Skill someSkill) {
    Assert.notNullArgument(someSkill, "Cannot find abilities for a null skill");
    Assert.notNullArgument(someSkill.getLevels(), "Skill levels cannot be null");

    try {
      Collection abilities = this.abilityDAO.getAbleEntitiesBySkill(someSkill.getKey());
      SortedMap sortedAbilities = new TreeMap();

      Ability ability;
      Object levelKey;
      for(Iterator abilitiesIterator = abilities.iterator(); abilitiesIterator.hasNext(); ((Collection)levelKey).add(ability)) {
        ability = (Ability)abilitiesIterator.next();
        String levelKey = String.valueOf(ability.getLevel());
        levelKey = (Collection)sortedAbilities.get(levelKey);
        if (levelKey == null) {
          levelKey = new ArrayList();
          sortedAbilities.put(levelKey, levelKey);
        }
      }

      Map levels = someSkill.getLevels();
      Iterator levelKeysIterator = levels.keySet().iterator();

      while(levelKeysIterator.hasNext()) {
        levelKey = levelKeysIterator.next();
        Collection levelAbilities = (Collection)sortedAbilities.get(levelKey);
        if (levelAbilities == null) {
          Collection levelAbilities = new ArrayList();
          sortedAbilities.put(levelKey, levelAbilities);
        }
      }

      return sortedAbilities;
    } catch (DAOException var9) {
      throw new ServiceException(var9);
    }
  }
}
