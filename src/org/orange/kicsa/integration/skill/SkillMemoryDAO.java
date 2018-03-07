package org.orange.kicsa.integration.skill;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.SortedMap;
import java.util.StringTokenizer;
import java.util.TreeMap;
import org.apache.log4j.Logger;
import org.orange.kicsa.SkillNotFoundException;
import org.orange.kicsa.business.Editable.Status;
import org.orange.kicsa.business.skill.Skill;
import org.orange.kicsa.business.skill.SkillImpl;
import org.orange.kicsa.business.skill.SkillKey;
import org.orange.kicsa.business.skill.SkillLevel;
import org.orange.kicsa.business.skill.SkillLevelImpl;
import org.orange.util.Assert;
import org.orange.util.NotImplementedException;
import org.orange.util.integration.DAOException;

public class SkillMemoryDAO implements SkillDAO {
  private Map sourcesCache;
  private Map destinationsCache;
  final Object[][] SKILL_LEVELS = new Object[][]{{new Integer(1), new SkillLevelImpl(1, "Débutant", "Celui qui débute")}, {new Integer(2), new SkillLevelImpl(2, "Maîtrise", "Celui qui débute moins")}, {new Integer(3), new SkillLevelImpl(3, "Expert", "Celui qui débute plus")}};
  final Object[][] SKILLS;
  final Object[][] RELATIONSHIPS;
  Logger log;
  private Map keyCache;
  private Map levelsCache;
  private static SkillMemoryDAO instance;
  private Map allSkillsCache;
  public static final String LEVELS_SEPARATOR = "\t";

  protected SkillMemoryDAO() throws DAOException {
    this.SKILLS = new Object[][]{{new SkillKey(1), new SkillImpl(1, "Compétences", "Compétences EASY", "", (SortedMap)null, Status.SYNC)}, {new SkillKey(2), new SkillImpl(2, "WAS", "Websphere Application Server", "", (SortedMap)null, Status.SYNC)}, {new SkillKey(3), new SkillImpl(3, "IBM", "International Business Machines", "", (SortedMap)null, Status.SYNC)}, {new SkillKey(4), new SkillImpl(4, "Produits", "", "", (SortedMap)null, Status.SYNC)}, {new SkillKey(5), new SkillImpl(5, "J2EE", "Java 2 Enterprise Edition", "", (SortedMap)null, Status.SYNC)}, {new SkillKey(6), new SkillImpl(6, "WAS 3.5", "Versions 3.5.x de WAS", "", (SortedMap)null, Status.SYNC)}};
    this.RELATIONSHIPS = new Object[][]{{new SkillKey(2), "Parent", new SkillKey(1)}, {new SkillKey(3), "Parent", new SkillKey(1)}, {new SkillKey(4), "Parent", new SkillKey(2)}, {new SkillKey(2), "Voir aussi", new SkillKey(3)}};
    this.log = Logger.getLogger(this.getClass());
    this.levelsCache = new HashMap();
    this.sourcesCache = new HashMap();
    this.destinationsCache = new HashMap();
    SortedMap levels = new TreeMap();

    for(int i = 0; i < this.SKILL_LEVELS.length; ++i) {
      levels.put(this.SKILL_LEVELS[i][0], this.SKILL_LEVELS[i][1]);
    }

  }

  public Skill create(Skill someSkill) throws DAOException {
    int newId = this.getNextId();
    someSkill.setId(newId);
    this.setByPrimaryKey(someSkill.getKey(), someSkill);
    if (this.log.isDebugEnabled()) {
      this.log.debug("Compétence " + someSkill + " créée");
    }

    return someSkill;
  }

  public void link(Skill someSourceSkill, String someType, SkillKey someDestinationSkill) throws DAOException {
    this.cache(someSourceSkill, someType, someDestinationSkill);
  }

