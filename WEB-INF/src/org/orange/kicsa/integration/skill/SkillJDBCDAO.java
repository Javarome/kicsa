package org.orange.kicsa.integration.skill;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.SortedMap;
import java.util.StringTokenizer;
import java.util.TreeMap;
import org.orange.kicsa.Application;
import org.orange.kicsa.SkillNotFoundException;
import org.orange.kicsa.business.Editable.Status;
import org.orange.kicsa.business.skill.Relationship;
import org.orange.kicsa.business.skill.RelationshipImpl;
import org.orange.kicsa.business.skill.Skill;
import org.orange.kicsa.business.skill.SkillImpl;
import org.orange.kicsa.business.skill.SkillKey;
import org.orange.kicsa.business.skill.SkillLevel;
import org.orange.kicsa.business.skill.SkillLevelImpl;
import org.orange.util.Assert;
import org.orange.util.integration.DAOException;
import org.orange.util.integration.JDBCDAO;

public class SkillJDBCDAO extends JDBCDAO implements SkillDAO {
  public static final String TABLE = "SKILL";
  public static final String KEY = "ID";
  public static final String SHORT_NAME = "SHORT_NAME";
  public static final String LONG_NAME = "LONG_NAME";
  public static final String COMMENTS = "COMMENTS";
  public static final String LEVEL_DESCRIPTIONS = "LEVELS_DESCRIPTIONS";
  public static final String STATUS = "STATUS";
  public static final char STATUS_PROPOSED = 'P';
  public static final char STATUS_APPROVED = 'A';
  public static final String ALL_FIELDS = "SKILL.ID,SKILL.SHORT_NAME,SKILL.LONG_NAME,SKILL.COMMENTS,SKILL.LEVELS_DESCRIPTIONS";
  public static final String RELATIONSHIP_TABLE = "RELATIONSHIP";
  public static final String RELATIONSHIP_ID_TO = "TO_ID";
  public static final String RELATIONSHIP_ID_FROM = "FROM_ID";
  public static final String RELATIONSHIP_TYPE = "TYPE";
  SkillMemoryDAO cache;
  public static final String RELATIONSHIP_INSERT = "INSERT INTO RELATIONSHIP (FROM_ID,TYPE,TO_ID) VALUES (?, ?, ?)";
  public static final String INSERT = "INSERT INTO SKILL (SKILL.ID,SKILL.SHORT_NAME,SKILL.LONG_NAME,SKILL.COMMENTS,SKILL.LEVELS_DESCRIPTIONS,STATUS) VALUES (?, ?, ?, ?, ?, ?)";
  public static final String RELATIONSHIP_REMOVE_FOR_SKILL = "DELETE FROM RELATIONSHIP WHERE TO_ID=? OR FROM_ID=?";
  public static final String SELECT_SOURCES = "SELECT  DISTINCT SKILL.ID,SKILL.SHORT_NAME,SKILL.LONG_NAME,SKILL.COMMENTS,SKILL.LEVELS_DESCRIPTIONS FROM SKILL,RELATIONSHIP WHERE SKILL.ID=RELATIONSHIP.FROM_ID AND RELATIONSHIP.TO_ID=? AND RELATIONSHIP.TYPE=? AND SKILL.STATUS='A'";
  public static final String SELECT_DESTINATIONS = "SELECT  DISTINCT SKILL.ID,SKILL.SHORT_NAME,SKILL.LONG_NAME,SKILL.COMMENTS,SKILL.LEVELS_DESCRIPTIONS FROM SKILL,RELATIONSHIP WHERE SKILL.ID=RELATIONSHIP.TO_ID AND RELATIONSHIP.FROM_ID=? AND RELATIONSHIP.TYPE=? AND SKILL.STATUS='A'";
  public static final String SELECT_BY_PRIMARY_KEY = "SELECT SKILL.ID,SKILL.SHORT_NAME,SKILL.LONG_NAME,SKILL.COMMENTS,SKILL.LEVELS_DESCRIPTIONS FROM SKILL WHERE ID=?";
  public static final String SELECT_NEW_KEY = "SELECT MAX(ID) FROM SKILL";
  public static final String SELECT_ALL = "SELECT ID,SHORT_NAME FROM SKILL ORDER BY SHORT_NAME";
  private static SkillJDBCDAO instance;
  public static final String LEVELS_SEPARATOR = "\t";
  public static final String SELECT_LEVELS_BY_SKILL = "SELECT LEVELS_DESCRIPTIONS FROM SKILL WHERE ID=?";
  public static final String REMOVE = "DELETE FROM SKILL WHERE ID=?";
  public static final String SKILL_UPDATE = "UPDATE SKILL SET SHORT_NAME=?,LONG_NAME=?,COMMENTS=?,LEVELS_DESCRIPTIONS=? WHERE ID=?";
  public static final String RELATIONSHIP_UPDATE = "UPDATE RELATIONSHIP SET TYPE=? WHERE FROM_ID=? AND TO_ID=?";
  public static final String RELATIONSHIP_REMOVE = "DELETE FROM RELATIONSHIP WHERE FROM_ID=? AND TYPE=? AND TO_ID=?";
  public static final String SELECT_ALL_DESTINATIONS = "SELECT ID,SHORT_NAME,RELATIONSHIP.TYPE FROM SKILL,RELATIONSHIP WHERE ID=TO_ID AND FROM_ID=?";

