package org.orange.kicsa.integration.ability;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import org.orange.kicsa.business.Editable.Status;
import org.orange.kicsa.business.ability.Ability;
import org.orange.kicsa.business.ability.EmployeeAbility;
import org.orange.kicsa.business.ability.EmployeeAbilityImpl;
import org.orange.kicsa.business.ability.EntityAbilityImpl;
import org.orange.kicsa.business.employee.EmployeeKey;
import org.orange.kicsa.business.skill.SkillKey;
import org.orange.util.Assert;
import org.orange.util.integration.DAOException;
import org.orange.util.integration.JDBCDAO;

public class AbilityJDBCDAO extends JDBCDAO implements AbilityDAO {
  public static final String ABILITIES_TABLE = "ABILITY";
  public static final String ABILITIES_LEVEL = "SKILL_LEVEL";
  public static final String ABILITIES_EMPLOYEE_KEY = "EMPLOYEE_ID";
  public static final String ABILITIES_SKILL_KEY = "SKILL_ID";
  public static final String ABILITIES_FIELDS = "ABILITY.SKILL_ID,ABILITY.EMPLOYEE_ID,ABILITY.SKILL_LEVEL";
  public static final String SELECT_BEST_EMPLOYEES_FOR_SKILLS_1STPART = "SELECT ID FROM EMPLOYEE,SKILL WHERE ";
  public static final String REMOVE_ABILITIES_BY_EMPLOYEE = "DELETE FROM ABILITY WHERE EMPLOYEE_ID=?";
  public static final String ABILITIES_SELECT_BY_EMPLOYEE = "SELECT ABILITY.SKILL_ID,ABILITY.EMPLOYEE_ID,ABILITY.SKILL_LEVEL,SKILL.SHORT_NAME,EMPLOYEE.LAST_NAME,EMPLOYEE.FIRST_NAME FROM ABILITY,SKILL,EMPLOYEE WHERE ABILITY.EMPLOYEE_ID=? AND ABILITY.EMPLOYEE_ID=EMPLOYEE.ID AND ABILITY.SKILL_ID=SKILL.ID";
  public static final String REMOVE_ABILITIES_BY_SKILL = "DELETE FROM ABILITY WHERE SKILL_ID=?";
  public static final String ABILITY_INSERT = "INSERT INTO ABILITY (ABILITY.SKILL_ID,ABILITY.EMPLOYEE_ID,ABILITY.SKILL_LEVEL) VALUES (?, ?, ?)";
  public static final String ABILITY_REMOVE = "DELETE FROM ABILITY WHERE SKILL_ID=? AND EMPLOYEE_ID=?";
  static final String ABILITIES_SELECT_EMPLOYEE_BY_SKILL = "SELECT ABILITY.SKILL_ID,ABILITY.EMPLOYEE_ID,ABILITY.SKILL_LEVEL,SKILL.SHORT_NAME,EMPLOYEE.LAST_NAME,EMPLOYEE.FIRST_NAME FROM ABILITY,SKILL,EMPLOYEE WHERE ABILITY.SKILL_ID=? AND ABILITY.SKILL_ID=SKILL.ID AND ABILITY.EMPLOYEE_ID=EMPLOYEE.ID";
  static final String ABILITIES_SELECT_ENTITIES_BY_SKILL = "SELECT  DISTINCT ABILITY.SKILL_ID,ABILITY.SKILL_LEVEL,SKILL.SHORT_NAME,EMPLOYEE.ENTITY_NAME,EMPLOYEE.ENTITY_URI FROM ABILITY,SKILL,EMPLOYEE WHERE ABILITY.SKILL_ID=? AND ABILITY.SKILL_ID=SKILL.ID AND ABILITY.EMPLOYEE_ID=EMPLOYEE.ID";
  public static final String ABILITY_UPDATE = "UPDATE ABILITY SET SKILL_LEVEL=? WHERE EMPLOYEE_ID=? AND SKILL_ID=?";
  private static AbilityJDBCDAO instance;
  AbilityMemoryDAO cache;

  protected AbilityJDBCDAO() throws DAOException {
  }

