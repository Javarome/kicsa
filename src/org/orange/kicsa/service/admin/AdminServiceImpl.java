package org.orange.kicsa.service.admin;

import java.util.Iterator;
import java.util.Map;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.transaction.UserTransaction;
import org.apache.log4j.Logger;
import org.orange.kicsa.Application;
import org.orange.kicsa.business.skill.RelationshipType;
import org.orange.kicsa.business.skill.Skill;
import org.orange.kicsa.business.skill.SkillKey;
import org.orange.kicsa.integration.AbstractDAOFactory;
import org.orange.kicsa.integration.skill.RelationshipTypeDAO;
import org.orange.kicsa.integration.skill.RelationshipTypeJDBCDAO;
import org.orange.kicsa.service.ServiceException;
import org.orange.util.Assert;
import org.orange.util.NotImplementedException;
import org.orange.util.integration.DAOException;

public class AdminServiceImpl implements AdminService {
  protected Logger log = Logger.getLogger(this.getClass());
  private RelationshipTypeDAO relationshipTypeDAO;
  private static AdminServiceImpl instance;

  public AdminServiceImpl() {
    try {
      AbstractDAOFactory daoFactory = Application.getDAOFactory();
      this.relationshipTypeDAO = daoFactory.createRelationshipTypeDAO();
    } catch (Throwable var2) {
      this.log.error(var2);
      throw new ServiceException(var2);
    }
  }

  public static AdminServiceImpl getInstance() throws DAOException {
    if (instance == null) {
      Class var0 = class$org$orange$kicsa$service$admin$AdminServiceImpl == null ? (class$org$orange$kicsa$service$admin$AdminServiceImpl = class$("org.orange.kicsa.service.admin.AdminServiceImpl")) : class$org$orange$kicsa$service$admin$AdminServiceImpl;
      synchronized(var0) {
        if (instance == null) {
          instance = new AdminServiceImpl();
        }
      }
    }

    return instance;
  }

  public void update(Skill someRootSkill, Map someRelationshipTypes) {
    Assert.notNullArgument(someRelationshipTypes, "relationshipTypes must not be null");
    Iterator relationshipTypesIterator = someRelationshipTypes.values().iterator();
    Assert.notEmptyArgument(relationshipTypesIterator, "RelationshipTypes must have one element at least");
    UserTransaction currentTransaction = null;

    try {
      if (!someRootSkill.equals(Application.getSkillFactory().getRoot())) {
        Application.getSkillFactory().setRoot(someRootSkill);
      }

      InitialContext namingContext = new InitialContext();
      currentTransaction = (UserTransaction)namingContext.lookup("java:comp/UserTransaction");
      currentTransaction.begin();

      do {
        RelationshipType relationshipType = (RelationshipType)relationshipTypesIterator.next();
        if (relationshipType.isNew()) {
          this.relationshipTypeDAO.create(relationshipType);
        } else if (relationshipType.isDeleted()) {
          this.relationshipTypeDAO.remove(relationshipType.getName());
        } else if (relationshipType.isDirty()) {
          this.relationshipTypeDAO.update(relationshipType);
        } else {
          if (!relationshipType.isSync()) {
            throw new ServiceException("Editable state not supported in " + relationshipType);
          }

          this.log.info("Nothing to update for " + relationshipType);
        }
      } while(relationshipTypesIterator.hasNext());

      currentTransaction.commit();
    } catch (NamingException var8) {
      throw new ServiceException("Could not retrieve java:comp/UserTransaction from the naming service", var8);
    } catch (DAOException var9) {
      throw new ServiceException("Could not update administration parameters", var9);
    } catch (Exception var10) {
      throw new ServiceException(var10);
    }
  }

  public RelationshipType createRelationshipType(RelationshipType someRelationshipType) {
    try {
      return RelationshipTypeJDBCDAO.getInstance().create(someRelationshipType);
    } catch (DAOException var3) {
      throw new ServiceException(var3);
    }
  }

  public void createSkillLevel(SkillKey someSkillKey, String someNewLevelName, String someNewLevelDescription, int someNewLevelIndex) {
    throw new NotImplementedException();
  }
}