  protected SkillJDBCDAO() throws DAOException {
    this.setCacheEnabled(false);
  }

  public void link(Skill someFromSkill, String someType, SkillKey someToSkillKey) throws DAOException {
    Assert.notNullArgument(someFromSkill, "Cannot create relationship from a null skill");
    Assert.notNullArgument(someFromSkill, "Cannot create relationship to a null skill key");
    if (super.log.isDebugEnabled()) {
      super.log.debug("link (source=" + someFromSkill + ", type=" + someType + ", destination=" + someToSkillKey);
    }

    try {
      Connection connection = this.getConnection();

      try {
        PreparedStatement insertStatement = connection.prepareStatement("INSERT INTO RELATIONSHIP (FROM_ID,TYPE,TO_ID) VALUES (?, ?, ?)");
        insertStatement.setInt(1, someFromSkill.getId());
        insertStatement.setString(2, someType);
        insertStatement.setInt(3, someToSkillKey.getId());
        int rowCount = insertStatement.executeUpdate();
        if (rowCount != 1) {
          throw new DAOException("Failed to create relationship " + someType + " between " + someFromSkill + " and skill #" + someToSkillKey);
        }
      } finally {
        connection.close();
      }

    } catch (SQLException var12) {
      throw new DAOException("Could not create relationship " + someType + " between " + someFromSkill + " and skill #" + someToSkillKey, var12);
    }
  }

