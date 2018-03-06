package org.orange.kicsa.presentation.control.skill;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.orange.kicsa.Application;
import org.orange.kicsa.business.employee.Employee;
import org.orange.kicsa.business.employee.EmployeeImpl;
import org.orange.kicsa.business.skill.Relationship;
import org.orange.kicsa.business.skill.RelationshipImpl;
import org.orange.kicsa.business.skill.Skill;
import org.orange.kicsa.business.skill.SkillKey;
import org.orange.kicsa.presentation.view.SkillForm;
import org.orange.kicsa.service.ability.AbilityService;
import org.orange.kicsa.service.ability.AbilityServicePool;
import org.orange.kicsa.service.employee.EmployeeService;
import org.orange.kicsa.service.employee.EmployeeServicePool;
import org.orange.kicsa.service.skill.SkillService;
import org.orange.kicsa.service.skill.SkillServicePool;
import org.orange.util.Assert;

public class EditAction extends DispatchAction {
  public static final String FAILURE_FORWARD = "FAILURE";
  public static final String DATA_MISSING_FORWARD = "DATA_MISSING";
  public static final String UPDATED_FORWARD = "UPDATED";
  public static final String CREATED_FORWARD = "CREATED";
  public static final String REMOVED_FORWARD = "REMOVED";
  public static final String LEVELS = "levels";
  public static final String RELATIONSHIP_TYPES = "relationshipTypes";
  public static final String SKILL_LIST = "skillList";
  public static final String EMPLOYEE_LIST = "employeeList";
  public static final String RELATIONSHIP_LIST = "relationshipList";
  public static final String SKILL_TO_EDIT = "skillToEdit";
  protected Logger log = Logger.getLogger(this.getClass());
  private SkillServicePool skillServicePool = SkillServicePool.getInstance();
  private EmployeeServicePool employeeServicePool = EmployeeServicePool.getInstance();
  private AbilityServicePool abilityServicePool = AbilityServicePool.getInstance();

  public EditAction() {
  }

  public ActionForward create(ActionMapping mapping, ActionForm someForm, HttpServletRequest someRequest, HttpServletResponse someResponse) throws Exception {
    Assert.notNullArgument(someForm, "Le formulaire transmis en devrait pas être nul. Vérifier si l'instance de form bean a pu être créée avec son constructeur par défaut");
    Assert.isValidArgument(someForm instanceof SkillForm, "Le formulaire " + someForm + " n'est pas du type " + (class$org$orange$kicsa$presentation$view$SkillForm == null ? (class$org$orange$kicsa$presentation$view$SkillForm = class$("org.orange.kicsa.presentation.view.SkillForm")) : class$org$orange$kicsa$presentation$view$SkillForm) + " attendu");
    SkillForm skillForm = (SkillForm)someForm;
    if (this.log.isDebugEnabled()) {
      this.log.debug("form input = " + someForm);
    }

    SkillService skillService = (SkillService)this.skillServicePool.borrowObject();

    ActionForward forward;
    try {
      Skill skill;
      if (someRequest.getMethod().equals("POST")) {
        if (this.isTokenValid(someRequest)) {
          skill = skillForm.getSkill();
          skill.setNew(true);
          skill = skillService.create(skill, skillForm.getRelationships(), skillForm.getOwners());
          skillForm.setSkill(skill);
          skillForm.setAbilities(new TreeMap());
          this.resetToken(someRequest);
          forward = mapping.findForward("CREATED");
        } else {
          this.log.error("Creation already done");
          ActionErrors errors = new ActionErrors();
          errors.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionError("error.createAlreadyDone"));
          this.saveErrors(someRequest, errors);
          forward = mapping.findForward("CREATED");
        }
      } else {
        if (this.log.isDebugEnabled()) {
          this.log.debug("The user wants to fill a new skill form");
        }

        skill = (Skill)Application.getSkillFactory().makeObject();
        Collection owners = new ArrayList();
        Collection relationships = new ArrayList();
        Relationship newRelationship = new RelationshipImpl(skill, "", Application.getSkillFactory().getRootKey(), "");
        this.prepareView(skillForm, someRequest, skillService, skill, relationships, newRelationship, owners);
        this.saveToken(someRequest);
        forward = mapping.findForward("DATA_MISSING");
      }
    } finally {
      this.skillServicePool.returnObject(skillService);
    }

    if (this.log.isDebugEnabled()) {
      this.log.debug("form output = " + someForm);
    }

    if (this.log.isDebugEnabled()) {
      this.log.debug("forward=" + forward);
    }

