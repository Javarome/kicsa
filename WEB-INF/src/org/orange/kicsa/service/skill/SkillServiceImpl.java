package org.orange.kicsa.service.skill;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.transaction.UserTransaction;
import org.apache.log4j.Logger;
import org.orange.kicsa.Application;
import org.orange.kicsa.SkillNotFoundException;
import org.orange.kicsa.business.skill.Relationship;
import org.orange.kicsa.business.skill.RelationshipType;
import org.orange.kicsa.business.skill.Skill;
import org.orange.kicsa.business.skill.SkillFactory;
import org.orange.kicsa.business.skill.SkillKey;
import org.orange.kicsa.business.skill.SkillTree;
import org.orange.kicsa.integration.AbstractDAOFactory;
import org.orange.kicsa.integration.skill.RelationshipTypeDAO;
import org.orange.kicsa.integration.skill.SkillDAO;
import org.orange.kicsa.service.ServiceException;
import org.orange.util.Assert;
import org.orange.util.NotImplementedException;
import org.orange.util.integration.DAOException;

public class SkillServiceImpl implements SkillService, SkillServiceRemote {
  private SkillTree skillTree;
  protected Logger log = Logger.getLogger(this.getClass());
  private static SkillServiceImpl instance = null;
  protected SkillDAO skillDAO;
  private RelationshipTypeDAO relationshipTypeDAO;

  protected SkillServiceImpl() {
    SkillFactory skillFactory = Application.getSkillFactory();
    Assert.notNull(skillFactory, "skillFactory cannot be null. Check if Application.init() performed well");
    this.skillTree = skillFactory.getTree();
    Assert.notNull(this.skillTree, "SkillTree cannot be null");

    try {
      AbstractDAOFactory daoFactory = Application.getDAOFactory();
      this.skillDAO = daoFactory.createSkillDAO();
      this.relationshipTypeDAO = daoFactory.createRelationshipTypeDAO();
    } catch (Throwable var3) {
      this.log.error(var3);
      throw new ServiceException(var3);
    }
  }

  public Collection findChilds(Skill someParent) throws SkillNotFoundException {
    Collection childs = this.skillTree.getChilds(someParent);
    return childs;
  }

  public Collection findParents(Skill someChild) throws SkillNotFoundException {
    Collection parents = this.skillTree.getParents(someChild);
    return parents;
  }

  public Skill findByPrimaryKey(SkillKey someSkillKey) throws SkillNotFoundException {
    try {
      Skill foundSkill = this.skillDAO.getByPrimaryKey(someSkillKey);
      return foundSkill;
    } catch (DAOException var3) {
      throw new ServiceException(var3);
    }
  }

  public Skill createRoot(Skill someRootSkill) {
    try {
      return this.skillDAO.create(someRootSkill);
    } catch (DAOException var3) {
      throw new ServiceException(var3);
    }
  }

  public Skill create(Skill someSkill, Collection someRelationships, Collection someOwners) {
    Assert.notNullArgument(someSkill, "La compétence à créer n'est pas nulle");
    Assert.notNullArgument(someRelationships, "Les relations de la compétence à créer ne peuvent être nulles");
    Iterator relationshipsIterator = someRelationships.iterator();
    Assert.notEmptyArgument(relationshipsIterator, "La compétence à créer doit avoir au moins une relation");
    Assert.notNullArgument(someOwners, "Les responsables de la compétence à créer ne peuvent être nuls");
    Iterator ownersIterator = someOwners.iterator();
    Assert.notEmptyArgument(ownersIterator, "La compétence à créer doit avoir au moins un responsable");
    UserTransaction currentTransaction = null;

    try {
      InitialContext namingContext = new InitialContext();
      currentTransaction = (UserTransaction)namingContext.lookup("java:comp/UserTransaction");
      currentTransaction.begin();
      Skill newSkill = this.skillDAO.create(someSkill);

      do {
        Relationship relationship = (Relationship)relationshipsIterator.next();
        this.link(newSkill, relationship.getType(), relationship.getDestinationKey());
      } while(relationshipsIterator.hasNext());

      currentTransaction.commit();
      return newSkill;
    } catch (NamingException var10) {
      throw new ServiceException("Impossible de récupérer java:comp/UserTransaction dans le service de nommage en raison de " + var10);
    } catch (DAOException var11) {
      throw new ServiceException("Impossible de créer la compétence " + someSkill + " en raison de " + var11);
    } catch (Exception var12) {
      throw new ServiceException(var12);
    }
  }