  public Skill create(Skill someSkill) throws DAOException {
    try {
      Connection connection = this.getConnection();

      Skill var6;
      try {
        int id;
        if (someSkill.getId() != Application.getSkillFactory().getRootId()) {
          connection.setTransactionIsolation(8);
          id = this.getNextId(connection);
        } else {
          id = someSkill.getId();
        }

        PreparedStatement insertStatement = connection.prepareStatement("INSERT INTO SKILL (SKILL.ID,SKILL.SHORT_NAME,SKILL.LONG_NAME,SKILL.COMMENTS,SKILL.LEVELS_DESCRIPTIONS,STATUS) VALUES (?, ?, ?, ?, ?, ?)");
        insertStatement.setInt(1, id);
        insertStatement.setString(2, someSkill.getShortName());
        insertStatement.setString(3, someSkill.getLongName());
        insertStatement.setString(4, someSkill.getComments());
        insertStatement.setString(5, this.marshallLevels(someSkill.getLevels()));
        insertStatement.setString(6, "A");
        if (super.log.isDebugEnabled()) {
          super.log.debug("Exécution de INSERT INTO SKILL (SKILL.ID,SKILL.SHORT_NAME,SKILL.LONG_NAME,SKILL.COMMENTS,SKILL.LEVELS_DESCRIPTIONS,STATUS) VALUES (?, ?, ?, ?, ?, ?)");
        }

        int rowsAffected = insertStatement.executeUpdate();
        if (rowsAffected != 1) {
          throw new DAOException("La requête INSERT INTO SKILL (SKILL.ID,SKILL.SHORT_NAME,SKILL.LONG_NAME,SKILL.COMMENTS,SKILL.LEVELS_DESCRIPTIONS,STATUS) VALUES (?, ?, ?, ?, ?, ?) a affecté " + rowsAffected + " enregistrements au lieu de 1");
        }

        someSkill.setId(id);
        someSkill.setSync(true);
        if (this.isCacheEnabled()) {
          this.cache.create(someSkill);
        }

        if (super.log.isDebugEnabled()) {
          super.log.debug("Compétence " + someSkill + " créée");
        }

        var6 = someSkill;
      } finally {
        connection.close();
      }

      return var6;
    } catch (SQLException var12) {
      throw new DAOException("Impossible de créer la compétence ", var12);
    }
  }

  public Collection getChilds(Skill someParentSkill) throws DAOException {
    Collection parentTypes = RelationshipTypeJDBCDAO.getInstance().getParentTypes();
    Iterator parentsIterator = parentTypes.iterator();
    HashSet allChilds = new HashSet();

    while(parentsIterator.hasNext()) {
      String parentType = (String)parentsIterator.next();
      Collection typedChilds = this.getSources(someParentSkill, parentType);
      allChilds.addAll(typedChilds);
    }

    return allChilds;
  }

  public Collection getParents(Skill someChildSkill) throws DAOException {
    Collection parentTypes = RelationshipTypeJDBCDAO.getInstance().getParentTypes();
    Iterator parentsIterator = parentTypes.iterator();
    HashSet allParents = new HashSet();

    while(parentsIterator.hasNext()) {
      String parentType = (String)parentsIterator.next();
      Collection typedParents = this.getDestinations(someChildSkill, parentType);
      allParents.addAll(typedParents);
    }

    return allParents;
  }

  public Collection getDestinations(Skill someSourceSkill) throws DAOException {
    try {
      Connection connection = this.getConnection();
      new HashSet();

      try {
        PreparedStatement selectStatement = connection.prepareStatement("SELECT ID,SHORT_NAME,RELATIONSHIP.TYPE FROM SKILL,RELATIONSHIP WHERE ID=TO_ID AND FROM_ID=?");
        selectStatement.setInt(1, someSourceSkill.getId());
        ResultSet resultSet = selectStatement.executeQuery();
        ArrayList destinationRelationships = new ArrayList();

        while(resultSet.next()) {
          Relationship relationship = this.rowToRelationship(someSourceSkill, resultSet);
          destinationRelationships.add(relationship);
        }

        ArrayList var14 = destinationRelationships;
        return var14;
      } finally {
        connection.close();
      }
    } catch (SQLException var13) {
      throw new DAOException("Impossible de lire les relations destinations depuis " + someSourceSkill, var13);
    }
  }

  public Collection getSources(Skill someDestinationSkill, String someRelationshipType) throws DAOException {
    Collection sourceSkills = null;
    if (this.isCacheEnabled()) {
      sourceSkills = this.cache.getSources(someDestinationSkill, someRelationshipType);
    }

    if (sourceSkills == null) {
      sourceSkills = this.readSources(someDestinationSkill, someRelationshipType);
      if (this.isCacheEnabled()) {
        this.cache.cacheSource(someDestinationSkill, someRelationshipType, sourceSkills);
      }
    }

    return sourceSkills;
  }

