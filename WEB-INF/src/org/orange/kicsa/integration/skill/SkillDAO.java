package org.orange.kicsa.integration.skill;

import java.util.Collection;
import java.util.Map;
import java.util.SortedMap;
import org.orange.kicsa.SkillNotFoundException;
import org.orange.kicsa.business.skill.Skill;
import org.orange.kicsa.business.skill.SkillKey;
import org.orange.util.integration.DAOException;

public interface SkillDAO {
  SortedMap getLevels(SkillKey var1) throws DAOException, SkillNotFoundException;

  Skill getByPrimaryKey(SkillKey var1) throws DAOException, SkillNotFoundException;

  Collection getParents(Skill var1) throws DAOException;

  Skill create(Skill var1) throws DAOException;

  Skill update(Skill var1) throws DAOException;

  void link(Skill var1, String var2, SkillKey var3) throws DAOException;

  void updateRelationship(SkillKey var1, String var2, SkillKey var3) throws DAOException;

  void removeRelationship(SkillKey var1, String var2, SkillKey var3) throws DAOException;

  int removeRelationships(SkillKey var1) throws DAOException;

  Collection getDestinations(Skill var1) throws DAOException;

  Collection getDestinations(Skill var1, String var2) throws DAOException;

  Collection getSources(Skill var1, String var2) throws DAOException;

  Collection getChilds(Skill var1) throws DAOException;

  void clearCache();

  Map getAll() throws DAOException;

  int remove(SkillKey var1) throws DAOException;
}
