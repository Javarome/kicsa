package org.orange.kicsa.presentation.control.admin;

import java.util.Iterator;
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
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.orange.kicsa.Application;
import org.orange.kicsa.business.skill.RelationshipType;
import org.orange.kicsa.business.skill.RelationshipTypeImpl;
import org.orange.kicsa.business.skill.Skill;
import org.orange.kicsa.presentation.view.AdminForm;
import org.orange.kicsa.service.admin.AdminService;
import org.orange.kicsa.service.admin.AdminServicePool;
import org.orange.kicsa.service.skill.SkillService;
import org.orange.kicsa.service.skill.SkillServicePool;
import org.orange.util.Assert;

public class EditAction extends Action {
  public static final String SKILL_LIST = "skillList";
  private SkillServicePool skillServicePool = SkillServicePool.getInstance();
  private AdminServicePool adminServicePool = AdminServicePool.getInstance();
  public static final String DATA_MISSING_FORWARD = "DATA_MISSING";
  public static final String UPDATED_FORWARD = "UPDATED";
  private Logger log = Logger.getLogger(this.getClass());

  public EditAction() {
  }

  public ActionForward execute(ActionMapping someMapping, ActionForm someForm, HttpServletRequest someRequest, HttpServletResponse servletResponse) throws Exception {
    Assert.notNullArgument(someForm, "The form cannot be null. Check if a form has been associated to this action and that it could be created by a default constructor");
    Assert.isValidArgument(someForm instanceof AdminForm, someForm + " is not an instannce of " + (class$org$orange$kicsa$presentation$view$AdminForm == null ? (class$org$orange$kicsa$presentation$view$AdminForm = class$("org.orange.kicsa.presentation.view.AdminForm")) : class$org$orange$kicsa$presentation$view$AdminForm));
    AdminForm adminForm = (AdminForm)someForm;
    ActionForward forward;
    if (someRequest.getMethod().equals("GET")) {
      if (this.log.isDebugEnabled()) {
        this.log.debug("The user wants to edit administrative parameters");
      }

      Skill rootSkill = Application.getSkillFactory().getRoot();
      adminForm.setRootSkill(rootSkill);
      SkillService skillService = (SkillService)this.skillServicePool.borrowObject();

      Map skillList;
      try {
        skillList = skillService.findRelationshipTypes();
        skillList.put("", new RelationshipTypeImpl("", false));
        adminForm.setRelationshipTypes(skillList);
      } finally {
        this.skillServicePool.returnObject(skillService);
      }

      skillList = skillService.findAll();
      someRequest.setAttribute("skillList", skillList);
      someRequest.getSession().setAttribute("skillList", skillList);
      Assert.isValid(skillList.size() > 0, "Skill list must have one element at list (the root skill)");
      someRequest.setAttribute("skillList", skillList);
      this.saveToken(someRequest);
      forward = someMapping.findForward("DATA_MISSING");
    } else if (this.isTokenValid(someRequest)) {
      Iterator relationshipTypesIterator = adminForm.getRelationshipTypes().values().iterator();

      while(relationshipTypesIterator.hasNext()) {
        RelationshipType relationshipType = (RelationshipType)relationshipTypesIterator.next();
        if (relationshipType.getName().length() == 0) {
          relationshipType.setSync(true);
        }
      }

      AdminService adminService = (AdminService)this.adminServicePool.borrowObject();

      try {
        adminService.update(adminForm.getRootSkill(), adminForm.getRelationshipTypes());
      } finally {
        this.adminServicePool.returnObject(adminService);
      }

      this.resetToken(someRequest);
      this.saveToken(someRequest);
      ActionMessages messages = new ActionMessages();
      messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("message.updateDone"));
      this.saveMessages(someRequest, messages);
      forward = someMapping.findForward("UPDATED");
    } else {
      this.log.error("Admin update already done");
      ActionErrors errors = new ActionErrors();
      errors.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionError("error.updateAlreadyDone"));
      this.saveErrors(someRequest, errors);
      forward = someMapping.findForward("UPDATED");
    }

    return forward;
  }
}