  public Collection getDestinations(Skill someSourceSkill, String someRelationshipType) throws DAOException {
    Collection destinationSkills = null;
    if (this.isCacheEnabled()) {
      destinationSkills = this.cache.getDestinations(someSourceSkill, someRelationshipType);
    }

    if (destinationSkills == null) {
      destinationSkills = this.readDestinations(someSourceSkill, someRelationshipType);
      if (this.isCacheEnabled()) {
        this.cache.cacheDestination(someSourceSkill, someRelationshipType, destinationSkills);
      }
    }

    return destinationSkills;
  }

  public int remove(SkillKey someSkillKey) throws DAOException {
    try {
      Connection connection = this.getConnection();

      try {
        PreparedStatement insertStatement = connection.prepareStatement("DELETE FROM SKILL WHERE ID=?");
        insertStatement.setInt(1, someSkillKey.getId());
        if (super.log.isDebugEnabled()) {
          super.log.debug("Exécution de DELETE FROM SKILL WHERE ID=?");
        }

        int rowsAffected = insertStatement.executeUpdate();
        if (rowsAffected != 1) {
          throw new DAOException("La suppression de la compétence de clé " + someSkillKey + " a échoué");
        }

        if (this.isCacheEnabled()) {
          this.cache.remove(someSkillKey);
        }

        if (super.log.isDebugEnabled()) {
          super.log.debug("Compétence de clé " + someSkillKey + " supprimée");
        }
      } finally {
        connection.close();
      }

      int var11 = this.removeRelationships(someSkillKey);
      return var11;
    } catch (Throwable var10) {
      throw new DAOException("Impossible de supprimer la compétence ", var10);
    }
  }

  public int removeRelationships(SkillKey someSkillKey) throws DAOException {
    try {
      Connection connection = this.getConnection();

      int var6;
      try {
        int id = someSkillKey.getId();
        PreparedStatement insertStatement = connection.prepareStatement("DELETE FROM RELATIONSHIP WHERE TO_ID=? OR FROM_ID=?");
        insertStatement.setInt(1, id);
        insertStatement.setInt(2, id);
        int affectedRows = insertStatement.executeUpdate();
        var6 = affectedRows;
      } finally {
        connection.close();
      }

      return var6;
    } catch (SQLException var12) {
      throw new DAOException("Impossible de supprimer les relations de la compétence de clé " + someSkillKey, var12);
    }
  }

  protected Collection readSources(Skill someDestinationSkill, String someRelationshipType) throws DAOException {
    if (super.log.isDebugEnabled()) {
      super.log.debug("readSources (" + someDestinationSkill + ")");
    }

    return this.readLinked(someDestinationSkill, "SELECT  DISTINCT SKILL.ID,SKILL.SHORT_NAME,SKILL.LONG_NAME,SKILL.COMMENTS,SKILL.LEVELS_DESCRIPTIONS FROM SKILL,RELATIONSHIP WHERE SKILL.ID=RELATIONSHIP.FROM_ID AND RELATIONSHIP.TO_ID=? AND RELATIONSHIP.TYPE=? AND SKILL.STATUS='A'", someRelationshipType);
  }

  protected Collection readLinked(Skill someParentSkill, String someQuery, String someRelationshipType) throws DAOException {
    Assert.notNullArgument(someParentSkill, "Impossible de lire les relations d'une compétence nulle");

    try {
      Connection connection = this.getConnection();
      HashSet linkedSkills = new HashSet();

      try {
        PreparedStatement selectByRefStatement = connection.prepareStatement(someQuery);
        selectByRefStatement.setInt(1, someParentSkill.getId());
        selectByRefStatement.setString(2, someRelationshipType);
        ResultSet skillSet = selectByRefStatement.executeQuery();

        while(skillSet.next()) {
          linkedSkills.add(this.rowToSkill((SkillKey)null, skillSet));
        }

        HashSet var8 = linkedSkills;
        return var8;
      } finally {
        connection.close();
      }
    } catch (SQLException var14) {
      throw new DAOException("Impossible de lire les liens '" + someRelationshipType + "' pour " + someParentSkill, var14);
    }
  }

