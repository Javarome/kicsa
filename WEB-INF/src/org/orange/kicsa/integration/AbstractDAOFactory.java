package org.orange.kicsa.integration;

import org.orange.kicsa.integration.ability.AbilityDAO;
import org.orange.kicsa.integration.employee.EmployeeDAO;
import org.orange.kicsa.integration.skill.RelationshipTypeDAO;
import org.orange.kicsa.integration.skill.SkillDAO;
import org.orange.util.integration.DAOException;

public abstract class AbstractDAOFactory {
  public AbstractDAOFactory() {
  }

  public abstract SkillDAO createSkillDAO() throws DAOException;

  public abstract RelationshipTypeDAO createRelationshipTypeDAO() throws DAOException;

  public abstract AbilityDAO createAdminDAO() throws DAOException;

  public abstract EmployeeDAO createEmployeeDAO() throws DAOException;

  public abstract AbilityDAO createAbilityDAO() throws DAOException;
}
