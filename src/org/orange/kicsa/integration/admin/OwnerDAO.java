package org.orange.kicsa.integration.admin;

import java.util.Collection;
import org.orange.kicsa.EmployeeNotFoundException;
import org.orange.kicsa.SkillNotFoundException;
import org.orange.kicsa.business.employee.EmployeeKey;
import org.orange.kicsa.business.skill.SkillKey;
import org.orange.util.integration.DAOException;

public interface OwnerDAO {
  Collection getOwnersBySkill(SkillKey var1) throws SkillNotFoundException, DAOException;

  Collection getSkillsByOwner(EmployeeKey var1) throws EmployeeNotFoundException, DAOException;

  void create(SkillKey var1, EmployeeKey var2) throws DAOException;

  int removeByEmployee(EmployeeKey var1) throws EmployeeNotFoundException, DAOException;

  int removeBySkill(SkillKey var1) throws SkillNotFoundException, DAOException;

  void remove(SkillKey var1, EmployeeKey var2) throws DAOException;
}
