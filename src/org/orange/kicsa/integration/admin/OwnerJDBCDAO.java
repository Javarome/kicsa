package org.orange.kicsa.integration.admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import org.orange.kicsa.EmployeeNotFoundException;
import org.orange.kicsa.SkillNotFoundException;
import org.orange.kicsa.business.employee.EmployeeKey;
import org.orange.kicsa.business.skill.SkillKey;
import org.orange.util.Assert;
import org.orange.util.integration.DAOException;
import org.orange.util.integration.JDBCDAO;

public class OwnerJDBCDAO extends JDBCDAO implements OwnerDAO {
  public static final String TABLE = "OWNER";
  public static final String SKILL_KEY = "SKILL_ID";
  public static final String EMPLOYEE_KEY = "EMPLOYEE_ID";
  public static final String INSERT = "INSERT INTO OWNER (SKILL_ID,EMPLOYEE_ID) VALUES (?, ?)";
  public static final String REMOVE = "DELETE FROM OWNER WHERE SKILL_ID=? AND EMPLOYEE_ID=?";
  public static final String SELECT_SKILLS_BY_OWNER = "SELECT SKILL_ID FROM OWNER WHERE EMPLOYEE_ID=?";
  private static OwnerJDBCDAO instance;
  public static final String SELECT_OWNERS_BY_SKILL = "SELECT EMPLOYEE_ID FROM OWNER WHERE SKILL_ID=?";
  public static final String REMOVE_BY_EMPLOYEE = "DELETE FROM OWNER WHERE EMPLOYEE_ID=?";
  public static final String REMOVE_BY_SKILL = "DELETE FROM OWNER WHERE SKILL_ID=?";

  protected OwnerJDBCDAO() throws DAOException {
    this.setCacheEnabled(false);
  }

  public void create(SkillKey someSkill, EmployeeKey someEmployee) throws DAOException {
    try {
      Connection connection = this.getConnection();

      try {
        PreparedStatement insertStatement = connection.prepareStatement("INSERT INTO OWNER (SKILL_ID,EMPLOYEE_ID) VALUES (?, ?)");
        insertStatement.setInt(1, someSkill.getId());
        insertStatement.setInt(2, someEmployee.getId());
        if (super.log.isDebugEnabled()) {
          super.log.debug("Exécution de INSERT INTO OWNER (SKILL_ID,EMPLOYEE_ID) VALUES (?, ?)");
        }

        int rowsAffected = insertStatement.executeUpdate();
        if (rowsAffected != 1) {
          throw new DAOException("La création INSERT INTO OWNER (SKILL_ID,EMPLOYEE_ID) VALUES (?, ?) a affecté " + rowsAffected + " enregistrements au lieu de 1");
        }

        if (super.log.isDebugEnabled()) {
          super.log.debug("Responsable " + someEmployee + " créé pour la compétence " + someSkill);
        }
      } finally {
        connection.close();
      }

    } catch (SQLException var11) {
      throw new DAOException("Impossible de créer le responsable", var11);
    }
  }

  public void remove(SkillKey someSkill, EmployeeKey someEmployee) throws DAOException {
    try {
      Connection connection = this.getConnection();

      try {
        PreparedStatement insertStatement = connection.prepareStatement("DELETE FROM OWNER WHERE SKILL_ID=? AND EMPLOYEE_ID=?");
        insertStatement.setInt(1, someSkill.getId());
        insertStatement.setInt(2, someEmployee.getId());
        if (super.log.isDebugEnabled()) {
          super.log.debug("Exécution de DELETE FROM OWNER WHERE SKILL_ID=? AND EMPLOYEE_ID=?");
        }

        int rowsAffected = insertStatement.executeUpdate();
        if (rowsAffected != 1) {
          throw new DAOException("La suppression de la responsabilité de " + someEmployee + " sur " + someSkill + " a échoué");
        }

        if (super.log.isDebugEnabled()) {
          super.log.debug("Owner " + someEmployee + " supprimé pour la compétence " + someSkill);
        }
      } finally {
        connection.close();
      }

    } catch (Throwable var11) {
      throw new DAOException("Impossible de supprimer le owner", var11);
    }
  }