  protected Collection readDestinations(Skill someSourceSkill, String someRelationshipType) throws DAOException {
    if (super.log.isDebugEnabled()) {
      super.log.debug("readDestinations (" + someSourceSkill + ")");
    }

    return this.readLinked(someSourceSkill, "SELECT  DISTINCT SKILL.ID,SKILL.SHORT_NAME,SKILL.LONG_NAME,SKILL.COMMENTS,SKILL.LEVELS_DESCRIPTIONS FROM SKILL,RELATIONSHIP WHERE SKILL.ID=RELATIONSHIP.TO_ID AND RELATIONSHIP.FROM_ID=? AND RELATIONSHIP.TYPE=? AND SKILL.STATUS='A'", someRelationshipType);
  }

  private int getNextId(Connection someConnection) throws SQLException {
    PreparedStatement selectMaxStatement = someConnection.prepareStatement("SELECT MAX(ID) FROM SKILL");
    ResultSet selectMaxResultSet = selectMaxStatement.executeQuery();
    if (!selectMaxResultSet.next()) {
      throw new SQLException("Impossible de récupérer la clé maximum avec SELECT MAX(ID) FROM SKILL");
    } else {
      return selectMaxResultSet.getInt(1) + 1;
    }
  }

  public Skill readByPrimaryKey(SkillKey someKey) throws DAOException, SkillNotFoundException {
    Assert.notNullArgument(someKey, "La clé de la compétence à lire ne peut être nulle");

    try {
      Connection connection = this.getConnection();

      Skill var6;
      try {
        PreparedStatement selectByRefStatement = connection.prepareStatement("SELECT SKILL.ID,SKILL.SHORT_NAME,SKILL.LONG_NAME,SKILL.COMMENTS,SKILL.LEVELS_DESCRIPTIONS FROM SKILL WHERE ID=?");
        selectByRefStatement.setInt(1, someKey.getId());
        if (super.log.isDebugEnabled()) {
          super.log.debug("Exécution de la requête JDBC " + selectByRefStatement);
        }

        ResultSet skillSet = selectByRefStatement.executeQuery();
        if (!skillSet.next()) {
          throw new SkillNotFoundException(someKey);
        }

        Skill namedSkill = this.rowToSkill(someKey, skillSet);
        var6 = namedSkill;
      } finally {
        connection.close();
      }

      return var6;
    } catch (SQLException var12) {
      throw new DAOException("Could not get Skill with ref=" + someKey, var12);
    }
  }

  protected Skill rowToSkill(SkillKey someSkillKey, ResultSet skillSet) throws SQLException, DAOException {
    Assert.notNullArgument(skillSet, "Le resultset ne peut être nul");
    int id = skillSet.getInt("ID");
    if (someSkillKey == null) {
      someSkillKey = new SkillKey(id);
    }

    String shortName = skillSet.getString("SHORT_NAME");
    SortedMap levels = null;
    String levelsDescription = skillSet.getString("LEVELS_DESCRIPTIONS");
    if (levelsDescription != null) {
      levels = unmarshallLevels(someSkillKey, levelsDescription);
    }

    Skill newSkill = new SkillImpl(id, shortName, skillSet.getString("LONG_NAME"), skillSet.getString("COMMENTS"), levels, Status.SYNC);
    if (super.log.isDebugEnabled()) {
      super.log.debug("read " + newSkill);
    }

    if (this.isCacheEnabled()) {
      this.cache.setByPrimaryKey(newSkill.getKey(), newSkill);
    }

    return newSkill;
  }