    return forward;
  }

  public ActionForward remove(ActionMapping mapping, ActionForm someForm, HttpServletRequest someRequest, HttpServletResponse someResponse) throws Exception {
    Assert.notNullArgument(someForm, "Le formulaire transmis en devrait pas être nul. Vérifier si l'instance de form bean a pu être créée avec son constructeur par défaut");
    String skillIdParameter = someRequest.getParameter("skillToEdit");
    Assert.notNull(skillIdParameter, "Le paramètre skillToEdit ne doit pas être nul");
    int skillId = Assert.isInt(skillIdParameter, "Le paramètre skillToEdit doit être un entier");
    Assert.isValidArgument(skillId != Application.getSkillFactory().getRootId(), "Il est interdit d'effacer la compétence racine (id=" + skillId + ")");
    AbilityService abilityService = (AbilityService)this.abilityServicePool.borrowObject();

    ActionForward forward;
    try {
      abilityService.removeSkill(new SkillKey(skillId));
      forward = mapping.findForward("REMOVED");
    } finally {
      this.abilityServicePool.returnObject(abilityService);
    }

    if (this.log.isDebugEnabled()) {
      this.log.debug("forward=" + forward);
    }

    return forward;
  }

  public ActionForward update(ActionMapping mapping, ActionForm someForm, HttpServletRequest someRequest, HttpServletResponse someResponse) throws Exception {
    Assert.notNullArgument(someForm, "Le formulaire transmis en devrait pas être nul. Vérifier si l'instance de form bean a pu être créée avec son constructeur par défaut");
    Assert.isValidArgument(someForm instanceof SkillForm, "Le formulaire " + someForm + " n'est pas du type " + (class$org$orange$kicsa$presentation$view$SkillForm == null ? (class$org$orange$kicsa$presentation$view$SkillForm = class$("org.orange.kicsa.presentation.view.SkillForm")) : class$org$orange$kicsa$presentation$view$SkillForm) + " attendu");
    SkillForm skillForm = (SkillForm)someForm;
    if (this.log.isDebugEnabled()) {
      this.log.debug("form input = " + someForm);
    }

    SkillService skillService = (SkillService)this.skillServicePool.borrowObject();

    ActionForward forward;
    try {
      if (someRequest.getMethod().equals("POST")) {
        if (this.isTokenValid(someRequest)) {
          Skill skill = skillForm.getSkill();
          Iterator relationshipsIterator = skillForm.getRelationships().iterator();

          while(relationshipsIterator.hasNext()) {
            Relationship relationship = (Relationship)relationshipsIterator.next();
            if (relationship.getDestinationKey().getId() == 0) {
              relationship.setSync(true);
            }
          }

          skill = skillService.update(skill, skillForm.getRelationships(), skillForm.getOwners());
          skillForm.setSkill(skill);
          this.resetToken(someRequest);
          forward = mapping.findForward("UPDATED");
        } else {
          this.log.error("La mise à jour a déjà été faite");
          ActionErrors errors = new ActionErrors();
          errors.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionError("error.updateAlreadyDone"));
          this.saveErrors(someRequest, errors);
          forward = mapping.findForward("UPDATED");
        }
      } else {
        String skillIdString = someRequest.getParameter("skillToEdit");
        Assert.notNull(skillIdString, "L'identifiant de compétence à éditer ne peut être nul");
        int skillId = Assert.isInt(skillIdString, "L'identifiant de compétence \"" + skillIdString + "\" doit être un nombre");
        if (this.log.isDebugEnabled()) {
          this.log.debug("L'utilisateur souhaite éditer la compétence " + skillId);
        }

        SkillKey skillKey = new SkillKey(skillId);
        Skill skill = skillService.findByPrimaryKey(skillKey);
        Collection relationships = skillService.findDestinations(skill);
        Relationship newRelationship = new RelationshipImpl(skill, "", new SkillKey(0), "");
        Collection owners = new ArrayList();
        this.prepareView(skillForm, someRequest, skillService, skill, relationships, newRelationship, owners);
        this.saveToken(someRequest);
        forward = mapping.findForward("DATA_MISSING");
      }
    } finally {
      this.skillServicePool.returnObject(skillService);
    }

    if (this.log.isDebugEnabled()) {
      this.log.debug("form output = " + someForm);
    }

    if (this.log.isDebugEnabled()) {
      this.log.debug("forward=" + forward);
    }

    return forward;
  }

  private void prepareView(SkillForm someSkillForm, HttpServletRequest someRequest, SkillService someSkillService, Skill someSkill, Collection someRelationships, Relationship someNewRelationship, Collection someOwners) throws Exception {
    Assert.notNullArgument(someSkillForm, "Le formulaire ne doit pas être nul");
    Assert.notNullArgument(someRequest, "La requête ne doit pas être nulle");
    Assert.notNullArgument(someSkillService, "Le skillService ne doit pas être nul");
    Assert.notNullArgument(someRelationships, "Le someRelationships ne doit pas être nul");
    Assert.notNullArgument(someNewRelationship, "Le someNewRelationship ne doit pas être nul");
    Assert.notNullArgument(someOwners, "Le someOwners ne doit pas être nul");
    someSkillForm.setSkill(someSkill);
    someRelationships.add(someNewRelationship);
    someSkillForm.setRelationships(someRelationships);
    Map relationshipTypes = someSkillService.findRelationshipTypes();
    if (this.log.isDebugEnabled()) {
      this.log.debug("relationshipTypes=" + relationshipTypes);
    }

    someRequest.getSession().setAttribute("relationshipTypes", relationshipTypes);
    Assert.isValid(relationshipTypes.size() > 0, "La liste des types de relation possibles devrait au moins comporter un élement. Ajoutez-en via l'administration.");
    Map skillList = someSkillService.findAll();
    someRequest.getSession().setAttribute("skillList", skillList);
    Assert.isValid(skillList.size() > 0, "La liste des compétences déjà existantes devrait au moins comporter un élement (la compétence racine)");
    Iterator relationshipsIteraror = someRelationships.iterator();
    Set skillListKeys = skillList.keySet();

    while(relationshipsIteraror.hasNext()) {
      Relationship relationship = (Relationship)relationshipsIteraror.next();
      SkillKey relationshipKey = relationship.getDestinationKey();
      if (skillListKeys.contains(relationshipKey)) {
        skillList.remove(relationshipKey);
      }
    }

    EmployeeService employeeService = (EmployeeService)this.employeeServicePool.borrowObject();

    try {
      Map employeeList = employeeService.findAll();
      someRequest.getSession().setAttribute("employeeList", employeeList);
      Employee newOwner = new EmployeeImpl("", "", (Date)null, "", "", "");
      newOwner.setNew(true);
      someOwners.add(newOwner);
      someSkillForm.setOwners(someOwners);
    } finally {
      this.employeeServicePool.returnObject(employeeService);
    }

  }
}
