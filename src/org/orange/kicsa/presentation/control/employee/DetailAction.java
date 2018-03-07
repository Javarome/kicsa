package org.orange.kicsa.presentation.control.employee;

import java.util.Collection;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.orange.kicsa.EmployeeNotFoundException;
import org.orange.kicsa.business.employee.Employee;
import org.orange.kicsa.business.employee.EmployeeKey;
import org.orange.kicsa.presentation.view.EmployeeForm;
import org.orange.kicsa.service.ability.AbilityService;
import org.orange.kicsa.service.ability.AbilityServicePool;
import org.orange.kicsa.service.employee.EmployeeService;
import org.orange.kicsa.service.employee.EmployeeServicePool;
import org.orange.util.Assert;

public class DetailAction extends Action {
  public static final String ABILITIES = "abilities";
  public static final String LEVELS = "levels";
  public static final String EMPLOYEE = "employee";
  public static final String EMPLOYEE_KEY = "id";
  static final String FOUND_FORWARD = "FOUND";
  static final String NOT_FOUND_FORWARD = "NOT_FOUND";
  static final String DATA_MISSING_FORWARD = "DATA_MISSING";
  protected Logger log = Logger.getLogger(this.getClass());
  private EmployeeServicePool employeeServicePool = EmployeeServicePool.getInstance();
  private AbilityServicePool abilityServicePool = AbilityServicePool.getInstance();

  public DetailAction() {
  }

  public ActionForward execute(ActionMapping mapping, ActionForm someForm, HttpServletRequest someRequest, HttpServletResponse someResponse) throws EmployeeNotFoundException, Exception {
    Assert.notNullArgument(someForm, "Le formulaire transmis en devrait pas être nul. Vérifier si l'instance de form bean a pu être créée avec son constructeur par défaut");
    Assert.isValidArgument(someForm instanceof EmployeeForm, "Le formulaire " + someForm + " n'est pas du type " + (class$org$orange$kicsa$presentation$view$EmployeeForm == null ? (class$org$orange$kicsa$presentation$view$EmployeeForm = class$("org.orange.kicsa.presentation.view.EmployeeForm")) : class$org$orange$kicsa$presentation$view$EmployeeForm) + " attendu");
    EmployeeForm detailForm = (EmployeeForm)someForm;
    String employeeIdParameter = someRequest.getParameter("id");
    if (this.log.isDebugEnabled()) {
      this.log.debug("employeeIdParameter=" + employeeIdParameter);
    }

    if (employeeIdParameter == null) {
      Employee employee = (Employee)someRequest.getAttribute("employee");
      if (this.log.isDebugEnabled()) {
        this.log.debug("someRequest.getAttribute (employee)=" + employee);
      }

      if (employee == null) {
        Assert.notNull(detailForm.getEmployee(), "L'employé ne peut être nul dans le formulaire " + detailForm);
      } else {
        detailForm.setEmployee(employee);
        detailForm.setAbilities((Collection)null);
      }
    } else {
      EmployeeService employeeService = (EmployeeService)this.employeeServicePool.borrowObject();

      try {
        int employeeId = Assert.isInt(employeeIdParameter, "L'identifiant d'employé doit être un entier");
        EmployeeKey employeeKey = new EmployeeKey(employeeId);
        Employee employee = employeeService.findByPrimaryKey(employeeKey);
        detailForm.setEmployee(employee);
        detailForm.setAbilities((Collection)null);
      } catch (EmployeeNotFoundException var23) {
        ActionErrors errors = new ActionErrors();
        errors.add("employee.id", new ActionError("employee.id", "L'utilisateur n'a pas été trouvé."));
        this.saveErrors(someRequest, errors);
        new ActionForward(mapping.getInput());
      } finally {
        this.employeeServicePool.returnObject(employeeService);
      }
    }

    if (detailForm.getAbilities() == null) {
      AbilityService abilityService = (AbilityService)this.abilityServicePool.borrowObject();

      try {
        Collection abilities = abilityService.findByEmployee(detailForm.getEmployee());
        detailForm.setAbilities(abilities);
      } finally {
        this.abilityServicePool.returnObject(abilityService);
      }
    }

    ActionForward forward = mapping.findForward("FOUND");
    if (this.log.isDebugEnabled()) {
      this.log.debug("forward=" + forward);
    }

    return forward;
  }
}