  protected Relationship rowToRelationship(Skill someSourceSkill, ResultSet someResultSet) throws SQLException, DAOException {
    Assert.notNullArgument(someResultSet, "Le resultset ne peut être nul");
    Relationship newRelationship = new RelationshipImpl(someSourceSkill, someResultSet.getString("TYPE"), new SkillKey(someResultSet.getInt("ID")), someResultSet.getString("SHORT_NAME"), Status.SYNC);
    return newRelationship;
  }

  protected String[] stringToStringArray(String someString) throws DAOException {
    Assert.notNullArgument(someString, "La chaîne à convertir en tableau de chaîne ne devrait pas être nulle");
    StringTokenizer tokenizer = new StringTokenizer(someString, "\n");
    ArrayList list = new ArrayList();

    while(tokenizer.hasMoreTokens()) {
      String text = tokenizer.nextToken();
      list.add(text);
    }

    String[] stringArray = new String[list.size()];

    for(int i = 0; i < stringArray.length; ++i) {
      stringArray[i] = (String)list.get(i);
    }

    return stringArray;
  }

  protected Map stringToMap(String commentsString) throws DAOException {
    StringTokenizer tokenizer = new StringTokenizer(commentsString, "\n");
    Map map = new HashMap();
    int var4 = 0;

    while(tokenizer.hasMoreTokens()) {
      String text = tokenizer.nextToken();
      map.put(new Integer(var4++), text);
    }

    return map;
  }

  private String commentsToString(Map someComments) {
    Iterator datesIterator = someComments.values().iterator();
    StringBuffer commentsToStringBuffer = new StringBuffer();

    while(datesIterator.hasNext()) {
      Date date = (Date)datesIterator.next();
      String comment = (String)someComments.get(date);
      commentsToStringBuffer.append(date).append(' ').append(comment);
    }

    return commentsToStringBuffer.toString();
  }

  public static SkillDAO getInstance() throws DAOException {
    if (instance == null) {
      Class var0 = class$org$orange$kicsa$integration$skill$SkillJDBCDAO == null ? (class$org$orange$kicsa$integration$skill$SkillJDBCDAO = class$("org.orange.kicsa.integration.skill.SkillJDBCDAO")) : class$org$orange$kicsa$integration$skill$SkillJDBCDAO;
      synchronized(var0) {
        instance = new SkillJDBCDAO();
      }
    }

    return instance;
  }

  public Skill getByPrimaryKey(SkillKey someKey) throws DAOException, SkillNotFoundException {
    Skill skill = null;
    if (this.isCacheEnabled()) {
      skill = this.cache.getByPrimaryKey(someKey);
    }

    if (skill == null) {
      skill = this.readByPrimaryKey(someKey);
    }

    return skill;
  }

  public Map getAll() throws DAOException {
    Map all = null;
    if (this.isCacheEnabled()) {
      all = this.cache.getAll();
    }

    if (all == null) {
      all = this.readAll();
    }

    return all;
  }

  public Map readAll() throws DAOException {
    try {
      Connection connection = this.getConnection();
      HashMap allSkills = new HashMap();

      try {
        PreparedStatement selectStatement = connection.prepareStatement("SELECT ID,SHORT_NAME FROM SKILL ORDER BY SHORT_NAME");
        ResultSet skillResultSet = selectStatement.executeQuery();

        while(skillResultSet.next()) {
          SkillKey key = new SkillKey(skillResultSet.getInt("ID"));
          String shortName = skillResultSet.getString("SHORT_NAME");
          allSkills.put(key, shortName);
        }

        HashMap var13 = allSkills;
        return var13;
      } finally {
        connection.close();
      }
    } catch (SQLException var12) {
      throw new DAOException("Impossible de lire l'ensemble des compétences", var12);
    }
  }

  public void clearCache() {
    if (this.isCacheEnabled()) {
      this.cache.clearCache();
    }

  }

