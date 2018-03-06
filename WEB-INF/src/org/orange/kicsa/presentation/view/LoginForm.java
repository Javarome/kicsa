package org.orange.kicsa.presentation.view;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class LoginForm extends ActionForm {
  private String username;
  private String password;

  public LoginForm() {
  }

  public String getUsername() {
    return this.username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return this.password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public ActionErrors validate(ActionMapping mapping, HttpServletRequest someRequest) {
    ActionErrors errors = super.validate(mapping, someRequest);
    return errors;
  }

  public void reset(ActionMapping someMapping, HttpServletRequest someServletRequest) {
    super.reset(someMapping, someServletRequest);
  }
}
