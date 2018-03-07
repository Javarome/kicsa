package org.orange.kicsa.service.ability;

import java.util.Collection;
import java.util.SortedMap;
import org.orange.kicsa.business.ability.Ability;
import org.orange.kicsa.business.ability.EmployeeAbility;
import org.orange.kicsa.business.employee.Employee;
import org.orange.kicsa.business.employee.EmployeeKey;
import org.orange.kicsa.business.skill.Skill;
import org.orange.kicsa.business.skill.SkillKey;

public interface AbilityService {
  Collection findByEmployee(Employee var1);

  Employee createEmployee(Employee var1, Collection var2, Collection var3);

  int removeEmployee(EmployeeKey var1);

  int removeSkill(SkillKey var1);

  Employee updateEmployee(Employee var1, Collection var2, Collection var3);

  Ability create(EmployeeAbility var1);

  SortedMap findAbleEmployeesBySkill(Skill var1);

  SortedMap findAbleEntitiesBySkill(Skill var1);
}