  public SortedMap getLevels(SkillKey someSkillKey) throws DAOException, SkillNotFoundException {
    Assert.notNullArgument(someSkillKey, "La clé de la compétence dont on veut lire les niveaux ne peut être nulle. Utiliser SkillKey.ROOT_KEY si la compétence racine est visée.");
    SortedMap levels = null;
    if (this.isCacheEnabled()) {
      levels = this.cache.getLevels(someSkillKey);
    }

    if (levels == null) {
      levels = this.readLevels(someSkillKey);
    }

    return levels;
  }

  protected String marshallLevels(SortedMap someLevels) {
    Assert.notNullArgument(someLevels, "Le dictionnaire de niveaux à convertir en chaîne ne devrait pas être nul");
    StringBuffer stringBuffer = new StringBuffer();
    Iterator levelsIterator = someLevels.values().iterator();

    while(levelsIterator.hasNext()) {
      SkillLevel level = (SkillLevel)levelsIterator.next();
      stringBuffer.append(level.getLevel()).append(' ').append("\t").append(level.getName()).append(' ').append("\t").append(level.getDescription()).append(' ').append("\t");
    }

    if (stringBuffer.length() >= "\t".length()) {
      stringBuffer.delete(stringBuffer.length() - "\t".length(), stringBuffer.length());
    }

    return stringBuffer.toString();
  }

  protected static SortedMap unmarshallLevels(SkillKey someSkillKey, String someLevelDescription) throws DAOException {
    StringTokenizer tokenizer = new StringTokenizer(someLevelDescription, "\t");
    TreeMap levels = new TreeMap();

    while(tokenizer.hasMoreTokens()) {
      int levelIndex;
      try {
        levelIndex = Integer.parseInt(tokenizer.nextToken().trim());
      } catch (NumberFormatException var8) {
        throw new DAOException("Ability level number expected in " + someLevelDescription, var8);
      }

      if (!tokenizer.hasMoreTokens()) {
        throw new DAOException("Ability level name expected in " + someLevelDescription);
      }

      String levelName = tokenizer.nextToken();
      if (!tokenizer.hasMoreTokens()) {
        throw new DAOException("Ability level description in " + someLevelDescription);
      }

      String levelDescription = tokenizer.nextToken();
      SkillLevelImpl newSkillLevel = new SkillLevelImpl(levelIndex, levelName, levelDescription);
      levels.put(String.valueOf(levelIndex), newSkillLevel);
    }

    return levels;
  }

  public SortedMap readLevels(SkillKey someSkillKey) throws SkillNotFoundException, DAOException {
    Assert.notNullArgument(someSkillKey, "La clé de la compétence dont on veut lire les niveaux ne peut être nulle. Utiliser SkillKey.ROOT_KEY si la compétence racine est visée.");

    try {
      Connection connection = this.getConnection();

      SortedMap var7;
      try {
        PreparedStatement selectByRefStatement = connection.prepareStatement("SELECT LEVELS_DESCRIPTIONS FROM SKILL WHERE ID=?");
        selectByRefStatement.setInt(1, someSkillKey.getId());
        ResultSet resultSet = selectByRefStatement.executeQuery();
        if (!resultSet.next()) {
          throw new SkillNotFoundException(someSkillKey);
        }

        String levelsDescriptions = resultSet.getString("LEVELS_DESCRIPTIONS");
        SortedMap skillLevels = unmarshallLevels(someSkillKey, levelsDescriptions);
        if (super.log.isDebugEnabled()) {
          super.log.debug("read " + skillLevels);
        }

        if (this.isCacheEnabled()) {
          this.cache.setLevels(someSkillKey, skillLevels);
        }

        var7 = skillLevels;
      } finally {
        connection.close();
      }

      return var7;
    } catch (SQLException var13) {
      throw new DAOException("Impossible de récupérer les niveaux pour " + someSkillKey, var13);
    }
  }

  public boolean isCacheEnabled() {
    return this.cache != null;
  }

  public void setCacheEnabled(boolean someEnabledState) throws DAOException {
    if (someEnabledState) {
      this.cache = SkillMemoryDAO.getInstance();
    } else {
      this.cache = null;
    }

  }

