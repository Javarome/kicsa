package org.orange.kicsa.presentation.control.employee;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
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
import org.orange.kicsa.business.ability.Ability;
import org.orange.kicsa.business.ability.EmployeeAbilityImpl;
import org.orange.kicsa.business.employee.Employee;
import org.orange.kicsa.business.employee.EmployeeImpl;
import org.orange.kicsa.business.employee.EmployeeKey;
import org.orange.kicsa.business.skill.SkillKey;
import org.orange.kicsa.presentation.view.EmployeeForm;
import org.orange.kicsa.service.ability.AbilityService;
import org.orange.kicsa.service.ability.AbilityServicePool;
import org.orange.kicsa.service.employee.EmployeeService;
import org.orange.kicsa.service.employee.EmployeeServicePool;
import org.orange.kicsa.service.skill.SkillService;
import org.orange.kicsa.service.skill.SkillServicePool;
import org.orange.util.Assert;

public class EditAction extends DispatchAction {
  public static final String UPDATE_START = "updateStart";
  public static final String FAILURE_FORWARD = "FAILURE";
  public static final String DATA_MISSING_FORWARD = "DATA_MISSING";
  public static final String UPDATED_FORWARD = "UPDATED";
  public static final String CREATED_FORWARD = "CREATED";
  public static final String REMOVED_FORWARD = "REMOVED";
  public static final String LEVELS = "levels";
  public static final String SKILL_LEVELS = "skillLevels";
  public static final String SKILL_LIST = "skillList";
  public static final String EMPLOYEE_LIST = "employeeList";
  public static final String RELATIONSHIP_LIST = "relationshipList";
  public static final String EMPLOYEE_TO_EDIT = "employeeToEdit";
  protected Logger log = Logger.getLogger(this.getClass());
  private AbilityServicePool abilityServicePool = AbilityServicePool.getInstance();
  private EmployeeServicePool employeeServicePool = EmployeeServicePool.getInstance();
  private SkillServicePool skillServicePool = SkillServicePool.getInstance();

  public EditAction() {
  }

  public ActionForward create(ActionMapping mapping, ActionForm someForm, HttpServletRequest someRequest, HttpServletResponse someResponse) throws Exception {
    Assert.notNullArgument(someForm, "Le formulaire transmis en devrait pas être nul. Vérifier si l'instance de form bean a pu être créée avec son constructeur par défaut");
    if (this.log.isDebugEnabled()) {
      this.log.debug("form input = " + someForm);
    }

    Assert.isValidArgument(someForm instanceof EmployeeForm, "Le formulaire " + someForm + " n'est pas du type " + (class$org$orange$kicsa$presentation$view$EmployeeForm == null ? (class$org$orange$kicsa$presentation$view$EmployeeForm = class$("org.orange.kicsa.presentation.view.EmployeeForm")) : class$org$orange$kicsa$presentation$view$EmployeeForm) + " attendu");
    EmployeeForm createForm = (EmployeeForm)someForm;
    AbilityService abilityService = (AbilityService)this.abilityServicePool.borrowObject();

    ActionForward forward;
    try {
      if (someRequest.getMethod().equals("POST")) {
        if (this.isTokenValid(someRequest)) {
          Employee employee = createForm.getEmployee();
          employee.setNew(true);
          abilityService.createEmployee(employee, createForm.getAbilities(), createForm.getRights());
          forward = mapping.findForward("CREATED");
        } else {
          this.log.error("La création a déjà été faite");
          ActionErrors errors = new ActionErrors();
          errors.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionError("error.createAlreadyDone"));
          this.saveErrors(someRequest, errors);
          forward = mapping.findForward("CREATED");
        }
      } else {
        if (this.log.isDebugEnabled()) {
          this.log.debug("L'utilisateur souhaite créer une nouvelle personne");
        }

        Employee employee = new EmployeeImpl("", "", new Date(), "", "", "");
        employee.setNew(true);
        Collection abilities = new ArrayList();
        this.prepareView(createForm, someRequest, employee, abilities);
        this.saveToken(someRequest);
        forward = mapping.findForward("DATA_MISSING");
      }
    } finally {
      this.abilityServicePool.returnObject(abilityService);
    }

    if (this.log.isDebugEnabled()) {
      this.log.debug("form output = " + someForm);
    }

    if (this.log.isDebugEnabled()) {
      this.log.debug("forward=" + forward);
    }

