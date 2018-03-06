package org.orange.kicsa.presentation.view;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.orange.kicsa.business.ability.Ability;
import org.orange.kicsa.business.employee.Employee;
import org.orange.kicsa.business.employee.EmployeeImpl;

public class EmployeeForm extends ActionForm implements Serializable {
  private static Logger log;
  private Collection abilities;
  private Collection rights;
  private Employee employee = new EmployeeImpl("", "", new Date(), "", "", "");

  public EmployeeForm() {
  }

  public Employee getEmployee() {
    return this.employee;
  }

  public void setEmployee(Employee employee) {
    this.employee = employee;
  }

  public ActionErrors validate(ActionMapping mapping, HttpServletRequest servletRequest) {
    ActionErrors errors = super.validate(mapping, servletRequest);
    if (errors == null) {
      errors = new ActionErrors();
    }

    if (this.abilities != null) {
      Iterator abilitiesIterator = this.getAbilities().iterator();

      while(abilitiesIterator.hasNext()) {
        Ability ability = (Ability)abilitiesIterator.next();
        if (ability.getSkillKey().getId() != 0 && ability.getLevel() == 0) {
          errors.add("employee.abilities.level", new ActionError("error.ability.level.required"));
        }
      }
    }

    return errors;
  }

  public Collection getAbilities() {
    return this.abilities;
  }

  public void setAbilities(Collection abilities) {
    this.abilities = abilities;
  }

  public Collection getRights() {
    if (log.isDebugEnabled()) {
      log.debug("getRights() =" + this.rights);
    }

    return this.rights;
  }

  public void setRights(Collection rights) {
    if (log.isDebugEnabled()) {
      log.debug("setRights (" + rights + ")");
    }

    this.rights = rights;
  }

  public String toString() {
    return "EmployeeForm { employee=" + this.getEmployee() + ", abilities=" + this.getAbilities() + ", rights=" + this.getRights() + " }";
  }

  public void reset(ActionMapping mapping, HttpServletRequest servletRequest) {
    super.reset(mapping, servletRequest);
    if (this.abilities != null) {
      Iterator abilitiesIterator = this.abilities.iterator();

      while(abilitiesIterator.hasNext()) {
        Ability ability = (Ability)abilitiesIterator.next();
        if (ability.getSkillKey().getId() == 0) {
          ability.setNew(true);
        } else {
          ability.setDirty(true);
        }
      }
    }

  }

  static {
    log = Logger.getLogger(class$org$orange$kicsa$presentation$view$EmployeeForm == null ? (class$org$orange$kicsa$presentation$view$EmployeeForm = class$("org.orange.kicsa.presentation.view.EmployeeForm")) : class$org$orange$kicsa$presentation$view$EmployeeForm);
  }
}