  public Skill update(Skill someSkill) throws DAOException {
    try {
      Connection connection = this.getConnection();

      Skill var5;
      try {
        PreparedStatement updateStatement = connection.prepareStatement("UPDATE SKILL SET SHORT_NAME=?,LONG_NAME=?,COMMENTS=?,LEVELS_DESCRIPTIONS=? WHERE ID=?");
        updateStatement.setString(1, someSkill.getShortName());
        updateStatement.setString(2, someSkill.getLongName());
        updateStatement.setString(3, someSkill.getComments());
        updateStatement.setString(4, this.marshallLevels(someSkill.getLevels()));
        updateStatement.setInt(5, someSkill.getId());
        if (super.log.isDebugEnabled()) {
          super.log.debug("Exécution de UPDATE SKILL SET SHORT_NAME=?,LONG_NAME=?,COMMENTS=?,LEVELS_DESCRIPTIONS=? WHERE ID=?");
        }

        int rowsAffected = updateStatement.executeUpdate();
        if (rowsAffected != 1) {
          throw new DAOException("La mise à jour de la compétence " + someSkill + " a échoué (n'existe pas dans la base ?)");
        }

        someSkill.setSync(true);
        if (this.isCacheEnabled()) {
          this.cache.update(someSkill);
        }

        if (super.log.isDebugEnabled()) {
          super.log.debug("Compétence " + someSkill + " mis à jour");
        }

        var5 = someSkill;
      } finally {
        connection.close();
      }

      return var5;
    } catch (SQLException var11) {
      throw new DAOException("Impossible de créer la compétence ", var11);
    }
  }

  public void updateRelationship(SkillKey someSource, String someRelationshipType, SkillKey someDestination) throws DAOException {
    Assert.notNullArgument(someSource, "Cannot update a relationship from a null skill");
    Assert.notNullArgument(someRelationshipType, "Cannot update a relationship of null type");
    Assert.notNullArgument(someDestination, "Cannot update a relationship to a null skill");

    try {
      Connection connection = this.getConnection();

      try {
        PreparedStatement updateStatement = connection.prepareStatement("UPDATE RELATIONSHIP SET TYPE=? WHERE FROM_ID=? AND TO_ID=?");
        updateStatement.setString(1, someRelationshipType);
        updateStatement.setInt(2, someSource.getId());
        updateStatement.setInt(3, someDestination.getId());
        int affectedRows = updateStatement.executeUpdate();
        if (affectedRows != 1) {
          throw new DAOException("Update failed for relationship \"" + someRelationshipType + "\" between skill #" + someSource + " and skill #" + someDestination + " (not found ?)");
        }
      } finally {
        connection.close();
      }

    } catch (SQLException var12) {
      throw new DAOException("Impossible de mettre à jour la relation " + someRelationshipType + " entre " + someSource + " et " + someDestination, var12);
    }
  }

  public void removeRelationship(SkillKey someSource, String someRelationshipType, SkillKey someDestination) throws DAOException {
    try {
      Connection connection = this.getConnection();

      try {
        PreparedStatement insertStatement = connection.prepareStatement("DELETE FROM RELATIONSHIP WHERE FROM_ID=? AND TYPE=? AND TO_ID=?");
        insertStatement.setInt(1, someSource.getId());
        insertStatement.setString(2, someRelationshipType);
        insertStatement.setInt(3, someDestination.getId());
        int affectedRows = insertStatement.executeUpdate();
        if (affectedRows != 1) {
          throw new DAOException("La suppression de la relation " + someRelationshipType + " entre " + someSource + " et " + someDestination);
        }
      } finally {
        connection.close();
      }

    } catch (SQLException var12) {
      throw new DAOException("Impossible de supprimer la relation " + someRelationshipType + " entre " + someSource + " et " + someDestination, var12);
    }
  }
}
