package org.orange.kicsa.presentation.view;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.orange.kicsa.business.employee.Employee;
import org.orange.kicsa.business.employee.EmployeeImpl;

public class EmployeeSearchForm extends ActionForm implements Serializable {
  private Employee employee = new EmployeeImpl("", "", new Date(), "", "", "");
  private Collection abilities;
  private static Logger log;

  public EmployeeSearchForm() {
  }

  public ActionErrors validate(ActionMapping mapping, HttpServletRequest servletRequest) {
    ActionErrors errors = super.validate(mapping, servletRequest);
    return errors;
  }

  public String toString() {
    return "EmployeeSearchForm { employee=" + this.getEmployee() + ", abilities=" + this.getAbilities() + " }";
  }

  public Employee getEmployee() {
    return this.employee;
  }

  public void setEmployee(Employee employee) {
    this.employee = employee;
  }

  public Collection getAbilities() {
    if (log.isDebugEnabled()) {
      log.debug("getOwners()=" + this.abilities);
    }

    return this.abilities;
  }

  public void setAbilities(Collection abilities) {
    if (log.isDebugEnabled()) {
      log.debug("setOwners (" + abilities + ")");
    }

    this.abilities = abilities;
  }

  static {
    log = Logger.getLogger(class$org$orange$kicsa$presentation$view$EmployeeSearchForm == null ? (class$org$orange$kicsa$presentation$view$EmployeeSearchForm = class$("org.orange.kicsa.presentation.view.EmployeeSearchForm")) : class$org$orange$kicsa$presentation$view$EmployeeSearchForm);
  }
}
