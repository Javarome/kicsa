package org.orange.kicsa.presentation.control.employee;

import java.util.Map;
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
import org.orange.kicsa.presentation.view.EmployeeSearchForm;
import org.orange.kicsa.service.employee.EmployeeService;
import org.orange.kicsa.service.employee.EmployeeServicePool;
import org.orange.util.Assert;

public class SearchAction extends Action {
  public static final String EMPLOYEE_LIST = "employeeList";
  public static final String EMPLOYEE_KEY = "id";
  public static final String SEARCH_SUBMISSION = "searchStart";
  static final String FOUND_FORWARD = "FOUND_ONE";
  static final String NOT_FOUND_FORWARD = "NOT_FOUND";
  static final String DATA_MISSING_FORWARD = "DATA_MISSING";
  protected Logger log = Logger.getLogger(this.getClass());
  private EmployeeServicePool employeeServicePool = EmployeeServicePool.getInstance();
  private int attribute1;

  public SearchAction() {
  }

  public ActionForward execute(ActionMapping mapping, ActionForm someForm, HttpServletRequest someRequest, HttpServletResponse someResponse) throws EmployeeNotFoundException, Exception {
    Assert.notNullArgument(someForm, "Le formulaire transmis en devrait pas être nul. Vérifier si l'instance de form bean a pu être créée avec son constructeur par défaut");
    Assert.isValidArgument(someForm instanceof EmployeeSearchForm, "Le formulaire " + someForm + " n'est pas du type " + (class$org$orange$kicsa$presentation$view$EmployeeSearchForm == null ? (class$org$orange$kicsa$presentation$view$EmployeeSearchForm = class$("org.orange.kicsa.presentation.view.EmployeeSearchForm")) : class$org$orange$kicsa$presentation$view$EmployeeSearchForm) + " attendu");
    EmployeeSearchForm searchForm = (EmployeeSearchForm)someForm;
    ActionErrors errors = new ActionErrors();
    EmployeeService employeeService = (EmployeeService)this.employeeServicePool.borrowObject();

    ActionForward forward;
    try {
      if (someRequest.getParameter("searchStart") != null) {
        int employeeId = searchForm.getEmployee().getId();
        EmployeeKey employeeKey = new EmployeeKey(employeeId);
        Employee employee = employeeService.findByPrimaryKey(employeeKey);
        someRequest.setAttribute("employee", employee);
        forward = mapping.findForward("FOUND_ONE");
      } else {
        Map employeeList = employeeService.findAll();
        someRequest.setAttribute("employeeList", employeeList);
        this.log.info("Pas de paramètre de recherche demandé: redirection vers formulaire de recherche");
        forward = mapping.findForward("DATA_MISSING");
      }
    } catch (EmployeeNotFoundException var16) {
      errors.add("employee.id", new ActionError("employee.id", "L'utilisateur n'a pas été trouvé."));
      forward = new ActionForward(mapping.getInput());
    } finally {
      this.employeeServicePool.returnObject(employeeService);
    }

    this.saveErrors(someRequest, errors);
    if (this.log.isDebugEnabled()) {
      this.log.debug("forward=" + forward);
    }

    return forward;
  }
}