  public Collection getSources(Skill someDestinationSkill, String someRelationshipType) {
    Collection sources = null;
    Map skillCache = this.getCachedSources(someDestinationSkill);
    if (skillCache == null) {
      skillCache = new HashMap();
      this.sourcesCache.put(someRelationshipType, skillCache);
    }

    sources = (Collection)((Map)skillCache).get(someRelationshipType);
    return sources;
  }

  protected void cache(Skill someSourceSkill, String someType, SkillKey someDestinationSkill) {
    this.cacheSource(someSourceSkill, someType, someDestinationSkill);
    this.cacheDestination(someSourceSkill, someType, someDestinationSkill);
  }

  protected void cacheSource(Skill someDestinationSkill, String someRelationshipType, SkillKey someSourceSkill) {
    Collection sources = this.getSources(someDestinationSkill, someRelationshipType);
    sources.add(someSourceSkill);
  }

  protected void cacheSource(Skill someDestinationSkill, String someRelationshipType, Collection someSources) {
    Map skillCache = this.getCachedSources(someDestinationSkill);
    if (skillCache == null) {
      skillCache = new HashMap();
      this.sourcesCache.put(someRelationshipType, skillCache);
    }

    ((Map)skillCache).put(someRelationshipType, someSources);
  }

  public void cacheDestination(Skill someSourceSkill, String someRelationshipType, SkillKey someDestinationSkill) {
    Collection destinations = this.getDestinations(someSourceSkill, someRelationshipType);
    destinations.add(someDestinationSkill);
  }

  protected void cacheDestination(Skill someSourceSkill, String someRelationshipType, Collection someDestinations) {
    Map skillCache = this.getCachedDestinations(someSourceSkill);
    if (skillCache == null) {
      skillCache = new HashMap();
      this.destinationsCache.put(someSourceSkill, skillCache);
    }

    ((Map)skillCache).put(someRelationshipType, someDestinations);
  }

  public Collection getChilds(Skill someParentSkill) throws DAOException {
    String parentType = "Parent";
    Collection childs = this.getSources(someParentSkill, parentType);
    return childs;
  }

  public Collection getDestinations(Skill someSourceSkill) throws DAOException {
    Collection destinations = null;
    Map skillCache = this.getCachedDestinations(someSourceSkill);
    if (skillCache != null) {
      destinations = skillCache.values();
    }

    return destinations;
  }

  public Collection getDestinations(Skill someSourceSkill, String someRelationshipType) {
    Collection destinations = null;
    Map skillCache = this.getCachedDestinations(someSourceSkill);
    if (skillCache == null) {
      Map skillCache = new HashMap();
      this.destinationsCache.put(someSourceSkill, skillCache);
    }

    return (Collection)destinations;
  }

  protected Map getCachedDestinations(Skill someSourceSkill) {
    Map skillCache = null;
    skillCache = (Map)this.destinationsCache.get(someSourceSkill);
    if (this.log.isDebugEnabled()) {
      this.log.debug("Les destinations pour la compétence  " + someSourceSkill + " sont " + skillCache + " dans le cache");
    }

    return skillCache;
  }

  protected Map getCachedSources(Skill someDestinationSkill) {
    Map skillCache = null;
    skillCache = (Map)this.sourcesCache.get(someDestinationSkill);
    if (this.log.isDebugEnabled()) {
      this.log.debug("Les sources pour la compétence  " + someDestinationSkill + " sont " + skillCache + " dans le cache");
    }

    return skillCache;
  }

  public int removeRelationships(SkillKey someSkillKey) throws DAOException {
    int affectedRows = 0;
    return affectedRows;
  }

  private int getNextId() {
    return this.keyCache.size();
  }

