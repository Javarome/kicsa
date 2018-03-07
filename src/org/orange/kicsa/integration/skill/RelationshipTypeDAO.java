package org.orange.kicsa.integration.skill;

import java.util.Collection;
import java.util.SortedMap;
import org.orange.kicsa.business.skill.RelationshipType;
import org.orange.util.integration.DAOException;

public interface RelationshipTypeDAO {
  RelationshipType create(RelationshipType var1) throws DAOException;

  void update(RelationshipType var1) throws DAOException;

  SortedMap getAll() throws DAOException;

  Collection getParentTypes() throws DAOException;

  void remove(String var1) throws DAOException;
}
