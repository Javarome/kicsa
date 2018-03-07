package org.orange.kicsa.presentation.control.skill;

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
import org.orange.kicsa.Application;
import org.orange.kicsa.SkillNotFoundException;
import org.orange.kicsa.business.skill.Skill;
import org.orange.kicsa.business.skill.SkillKey;
import org.orange.kicsa.presentation.view.SkillForm;
import org.orange.kicsa.service.skill.SkillService;
import org.orange.kicsa.service.skill.SkillServicePool;
import org.orange.util.Assert;

public class TreeAction extends Action {
  public static final String FOUND_CHILDS_FORWARD = "FOUND_CHILDS";
  public static final String NO_CHILDS_FORWARD = "NO_CHILDS";
  public static final String CURRENT_TREENODE_ID = "id";
  public static final String PARENT = "parent";
  public static final String CHILDS = "childs";
  protected Logger log = Logger.getLogger(this.getClass());
  private SkillServicePool skillServicePool = SkillServicePool.getInstance();

  public TreeAction() {
  }

  public ActionForward execute(ActionMapping someMapping, ActionForm someForm, HttpServletRequest someRequest, HttpServletResponse someResponse) throws SkillNotFoundException, Exception {
    String currentTreeNodeIdParameter = someRequest.getParameter("id");
    if (this.log.isDebugEnabled()) {
      this.log.debug("id = " + currentTreeNodeIdParameter);
    }

    if (currentTreeNodeIdParameter == null) {
      currentTreeNodeIdParameter = Application.getSkillFactory().getRootKey().toString();
    }

    int currentTreeNodeId = Assert.isInt(currentTreeNodeIdParameter, "The current skill id must be an int");
    SkillService skillService = (SkillService)this.skillServicePool.borrowObject();

    ActionForward forward;
    try {
      Skill parentSkill;
      try {
        parentSkill = skillService.findByPrimaryKey(new SkillKey(currentTreeNodeId));
      } catch (SkillNotFoundException var16) {
        ActionErrors errors = new ActionErrors();
        errors.add("id", new ActionError("id", var16.getMessage()));
        this.saveErrors(someRequest, errors);
        new ActionForward(someMapping.getInput());
        parentSkill = Application.getSkillFactory().getRoot();
      }

      Collection childs = skillService.findChilds(parentSkill);
      if (childs.size() > 0) {
        someRequest.setAttribute("parent", parentSkill);
        someRequest.setAttribute("childs", childs);
        if (this.log.isDebugEnabled()) {
          this.log.debug(childs.size() + " placés dans la requête");
        }

        Collection parents = skillService.findParents(parentSkill);
        someRequest.setAttribute("parents", parents);
        forward = someMapping.findForward("FOUND_CHILDS");
      } else {
        Assert.notNullArgument(someForm, "Le formulaire transmis en devrait pas être nul. Vérifier si l'instance de form bean a pu être créée avec son constructeur par défaut");
        Assert.isValidArgument(someForm instanceof SkillForm, "Le formulaire " + someForm + " n'est pas du type " + (class$org$orange$kicsa$presentation$view$SkillForm == null ? (class$org$orange$kicsa$presentation$view$SkillForm = class$("org.orange.kicsa.presentation.view.SkillForm")) : class$org$orange$kicsa$presentation$view$SkillForm) + " attendu");
        SkillForm skillForm = (SkillForm)someForm;
        skillForm.setSkill(parentSkill);
        if (this.log.isDebugEnabled()) {
          this.log.debug("No child skills. Populated " + skillForm);
        }

        forward = someMapping.findForward("NO_CHILDS");
      }
    } finally {
      this.skillServicePool.returnObject(skillService);
    }

    if (this.log.isDebugEnabled()) {
      this.log.debug("forward=" + forward);
    }

    return forward;
  }
}