    return forward;
  }

  public ActionForward update(ActionMapping mapping, ActionForm someForm, HttpServletRequest someRequest, HttpServletResponse someResponse) throws Exception {
    Assert.notNullArgument(someForm, "Le formulaire transmis en devrait pas être nul. Vérifier si l'instance de form bean a pu être créée avec son constructeur par défaut");
    Assert.isValidArgument(someForm instanceof EmployeeForm, "Le formulaire " + someForm + " n'est pas du type " + (class$org$orange$kicsa$presentation$view$EmployeeForm == null ? (class$org$orange$kicsa$presentation$view$EmployeeForm = class$("org.orange.kicsa.presentation.view.EmployeeForm")) : class$org$orange$kicsa$presentation$view$EmployeeForm) + " attendu");
    EmployeeForm updateForm = (EmployeeForm)someForm;
    if (this.log.isDebugEnabled()) {
      this.log.debug("form input = " + someForm);
    }

    AbilityService abilityService = (AbilityService)this.abilityServicePool.borrowObject();

    ActionForward forward;
    try {
      Employee employee;
      Collection abilities;
      if (someRequest.getParameter("updateStart") != null) {
        String employeeIdParameter = someRequest.getParameter("employeeToEdit");
        Assert.notNullArgument(employeeIdParameter, "L'identifiant d'employé doit être fourni");
        if (this.log.isDebugEnabled()) {
          this.log.debug("L'utilisateur souhaite éditer la personne " + employeeIdParameter);
        }

        int employeeId = Assert.isInt(employeeIdParameter, "L'identifiant d'employé doit être un entier");
        EmployeeKey employeeKey = new EmployeeKey(employeeId);
        EmployeeService employeeService = (EmployeeService)this.employeeServicePool.borrowObject();

        try {
          employee = employeeService.findByPrimaryKey(employeeKey);
          employee.setDirty(true);
          abilities = abilityService.findByEmployee(employee);
          this.prepareView(updateForm, someRequest, employee, abilities);
          this.saveToken(someRequest);
          forward = mapping.findForward("DATA_MISSING");
        } finally {
          this.employeeServicePool.returnObject(employeeService);
        }
      } else if (this.isTokenValid(someRequest)) {
        employee = updateForm.getEmployee();
        if (this.log.isDebugEnabled()) {
          this.log.debug("L'utilisateur envoie une mise à jour de la personne " + employee);
        }

        abilities = updateForm.getAbilities();
        Iterator abilitiesIterator = abilities.iterator();

        while(abilitiesIterator.hasNext()) {
          Ability ability = (Ability)abilitiesIterator.next();
          SkillKey abilitySkillKey = ability.getSkillKey();
          if (abilitySkillKey.getId() == 0) {
            ability.setSync(true);
          }
        }

        abilityService.updateEmployee(employee, abilities, updateForm.getRights());
        this.resetToken(someRequest);
        forward = mapping.findForward("UPDATED");
      } else {
        this.log.error("La mise à jour a déjà été faite");
        ActionErrors errors = new ActionErrors();
        errors.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionError("error.updateAlreadyDone"));
        this.saveErrors(someRequest, errors);
        forward = mapping.findForward("UPDATED");
      }
    } finally {
      this.abilityServicePool.returnObject(abilityService);
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
    String skillIdParameter = someRequest.getParameter("employeeToEdit");
    Assert.notNull(skillIdParameter, "Le paramètre employeeToEdit ne doit pas être nul");
    int employeeId = Assert.isInt(skillIdParameter, "Le paramètre employeeToEdit doit être un entier");
    AbilityService abilityService = (AbilityService)this.abilityServicePool.borrowObject();

    ActionForward forward;
    try {
      abilityService.removeEmployee(new EmployeeKey(employeeId));
      forward = mapping.findForward("REMOVED");
    } finally {
      this.abilityServicePool.returnObject(abilityService);
    }

    if (this.log.isDebugEnabled()) {
      this.log.debug("forward=" + forward);
    }

    return forward;
  }

  private void prepareView(EmployeeForm someEmployeeForm, HttpServletRequest someRequest, Employee someEmployee, Collection someAbilities) throws Exception {
    someEmployeeForm.setEmployee(someEmployee);
    SkillService skillService = (SkillService)this.skillServicePool.borrowObject();

    try {
      Map skillList = skillService.findAll();
      Iterator abilitiesIteraror = someAbilities.iterator();
      Set skillListKeys = skillList.keySet();

      while(abilitiesIteraror.hasNext()) {
        Ability ability = (Ability)abilitiesIteraror.next();
        SkillKey abilityKey = ability.getSkillKey();
        if (skillListKeys.contains(abilityKey)) {
          skillList.remove(abilityKey);
        }
      }

      someRequest.getSession().setAttribute("skillList", skillList);
    } finally {
      this.skillServicePool.returnObject(skillService);
    }

    EmployeeAbilityImpl var15 = new EmployeeAbilityImpl(someEmployee.getKey(), someEmployee.getFirstName(), someEmployee.getLastName(), new SkillKey(0), "", 0);
    someAbilities.add(var15);
    someEmployeeForm.setAbilities(someAbilities);
    someRequest.getSession().setAttribute("skillLevels", Application.getSkillFactory().getRoot().getLevels());
  }
}
