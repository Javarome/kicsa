package org.orange.kicsa.integration.ability;

import java.util.Collection;
import org.orange.kicsa.business.ability.EmployeeAbility;
import org.orange.kicsa.business.employee.EmployeeKey;
import org.orange.kicsa.business.skill.SkillKey;
import org.orange.util.NotImplementedException;
import org.orange.util.integration.DAOException;

public class AbilityMemoryDAO implements AbilityDAO {
  private static AbilityMemoryDAO instance;

  protected AbilityMemoryDAO() throws DAOException {
  }

  public static AbilityMemoryDAO getInstance() throws DAOException {
    if (instance == null) {
      Class var0 = class$org$orange$kicsa$integration$ability$AbilityMemoryDAO == null ? (class$org$orange$kicsa$integration$ability$AbilityMemoryDAO = class$("org.orange.kicsa.integration.ability.AbilityMemoryDAO")) : class$org$orange$kicsa$integration$ability$AbilityMemoryDAO;
      synchronized(var0) {
        if (instance == null) {
          instance = new AbilityMemoryDAO();
        }
      }
    }

    return instance;
  }

  public Collection getByEmployee(EmployeeKey someKey) throws DAOException {
    throw new NotImplementedException();
  }

  public EmployeeAbility create(EmployeeAbility someAbility) throws DAOException {
    throw new NotImplementedException();
  }

  public int removeByEmployee(EmployeeKey someEmployeeKey) throws DAOException {
    throw new NotImplementedException();
  }

  public int removeBySkill(SkillKey someSkillKey) throws DAOException {
    throw new NotImplementedException();
  }

  public void remove(EmployeeAbility someAbility) throws DAOException {
    throw new NotImplementedException();
  }

  public EmployeeAbility update(EmployeeAbility someAbility) throws DAOException {
    throw new NotImplementedException();
  }

  public Collection getAbleEmployeesBySkill(SkillKey someSkillKey) throws DAOException {
    throw new NotImplementedException();
  }

  public Collection getAbleEntitiesBySkill(SkillKey someSkillKey) throws DAOException {
    throw new NotImplementedException();
  }
}
