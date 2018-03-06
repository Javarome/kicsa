package org.orange.kicsa.integration;

import org.orange.kicsa.integration.ability.AbilityDAO;
import org.orange.kicsa.integration.ability.AbilityJDBCDAO;
import org.orange.kicsa.integration.employee.EmployeeDAO;
import org.orange.kicsa.integration.employee.EmployeeJDBCDAO;
import org.orange.kicsa.integration.skill.RelationshipTypeDAO;
import org.orange.kicsa.integration.skill.RelationshipTypeJDBCDAO;
import org.orange.kicsa.integration.skill.SkillDAO;
import org.orange.kicsa.integration.skill.SkillJDBCDAO;
import org.orange.kicsa.service.ServiceException;
import org.orange.util.integration.DAOException;

public class JDBCDAOFactory extends AbstractDAOFactory {
  private static JDBCDAOFactory instance = null;

  protected JDBCDAOFactory() {
  }

  public SkillDAO createSkillDAO() throws DAOException {
    try {
      SkillDAO skillDAO = SkillJDBCDAO.getInstance();
      return skillDAO;
    } catch (DAOException var2) {
      var2.printStackTrace();
      throw new ServiceException(var2);
    }
  }

  public RelationshipTypeDAO createRelationshipTypeDAO() throws DAOException {
    return RelationshipTypeJDBCDAO.getInstance();
  }

  public AbilityDAO createAdminDAO() throws DAOException {
    throw new RuntimeException("Non implémenté");
  }

  public EmployeeDAO createEmployeeDAO() throws DAOException {
    return EmployeeJDBCDAO.getInstance();
  }

  public AbilityDAO createAbilityDAO() throws DAOException {
    return AbilityJDBCDAO.getInstance();
  }

  public static JDBCDAOFactory getInstance() {
    if (instance == null) {
      Class var0 = class$org$orange$kicsa$integration$JDBCDAOFactory == null ? (class$org$orange$kicsa$integration$JDBCDAOFactory = class$("org.orange.kicsa.integration.JDBCDAOFactory")) : class$org$orange$kicsa$integration$JDBCDAOFactory;
      synchronized(var0) {
        if (instance == null) {
          instance = new JDBCDAOFactory();
        }
      }
    }

    return instance;
  }
}