  public Collection getSkillsByOwner(EmployeeKey someEmployee) throws DAOException, EmployeeNotFoundException {
    Assert.notNullArgument(someEmployee, "La clé d'employé recherchée ne peut être nulle");

    try {
      Connection connection = this.getConnection();

      try {
        PreparedStatement selectByRefStatement = connection.prepareStatement("SELECT SKILL_ID FROM OWNER WHERE EMPLOYEE_ID=?");
        selectByRefStatement.setInt(1, someEmployee.getId());
        if (super.log.isDebugEnabled()) {
          super.log.debug("Exécution de la requête JDBC " + selectByRefStatement);
        }

        ResultSet skillSet = selectByRefStatement.executeQuery();
        if (!skillSet.next()) {
          throw new EmployeeNotFoundException(someEmployee);
        } else {
          ArrayList skills = new ArrayList();

          do {
            skills.add(new SkillKey(skillSet.getInt("SKILL_ID")));
          } while(skillSet.next());

          ArrayList var6 = skills;
          return var6;
        }
      } finally {
        connection.close();
      }
    } catch (SQLException var12) {
      throw new DAOException("Could not get skills for owner=" + someEmployee, var12);
    }
  }

  public static OwnerDAO getInstance() throws DAOException {
    if (instance == null) {
      Class var0 = class$org$orange$kicsa$integration$admin$OwnerJDBCDAO == null ? (class$org$orange$kicsa$integration$admin$OwnerJDBCDAO = class$("org.orange.kicsa.integration.admin.OwnerJDBCDAO")) : class$org$orange$kicsa$integration$admin$OwnerJDBCDAO;
      synchronized(var0) {
        instance = new OwnerJDBCDAO();
      }
    }

    return instance;
  }

  public boolean isCacheEnabled() {
    return false;
  }

  public void setCacheEnabled(boolean someEnabledState) throws DAOException {
  }

  public Collection getOwnersBySkill(SkillKey someSkill) throws DAOException, SkillNotFoundException {
    Assert.notNullArgument(someSkill, "La clé de la compétence recherchée ne peut être nulle");

    try {
      Connection connection = this.getConnection();

      try {
        PreparedStatement selectByRefStatement = connection.prepareStatement("SELECT EMPLOYEE_ID FROM OWNER WHERE SKILL_ID=?");
        selectByRefStatement.setInt(1, someSkill.getId());
        if (super.log.isDebugEnabled()) {
          super.log.debug("Exécution de la requête JDBC " + selectByRefStatement);
        }

        ResultSet employeeSet = selectByRefStatement.executeQuery();
        if (!employeeSet.next()) {
          throw new SkillNotFoundException(someSkill);
        } else {
          ArrayList employees = new ArrayList();

          do {
            employees.add(new EmployeeKey(employeeSet.getInt("EMPLOYEE_ID")));
          } while(employeeSet.next());

          ArrayList var6 = employees;
          return var6;
        }
      } finally {
        connection.close();
      }
    } catch (SQLException var12) {
      throw new DAOException("Could not get owners for skill " + someSkill, var12);
    }
  }

  public int removeByEmployee(EmployeeKey someEmployee) throws DAOException, EmployeeNotFoundException {
    try {
      Connection connection = this.getConnection();

      int var5;
      try {
        PreparedStatement insertStatement = connection.prepareStatement("DELETE FROM OWNER WHERE EMPLOYEE_ID=?");
        insertStatement.setInt(1, someEmployee.getId());
        if (super.log.isDebugEnabled()) {
          super.log.debug("Exécution de DELETE FROM OWNER WHERE EMPLOYEE_ID=?");
        }

        int rowsAffected = insertStatement.executeUpdate();
        if (super.log.isDebugEnabled()) {
          super.log.debug("Responsabilités supprimées pour l'employé " + someEmployee);
        }

        var5 = rowsAffected;
      } finally {
        connection.close();
      }

      return var5;
    } catch (Throwable var11) {
      throw new DAOException("Impossible les responsabiltiés de l'employé", var11);
    }
  }

  public int removeBySkill(SkillKey someSkill) throws DAOException, SkillNotFoundException {
    try {
      Connection connection = this.getConnection();

      int var5;
      try {
        PreparedStatement insertStatement = connection.prepareStatement("DELETE FROM OWNER WHERE SKILL_ID=?");
        insertStatement.setInt(1, someSkill.getId());
        if (super.log.isDebugEnabled()) {
          super.log.debug("Exécution de DELETE FROM OWNER WHERE SKILL_ID=?");
        }

        int rowsAffected = insertStatement.executeUpdate();
        if (super.log.isDebugEnabled()) {
          super.log.debug("Responsabilités supprimées pour l'employé " + someSkill);
        }

        var5 = rowsAffected;
      } finally {
        connection.close();
      }

      return var5;
    } catch (Throwable var11) {
      throw new DAOException("Impossible de supprimer les responsabiltiés pour la compétence", var11);
    }
  }
}