  protected void setByPrimaryKey(SkillKey someKey, Skill someSkill) {
    this.keyCache.put(someKey, someSkill);
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

  public static SkillMemoryDAO getInstance() throws DAOException {
    if (instance == null) {
      Class var0 = class$org$orange$kicsa$integration$skill$SkillMemoryDAO == null ? (class$org$orange$kicsa$integration$skill$SkillMemoryDAO = class$("org.orange.kicsa.integration.skill.SkillMemoryDAO")) : class$org$orange$kicsa$integration$skill$SkillMemoryDAO;
      synchronized(var0) {
        instance = new SkillMemoryDAO();
      }
    }

    return instance;
  }

  public Skill getByPrimaryKey(SkillKey someSkillKey) throws DAOException, SkillNotFoundException {
    Skill skill = (Skill)this.keyCache.get(someSkillKey);
    return skill;
  }

  public Map getAll() throws DAOException {
    return this.allSkillsCache;
  }

  public void clearCache() {
    this.sourcesCache.clear();
    this.destinationsCache.clear();
    this.allSkillsCache = null;
    this.keyCache.clear();
  }

  public SortedMap getLevels(SkillKey someSkillKey) throws DAOException, SkillNotFoundException {
    if (someSkillKey == null) {
      throw new DAOException("La clé de la compétence dont on veut lire les niveaux ne peut être nulle. Utiliser SkillKey.ROOT_KEY si la compétence racine est visée.");
    } else {
      SortedMap levels = (SortedMap)this.levelsCache.get(someSkillKey);
      return levels;
    }
  }

  public String levelsToString(SortedMap someLevels) {
    if (someLevels == null) {
      throw new RuntimeException("Le dictionnaire de niveaux à convertir en chaîne ne devrait pas être nul");
    } else {
      StringBuffer stringBuffer = new StringBuffer();
      Iterator levelsIterator = someLevels.values().iterator();

      while(levelsIterator.hasNext()) {
        SkillLevel level = (SkillLevel)levelsIterator.next();
        stringBuffer.append(level.getLevel()).append("\t").append(level.getName()).append("\t").append(level.getDescription()).append("\t");
      }

      if (stringBuffer.length() >= "\t".length()) {
        stringBuffer.delete(stringBuffer.length() - "\t".length(), stringBuffer.length());
      }

      return stringBuffer.toString();
    }
  }

  protected static SortedMap stringToLevels(SkillKey someSkillKey, String someLevelDescription) throws DAOException {
    StringTokenizer tokenizer = new StringTokenizer(someLevelDescription, "\t");
    TreeMap levels = new TreeMap();

    while(tokenizer.hasMoreTokens()) {
      int levelIndex = Integer.parseInt(tokenizer.nextToken());
      if (!tokenizer.hasMoreTokens()) {
        throw new DAOException("Nom de niveau attendu dans " + someLevelDescription);
      }

      String levelName = tokenizer.nextToken();
      if (!tokenizer.hasMoreTokens()) {
        throw new DAOException("Description de niveau attendu dans " + someLevelDescription);
      }

      String levelDescription = tokenizer.nextToken();
      SkillLevelImpl newSkillLevel = new SkillLevelImpl(levelIndex, levelName, levelDescription);
      levels.put(new Integer(levelIndex), newSkillLevel);
    }

    return levels;
  }

  protected void setLevels(SkillKey someSkillKey, SortedMap someLevels) {
    this.levelsCache.put(someSkillKey, someLevels);
  }

  public int remove(SkillKey someSkillKey) throws DAOException {
    this.keyCache.remove(someSkillKey);
    if (this.log.isDebugEnabled()) {
      this.log.debug("Compétence de clé " + someSkillKey + " supprimée");
    }

    int removedRelationshipCount = this.removeRelationships(someSkillKey);
    return removedRelationshipCount;
  }

  public Collection getParents(Skill someChildSkill) throws DAOException {
    throw new NotImplementedException();
  }

  public Skill update(Skill someSkill) throws DAOException {
    throw new NotImplementedException();
  }

  public void removeRelationship(SkillKey someSkillKey, String relationshipType, SkillKey relationshipDestination) throws DAOException {
    throw new NotImplementedException();
  }

  public void updateRelationship(SkillKey someSkillKey, String relationshipType, SkillKey relationshipDestination) throws DAOException {
    throw new NotImplementedException();
  }
}