  public Skill update(Skill someSkill, Collection someRelationships, Collection someOwners) {
    Assert.notNullArgument(someSkill, "La compétence à modifier ne peut être nulle");
    Assert.notNullArgument(someRelationships, "Les relations de la compétence à modifier ne peuvent être nulles");
    Iterator relationshipsIterator = someRelationships.iterator();
    Assert.notEmptyArgument(relationshipsIterator, "La compétence à modifier doit avoir au moins une relation");
    UserTransaction currentTransaction = null;

    try {
      InitialContext namingContext = new InitialContext();
      currentTransaction = (UserTransaction)namingContext.lookup("java:comp/UserTransaction");
      currentTransaction.begin();
      someSkill = this.skillDAO.update(someSkill);

      do {
        Relationship relationship = (Relationship)relationshipsIterator.next();
        if (relationship.isNew()) {
          this.link(someSkill, relationship.getType(), relationship.getDestinationKey());
        } else if (relationship.isDeleted()) {
          this.skillDAO.removeRelationship(someSkill.getKey(), relationship.getType(), relationship.getDestinationKey());
        } else if (relationship.isDirty()) {
          this.skillDAO.updateRelationship(someSkill.getKey(), relationship.getType(), relationship.getDestinationKey());
        } else {
          if (!relationship.isSync()) {
            throw new ServiceException("Editable state not supported in " + relationship);
          }

          this.log.info("Nothing to update for " + relationship);
        }
      } while(relationshipsIterator.hasNext());

      currentTransaction.commit();
      return someSkill;
    } catch (NamingException var9) {
      throw new ServiceException("Could not retrieve java:comp/UserTransaction from the naming service", var9);
    } catch (DAOException var10) {
      throw new ServiceException("Could not update " + someSkill, var10);
    } catch (Exception var11) {
      throw new ServiceException(var11);
    }
  }

  public int remove(SkillKey someSkillKey) {
    if (this.skillDAO == null) {
      throw new ServiceException("relationShipDAO devrait être initialisé");
    } else {
      try {
        return this.skillDAO.remove(someSkillKey);
      } catch (DAOException var3) {
        throw new ServiceException(var3);
      }
    }
  }

  public static SkillServiceImpl getInstance() {
    if (instance == null) {
      Class var0 = class$org$orange$kicsa$service$skill$SkillServiceImpl == null ? (class$org$orange$kicsa$service$skill$SkillServiceImpl = class$("org.orange.kicsa.service.skill.SkillServiceImpl")) : class$org$orange$kicsa$service$skill$SkillServiceImpl;
      synchronized(var0) {
        if (instance == null) {
          instance = new SkillServiceImpl();
        }
      }
    }

    return instance;
  }

  public void link(Skill someSourceSkill, String relationshipType, SkillKey someDestinationSkill) {
    try {
      this.skillDAO.link(someSourceSkill, relationshipType, someDestinationSkill);
    } catch (DAOException var5) {
      throw new ServiceException(var5);
    }
  }

  public void addOwner(SkillKey someSkillKey, String someType, SkillKey someDestinationSkillKey) {
    throw new NotImplementedException();
  }

  public Map findAll() {
    try {
      Map allSkills = this.skillDAO.getAll();
      return allSkills;
    } catch (DAOException var2) {
      throw new ServiceException(var2);
    }
  }

  public Map findRelationshipTypes() {
    try {
      Map types = this.relationshipTypeDAO.getAll();
      return types;
    } catch (DAOException var2) {
      throw new ServiceException(var2);
    }
  }

  public RelationshipType createRelationshipType(RelationshipType someRelationshipType) {
    try {
      return this.relationshipTypeDAO.create(someRelationshipType);
    } catch (DAOException var3) {
      throw new ServiceException(var3);
    }
  }

  public void removeRelationshipType(String someNewRelationshipType) {
    try {
      this.relationshipTypeDAO.remove(someNewRelationshipType);
    } catch (DAOException var3) {
      throw new ServiceException(var3);
    }
  }

  public Collection findDestinations(Skill someSkill) throws SkillNotFoundException {
    try {
      Collection relationshipsFromBySkill = this.skillDAO.getDestinations(someSkill);
      return relationshipsFromBySkill;
    } catch (DAOException var3) {
      throw new ServiceException(var3);
    }
  }

  public Collection findDestinations(Skill someSkill, String someRelationshipType) throws SkillNotFoundException {
    try {
      Collection relationshipsFromBySkill = this.skillDAO.getDestinations(someSkill, someRelationshipType);
      return relationshipsFromBySkill;
    } catch (DAOException var4) {
      throw new ServiceException(var4);
    }
  }

  public Collection findSources(Skill someSkill, String someRelationshipType) throws SkillNotFoundException {
    try {
      Collection relationshipsToBySkill = this.skillDAO.getSources(someSkill, someRelationshipType);
      return relationshipsToBySkill;
    } catch (DAOException var4) {
      throw new ServiceException(var4);
    }
  }
}