  public static AbilityJDBCDAO getInstance() throws DAOException {
    if (instance == null) {
      Class var0 = class$org$orange$kicsa$integration$ability$AbilityJDBCDAO == null ? (class$org$orange$kicsa$integration$ability$AbilityJDBCDAO = class$("org.orange.kicsa.integration.ability.AbilityJDBCDAO")) : class$org$orange$kicsa$integration$ability$AbilityJDBCDAO;
      synchronized(var0) {
        if (instance == null) {
          instance = new AbilityJDBCDAO();
        }
      }
    }

    return instance;
  }

  public Collection getByEmployee(EmployeeKey someEmployeeKey) throws DAOException {
    try {
      Connection connection = this.getConnection();

      ArrayList var13;
      try {
        PreparedStatement selectByRefStatement = connection.prepareStatement("SELECT ABILITY.SKILL_ID,ABILITY.EMPLOYEE_ID,ABILITY.SKILL_LEVEL,SKILL.SHORT_NAME,EMPLOYEE.LAST_NAME,EMPLOYEE.FIRST_NAME FROM ABILITY,SKILL,EMPLOYEE WHERE ABILITY.EMPLOYEE_ID=? AND ABILITY.EMPLOYEE_ID=EMPLOYEE.ID AND ABILITY.SKILL_ID=SKILL.ID");
        selectByRefStatement.setInt(1, someEmployeeKey.getId());
        ResultSet abilitiesResultSet = selectByRefStatement.executeQuery();
        ArrayList abilities = new ArrayList();

        while(abilitiesResultSet.next()) {
          Ability ability = this.rowToAbility(someEmployeeKey, abilitiesResultSet);
          abilities.add(ability);
        }

        if (super.log.isDebugEnabled()) {
          super.log.debug("La requête SELECT ABILITY.SKILL_ID,ABILITY.EMPLOYEE_ID,ABILITY.SKILL_LEVEL,SKILL.SHORT_NAME,EMPLOYEE.LAST_NAME,EMPLOYEE.FIRST_NAME FROM ABILITY,SKILL,EMPLOYEE WHERE ABILITY.EMPLOYEE_ID=? AND ABILITY.EMPLOYEE_ID=EMPLOYEE.ID AND ABILITY.SKILL_ID=SKILL.ID a récupéré " + abilities.size() + " abilities");
        }

        var13 = abilities;
      } finally {
        connection.close();
      }

      return var13;
    } catch (SQLException var12) {
      throw new DAOException("Impossible de lire les abilities de l'employé de clé " + someEmployeeKey, var12);
    }
  }

  public int removeByEmployee(EmployeeKey someEmployeeKey) throws DAOException {
    try {
      Connection connection = this.getConnection();

      int var6;
      try {
        int id = someEmployeeKey.getId();
        PreparedStatement removeStatement = connection.prepareStatement("DELETE FROM ABILITY WHERE EMPLOYEE_ID=?");
        removeStatement.setInt(1, id);
        int affectedRows = removeStatement.executeUpdate();
        var6 = affectedRows;
      } finally {
        connection.close();
      }

      return var6;
    } catch (SQLException var12) {
      throw new DAOException("Impossible de supprimer les capacités de l'employé de clé " + someEmployeeKey, var12);
    }
  }

  public int removeBySkill(SkillKey someSkillKey) throws DAOException {
    try {
      Connection connection = this.getConnection();

      int var6;
      try {
        int id = someSkillKey.getId();
        PreparedStatement removeStatement = connection.prepareStatement("DELETE FROM ABILITY WHERE EMPLOYEE_ID=?");
        removeStatement.setInt(1, id);
        int affectedRows = removeStatement.executeUpdate();
        var6 = affectedRows;
      } finally {
        connection.close();
      }

      return var6;
    } catch (SQLException var12) {
      throw new DAOException("Impossible de supprimer les capacités relatives à la compétence de clé " + someSkillKey, var12);
    }
  }

