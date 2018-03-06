package org.orange.kicsa.integration;

import org.orange.kicsa.integration.ability.AbilityDAO;
import org.orange.kicsa.integration.ability.AbilityJDODAO;
import org.orange.kicsa.integration.employee.EmployeeDAO;
import org.orange.kicsa.integration.skill.RelationshipTypeDAO;
import org.orange.kicsa.integration.skill.SkillDAO;
import org.orange.util.integration.DAOException;

public class JDODAOFactory extends AbstractDAOFactory {
  private static JDODAOFactory instance = null;

  protected JDODAOFactory() {
  }

  public SkillDAO createSkillDAO() {
    throw new RuntimeException("Non implémenté");
  }

  public AbilityDAO createAdminDAO() {
    throw new RuntimeException("Non implémenté");
  }

  public EmployeeDAO createEmployeeDAO() {
    throw new RuntimeException("Non implémenté");
  }

  public AbilityDAO createAbilityDAO() {
    return new AbilityJDODAO();
  }

  public static JDODAOFactory getInstance() {
    if (instance == null) {
      Class var0 = class$org$orange$kicsa$integration$JDODAOFactory == null ? (class$org$orange$kicsa$integration$JDODAOFactory = class$("org.orange.kicsa.integration.JDODAOFactory")) : class$org$orange$kicsa$integration$JDODAOFactory;
      synchronized(var0) {
        if (instance == null) {
          instance = new JDODAOFactory();
        }
      }
    }

    return instance;
  }

  public RelationshipTypeDAO createRelationshipTypeDAO() throws DAOException {
    throw new RuntimeException("Non implémenté");
  }
}
