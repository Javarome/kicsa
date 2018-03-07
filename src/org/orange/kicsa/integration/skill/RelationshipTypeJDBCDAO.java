package org.orange.kicsa.integration.skill;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashSet;
import java.util.SortedMap;
import java.util.TreeMap;
import org.orange.kicsa.business.Editable.Status;
import org.orange.kicsa.business.skill.RelationshipType;
import org.orange.kicsa.business.skill.RelationshipTypeImpl;
import org.orange.util.integration.DAOException;
import org.orange.util.integration.JDBCDAO;

public class RelationshipTypeJDBCDAO extends JDBCDAO implements RelationshipTypeDAO {
  public static final String TABLE = "RELATIONSHIP_TYPE";
  public static final String NAME = "NAME";
  public static final String IS_PARENT = "IS_PARENT";
  public static final String INSERT = "INSERT INTO RELATIONSHIP_TYPE (NAME,IS_PARENT) VALUES (?, ?)";
  private static RelationshipTypeJDBCDAO instance;
  public static final String SELECT_ALL = "SELECT NAME,IS_PARENT FROM RELATIONSHIP_TYPE";
  public static final String SELECT_PARENTS;
  public static final String REMOVE = "DELETE FROM RELATIONSHIP_TYPE WHERE NAME=?";
  RelationshipTypeMemoryDAO cache;
  public static final String RELATIONSHIP_TYPE_UPDATE = "UPDATE RELATIONSHIP_TYPE SET IS_PARENT=? WHERE NAME=?";

  public RelationshipType create(RelationshipType someRelationshipType) throws DAOException {
    try {
      Connection connection = this.getConnection();

      RelationshipType var5;
      try {
        PreparedStatement insertStatement = connection.prepareStatement("INSERT INTO RELATIONSHIP_TYPE (NAME,IS_PARENT) VALUES (?, ?)");
        insertStatement.setString(1, someRelationshipType.getName());
        insertStatement.setString(2, someRelationshipType.isParent() ? "True" : "False");
        int affectedRows = insertStatement.executeUpdate();
        if (affectedRows != 1) {
          throw new DAOException("Creation of " + someRelationshipType + " failed");
        }

        var5 = someRelationshipType;
      } finally {
        connection.close();
      }

      return var5;
    } catch (SQLException var11) {
      throw new DAOException("Could not create " + someRelationshipType, var11);
    }
  }

  public static RelationshipTypeJDBCDAO getInstance() throws DAOException {
    if (instance == null) {
      Class var0 = class$org$orange$kicsa$integration$skill$RelationshipTypeJDBCDAO == null ? (class$org$orange$kicsa$integration$skill$RelationshipTypeJDBCDAO = class$("org.orange.kicsa.integration.skill.RelationshipTypeJDBCDAO")) : class$org$orange$kicsa$integration$skill$RelationshipTypeJDBCDAO;
      synchronized(var0) {
        if (instance == null) {
          instance = new RelationshipTypeJDBCDAO();
        }
      }
    }

    return instance;
  }

  protected RelationshipTypeJDBCDAO() throws DAOException {
  }

  protected RelationshipType rowToRelationshipType(ResultSet someResultSet) throws SQLException {
    Boolean isParent = Boolean.valueOf(someResultSet.getString("IS_PARENT"));
    String name = someResultSet.getString("NAME");
    RelationshipType relationshipType = new RelationshipTypeImpl(name, isParent, Status.SYNC);
    return relationshipType;
  }

  public SortedMap getAll() throws DAOException {
    try {
      Connection connection = this.getConnection();

      TreeMap var12;
      try {
        PreparedStatement insertStatement = connection.prepareStatement("SELECT NAME,IS_PARENT FROM RELATIONSHIP_TYPE");
        ResultSet typesResultSet = insertStatement.executeQuery();
        TreeMap types = new TreeMap();

        while(typesResultSet.next()) {
          RelationshipType relationshipType = this.rowToRelationshipType(typesResultSet);
          types.put(relationshipType.getName(), relationshipType);
        }

        if (super.log.isDebugEnabled()) {
          super.log.debug("relationshipTypes=" + types);
        }

        var12 = types;
      } finally {
        connection.close();
      }

      return var12;
    } catch (SQLException var11) {
      throw new DAOException("Impossible de récupérer les types de relation", var11);
    }
  }

  public Collection getParentTypes() throws DAOException {
    Collection parentTypes = null;
    if (this.isCacheEnabled()) {
      parentTypes = this.cache.getParentTypes();
    }

    if (parentTypes == null) {
      parentTypes = this.readParentTypes();
    }

    return parentTypes;
  }

  public Collection readParentTypes() throws DAOException {
    try {
      Connection connection = this.getConnection();

      try {
        PreparedStatement statement = connection.prepareStatement(SELECT_PARENTS);
        ResultSet resultSet = statement.executeQuery();
        HashSet parents = new HashSet(3);

        while(resultSet.next()) {
          String name = resultSet.getString("NAME");
          parents.add(name);
        }

        HashSet var12 = parents;
        return var12;
      } finally {
        connection.close();
      }
    } catch (SQLException var11) {
      throw new DAOException("Impossible de récupérer les types parents", var11);
    }
  }

  public void remove(String someRelationshipType) throws DAOException {
    try {
      Connection connection = this.getConnection();

      try {
        PreparedStatement insertStatement = connection.prepareStatement("DELETE FROM RELATIONSHIP_TYPE WHERE NAME=?");
        insertStatement.setString(1, someRelationshipType);
        int affectedRows = insertStatement.executeUpdate();
        if (affectedRows != 1) {
          throw new DAOException("La requête DELETE FROM RELATIONSHIP_TYPE WHERE NAME=? a affecté " + affectedRows + " enregistrements et non pas 1");
        }
      } finally {
        connection.close();
      }

    } catch (SQLException var10) {
      throw new DAOException("Impossible de supprimer le type de relation \"" + someRelationshipType + "\"", var10);
    }
  }

  public boolean isCacheEnabled() {
    return this.cache != null;
  }

  public void update(RelationshipType someRelationshipType) throws DAOException {
    try {
      Connection connection = this.getConnection();

      try {
        PreparedStatement updateStatement = connection.prepareStatement("UPDATE RELATIONSHIP_TYPE SET IS_PARENT=? WHERE NAME=?");
        updateStatement.setString(1, someRelationshipType.isParent() ? "True" : "False");
        updateStatement.setString(2, someRelationshipType.getName());
        int affectedRows = updateStatement.executeUpdate();
        if (affectedRows != 1) {
          throw new DAOException("Update of " + someRelationshipType + " failed");
        }
      } finally {
        connection.close();
      }

    } catch (SQLException var10) {
      throw new DAOException("Could not update " + someRelationshipType, var10);
    }
  }

  static {
    SELECT_PARENTS = "SELECT NAME FROM RELATIONSHIP_TYPE WHERE IS_PARENT='" + Boolean.TRUE + "'";
  }
}