  protected EmployeeAbilityImpl rowToAbility(EmployeeKey someEmployeeKey, ResultSet someAbilityResultSet) throws SQLException {
    Assert.notNullArgument(someEmployeeKey, "Cannot instantiate an EmployeeAbility with a null employee key");
    Assert.notNullArgument(someAbilityResultSet, "Cannot instantiate an EmployeeAbility from a null ResultSet");
    EmployeeAbilityImpl ability = new EmployeeAbilityImpl(someEmployeeKey, someAbilityResultSet.getString("FIRST_NAME"), someAbilityResultSet.getString("LAST_NAME"), new SkillKey(someAbilityResultSet.getInt("SKILL_ID")), someAbilityResultSet.getString("SHORT_NAME"), someAbilityResultSet.getInt("SKILL_LEVEL"), Status.SYNC);
    return ability;
  }

  protected EmployeeAbilityImpl rowToEmployeeAbility(SkillKey someSkillKey, ResultSet someAbilityResultSet) throws SQLException {
    Assert.notNullArgument(someSkillKey, "Cannot instantiate an EmployeeAbility with a null skill key");
    Assert.notNullArgument(someAbilityResultSet, "Cannot instantiate an EmployeeAbility from a null ResultSet");
    EmployeeAbilityImpl ability = new EmployeeAbilityImpl(new EmployeeKey(someAbilityResultSet.getInt("EMPLOYEE_ID")), someAbilityResultSet.getString("FIRST_NAME"), someAbilityResultSet.getString("LAST_NAME"), someSkillKey, someAbilityResultSet.getString("SHORT_NAME"), someAbilityResultSet.getInt("SKILL_LEVEL"), Status.SYNC);
    return ability;
  }

  protected EntityAbilityImpl rowToEntityAbility(SkillKey someSkillKey, ResultSet someAbilityResultSet) throws SQLException {
    Assert.notNullArgument(someSkillKey, "Cannot instantiate an EntityAbility with a null skill key");
    Assert.notNullArgument(someAbilityResultSet, "Cannot instantiate an EmployeeAbility from a null ResultSet");
    EntityAbilityImpl ability = new EntityAbilityImpl(someAbilityResultSet.getString("ENTITY_NAME"), someAbilityResultSet.getString("ENTITY_URI"), someSkillKey, someAbilityResultSet.getString("SHORT_NAME"), someAbilityResultSet.getInt("SKILL_LEVEL"), Status.SYNC);
    return ability;
  }

  public EmployeeAbility create(EmployeeAbility someAbility) throws DAOException {
    Assert.notNullArgument(someAbility, "Cannot create a null ability");

    try {
      Connection connection = this.getConnection();

      EmployeeAbility var5;
      try {
        PreparedStatement insertStatement = connection.prepareStatement("INSERT INTO ABILITY (ABILITY.SKILL_ID,ABILITY.EMPLOYEE_ID,ABILITY.SKILL_LEVEL) VALUES (?, ?, ?)");
        insertStatement.setInt(1, someAbility.getSkillKey().getId());
        insertStatement.setInt(2, someAbility.getEmployeeKey().getId());
        insertStatement.setInt(3, someAbility.getLevel());
        int affectedRows = insertStatement.executeUpdate();
        if (affectedRows != 1) {
          throw new DAOException("Creation failed for " + someAbility + " (duplicate ?)");
        }

        if (this.isCacheEnabled()) {
          this.cache.create(someAbility);
        }

        if (super.log.isDebugEnabled()) {
          super.log.debug("Ability " + someAbility + " created in database");
        }

        var5 = someAbility;
      } finally {
        connection.close();
      }

      return var5;
    } catch (SQLException var11) {
      throw new DAOException("Could not create " + someAbility, var11);
    }
  }

  public void remove(EmployeeAbility someAbility) throws DAOException {
    Assert.notNullArgument(someAbility, "Cannot remove a null ability");

    try {
      Connection connection = this.getConnection();

      try {
        PreparedStatement insertStatement = connection.prepareStatement("DELETE FROM ABILITY WHERE SKILL_ID=? AND EMPLOYEE_ID=?");
        insertStatement.setInt(1, someAbility.getSkillKey().getId());
        insertStatement.setInt(2, someAbility.getEmployeeKey().getId());
        int affectedRows = insertStatement.executeUpdate();
        if (affectedRows != 1) {
          throw new DAOException("Removal failed for " + someAbility + " (not found in database ?)");
        }

        if (this.isCacheEnabled()) {
          this.cache.remove(someAbility);
        }

        if (super.log.isDebugEnabled()) {
          super.log.debug("Ability " + someAbility + " removed from database");
        }
      } finally {
        connection.close();
      }

    } catch (SQLException var10) {
      throw new DAOException("Could not remove " + someAbility, var10);
    }
  }

