package org.orange.kicsa.presentation.control.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class LoginAction extends Action {
  public static final String J2EE_LOGIN_FORWARD = "J2EE_LOGIN";
  private Logger log = Logger.getLogger(this.getClass());

  public LoginAction() {
  }

  public ActionForward execute(ActionMapping someMapping, ActionForm someForm, HttpServletRequest someRequest, HttpServletResponse servletResponse) throws Exception {
    ActionForward forward = someMapping.findForward("J2EE_LOGIN");
    return forward;
  }
}
