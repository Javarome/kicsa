package org.orange.kicsa.presentation.control.skill;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.orange.kicsa.SkillNotFoundException;
import org.orange.kicsa.service.skill.SkillServicePool;
import org.orange.util.NotImplementedException;

public class SearchAction extends Action {
  public static final String EMPLOYEE_LIST = "skillList";
  public static final String EMPLOYEE_KEY = "id";
  public static final String SEARCH_SUBMISSION = "searchStart";
  static final String FOUND_FORWARD = "FOUND_ONE";
  static final String NOT_FOUND_FORWARD = "NOT_FOUND";
  static final String DATA_MISSING_FORWARD = "DATA_MISSING";
  protected Logger log = Logger.getLogger(this.getClass());
  private SkillServicePool skillServicePool = SkillServicePool.getInstance();

  public SearchAction() {
  }

  public ActionForward execute(ActionMapping mapping, ActionForm someForm, HttpServletRequest someRequest, HttpServletResponse someResponse) throws SkillNotFoundException, Exception {
    throw new NotImplementedException();
  }
}
