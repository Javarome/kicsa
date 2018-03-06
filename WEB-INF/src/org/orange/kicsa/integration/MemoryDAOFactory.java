package org.orange.kicsa.integration;

import org.orange.kicsa.integration.ability.AbilityDAO;
import org.orange.kicsa.integration.ability.AbilityMemoryDAO;
import org.orange.kicsa.integration.employee.EmployeeDAO;
import org.orange.kicsa.integration.employee.EmployeeMemoryDAO;
import org.orange.kicsa.integration.skill.RelationshipTypeDAO;
import org.orange.kicsa.integration.skill.RelationshipTypeMemoryDAO;
import org.orange.kicsa.integration.skill.SkillDAO;
import org.orange.kicsa.integration.skill.SkillMemoryDAO;
import org.orange.kicsa.service.ServiceException;
import org.orange.util.integration.DAOException;

public class MemoryDAOFactory extends AbstractDAOFactory {
  private static MemoryDAOFactory instance = null;

  protected MemoryDAOFactory() {
  }

  public SkillDAO createSkillDAO() throws DAOException {
    try {
      SkillDAO skillDAO = SkillMemoryDAO.getInstance();
      return skillDAO;
    } catch (DAOException var2) {
      throw new ServiceException(var2);
    }
  }

  public RelationshipTypeDAO createRelationshipTypeDAO() throws DAOException {
    try {
      return RelationshipTypeMemoryDAO.getInstance();
    } catch (DAOException var2) {
      throw new ServiceException(var2);
    }
  }

  public AbilityDAO createAdminDAO() throws DAOException {
    throw new RuntimeException("Non implémenté");
  }

  public EmployeeDAO createEmployeeDAO() throws DAOException {
    try {
      return EmployeeMemoryDAO.getInstance();
    } catch (DAOException var2) {
      throw new ServiceException(var2);
    }
  }

  public AbilityDAO createAbilityDAO() throws DAOException {
    return AbilityMemoryDAO.getInstance();
  }

  public static MemoryDAOFactory getInstance() {
    if (instance == null) {
      Class var0 = class$org$orange$kicsa$integration$MemoryDAOFactory == null ? (class$org$orange$kicsa$integration$MemoryDAOFactory = class$("org.orange.kicsa.integration.MemoryDAOFactory")) : class$org$orange$kicsa$integration$MemoryDAOFactory;
      synchronized(var0) {
        if (instance == null) {
          instance = new MemoryDAOFactory();
        }
      }
    }

    return instance;
  }
}
