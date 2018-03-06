package org.orange.kicsa.integration.skill;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import org.orange.kicsa.business.skill.RelationshipType;
import org.orange.util.NotImplementedException;
import org.orange.util.integration.DAOException;

public class RelationshipTypeMemoryDAO implements RelationshipTypeDAO {
  private static RelationshipTypeMemoryDAO instance;
  final Object[][] TYPES;
  Collection cachedParentTypes;

  public RelationshipType create(RelationshipType someRelationshipType) throws DAOException {
    throw new NotImplementedException();
  }

  public static RelationshipTypeMemoryDAO getInstance() throws DAOException {
    if (instance == null) {
      Class var0 = class$org$orange$kicsa$integration$skill$RelationshipTypeMemoryDAO == null ? (class$org$orange$kicsa$integration$skill$RelationshipTypeMemoryDAO = class$("org.orange.kicsa.integration.skill.RelationshipTypeMemoryDAO")) : class$org$orange$kicsa$integration$skill$RelationshipTypeMemoryDAO;
      synchronized(var0) {
        if (instance == null) {
          instance = new RelationshipTypeMemoryDAO();
        }
      }
    }

    return instance;
  }

  protected RelationshipTypeMemoryDAO() throws DAOException {
    this.TYPES = new Object[][]{{"Parent", Boolean.TRUE}, {"Voir aussi", Boolean.FALSE}, {"Utilise", Boolean.FALSE}};
  }

  public SortedMap getAll() throws DAOException {
    SortedMap types = new TreeMap();

    for(int i = 0; i < this.TYPES.length; ++i) {
      String typeName = (String)this.TYPES[i][0];
      Boolean isParent = (Boolean)this.TYPES[i][1];
      types.put(typeName, isParent);
    }

    return types;
  }

  public Collection getParentTypes() throws DAOException {
    if (this.cachedParentTypes == null) {
      this.cachedParentTypes = this.readParentTypes();
    }

    return this.cachedParentTypes;
  }

  public Collection readParentTypes() throws DAOException {
    Set parents = new HashSet(1);
    parents.add(this.TYPES[0][0]);
    return parents;
  }

  public void remove(String someRelationshipType) throws DAOException {
    throw new NotImplementedException();
  }

  public void update(RelationshipType someRelationshipType) throws DAOException {
    throw new NotImplementedException();
  }
}
