package org.orange.kicsa.integration.ability;

import java.util.Collection;
import org.orange.kicsa.business.ability.EmployeeAbility;
import org.orange.kicsa.business.employee.EmployeeKey;
import org.orange.kicsa.business.skill.SkillKey;
import org.orange.util.integration.DAOException;

public interface AbilityDAO {
  Collection getByEmployee(EmployeeKey var1) throws DAOException;

  EmployeeAbility create(EmployeeAbility var1) throws DAOException;

  EmployeeAbility update(EmployeeAbility var1) throws DAOException;

  int removeByEmployee(EmployeeKey var1) throws DAOException;

  int removeBySkill(SkillKey var1) throws DAOException;

  Collection getAbleEmployeesBySkill(SkillKey var1) throws DAOException;

  Collection getAbleEntitiesBySkill(SkillKey var1) throws DAOException;

  void remove(EmployeeAbility var1) throws DAOException;
}
