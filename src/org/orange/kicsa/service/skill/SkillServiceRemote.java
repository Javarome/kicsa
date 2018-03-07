package org.orange.kicsa.service.skill;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Collection;
import java.util.Map;
import org.orange.kicsa.SkillNotFoundException;
import org.orange.kicsa.business.skill.Skill;
import org.orange.kicsa.business.skill.SkillKey;

public interface SkillServiceRemote extends Remote {
  Skill findByPrimaryKey(SkillKey var1) throws RemoteException, SkillNotFoundException;

  Skill create(Skill var1, Collection var2, Collection var3) throws RemoteException;

  Skill update(Skill var1, Collection var2, Collection var3) throws RemoteException;

  int remove(SkillKey var1) throws RemoteException;

  Skill createRoot(Skill var1) throws RemoteException;

  Collection findChilds(Skill var1) throws RemoteException, SkillNotFoundException;

  Collection findParents(Skill var1) throws RemoteException, SkillNotFoundException;

  void link(Skill var1, String var2, SkillKey var3) throws RemoteException;

  Collection findDestinations(Skill var1) throws RemoteException, SkillNotFoundException;

  Map findAll() throws RemoteException;

  Map findRelationshipTypes() throws RemoteException;
}