  public boolean isCacheEnabled() {
    return this.cache != null;
  }

  public Collection getAbleEmployeesBySkill(SkillKey someSkillKey) throws DAOException {
    try {
      Connection connection = this.getConnection();

      ArrayList var13;
      try {
        PreparedStatement selectByRefStatement = connection.prepareStatement("SELECT ABILITY.SKILL_ID,ABILITY.EMPLOYEE_ID,ABILITY.SKILL_LEVEL,SKILL.SHORT_NAME,EMPLOYEE.LAST_NAME,EMPLOYEE.FIRST_NAME FROM ABILITY,SKILL,EMPLOYEE WHERE ABILITY.SKILL_ID=? AND ABILITY.SKILL_ID=SKILL.ID AND ABILITY.EMPLOYEE_ID=EMPLOYEE.ID");
        selectByRefStatement.setInt(1, someSkillKey.getId());
        ResultSet abilitiesResultSet = selectByRefStatement.executeQuery();
        ArrayList abilities = new ArrayList();

        while(abilitiesResultSet.next()) {
          Ability ability = this.rowToEmployeeAbility(someSkillKey, abilitiesResultSet);
          abilities.add(ability);
        }

        if (super.log.isDebugEnabled()) {
          super.log.debug("Read " + abilities.size() + " employees' abilities");
        }

        var13 = abilities;
      } finally {
        connection.close();
      }

      return var13;
    } catch (SQLException var12) {
      throw new DAOException("Could not read abilities for skill key " + someSkillKey, var12);
    }
  }

  public Collection getAbleEntitiesBySkill(SkillKey someSkillKey) throws DAOException {
    try {
      Connection connection = this.getConnection();

      ArrayList var13;
      try {
        PreparedStatement selectByRefStatement = connection.prepareStatement("SELECT  DISTINCT ABILITY.SKILL_ID,ABILITY.SKILL_LEVEL,SKILL.SHORT_NAME,EMPLOYEE.ENTITY_NAME,EMPLOYEE.ENTITY_URI FROM ABILITY,SKILL,EMPLOYEE WHERE ABILITY.SKILL_ID=? AND ABILITY.SKILL_ID=SKILL.ID AND ABILITY.EMPLOYEE_ID=EMPLOYEE.ID");
        selectByRefStatement.setInt(1, someSkillKey.getId());
        ResultSet abilitiesResultSet = selectByRefStatement.executeQuery();
        ArrayList abilities = new ArrayList();

        while(abilitiesResultSet.next()) {
          Ability ability = this.rowToEntityAbility(someSkillKey, abilitiesResultSet);
          abilities.add(ability);
        }

        if (super.log.isDebugEnabled()) {
          super.log.debug("Read " + abilities.size() + " entities' abilities");
        }

        var13 = abilities;
      } finally {
        connection.close();
      }

      return var13;
    } catch (SQLException var12) {
      throw new DAOException("Could not read abilities for skill key " + someSkillKey, var12);
    }
  }

  public EmployeeAbility update(EmployeeAbility someAbility) throws DAOException {
    try {
      Connection connection = this.getConnection();

      EmployeeAbility var5;
      try {
        PreparedStatement updateStatement = connection.prepareStatement("UPDATE ABILITY SET SKILL_LEVEL=? WHERE EMPLOYEE_ID=? AND SKILL_ID=?");
        updateStatement.setInt(1, someAbility.getLevel());
        updateStatement.setInt(2, someAbility.getEmployeeKey().getId());
        updateStatement.setInt(3, someAbility.getSkillKey().getId());
        int affectedRows = updateStatement.executeUpdate();
        if (affectedRows != 1) {
          throw new DAOException("Update failed for ability " + someAbility);
        }

        var5 = someAbility;
      } finally {
        connection.close();
      }

      return var5;
    } catch (SQLException var11) {
      throw new DAOException("Could not update ability " + someAbility, var11);
    }
  }
}
