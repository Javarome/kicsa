package org.orange.kicsa.presentation.control.skill;

import java.util.SortedMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.orange.kicsa.SkillNotFoundException;
import org.orange.kicsa.business.skill.Skill;
import org.orange.kicsa.business.skill.SkillKey;
import org.orange.kicsa.presentation.view.SkillForm;
import org.orange.kicsa.service.ability.AbilityService;
import org.orange.kicsa.service.ability.AbilityServicePool;
import org.orange.kicsa.service.skill.SkillService;
import org.orange.kicsa.service.skill.SkillServicePool;
import org.orange.util.Assert;

public class DetailAction extends Action {
  public static final String EMPLOYEE_VIEWER_ROLE = "EmployeeViewer";
  public static final String FOUND_FORWARD = "FOUND";
  public static final String SKILL_ID = "detailledSkill";
  public static final String SKILL = "skill";
  public static final String ABILITIES = "abilities";
  public static final String PARENTS = "parents";
  protected Logger log = Logger.getLogger(this.getClass());
  private SkillServicePool skillServicePool = SkillServicePool.getInstance();
  private AbilityServicePool abilityServicePool = AbilityServicePool.getInstance();

  public DetailAction() {
  }

  public ActionForward execute(ActionMapping mapping, ActionForm someForm, HttpServletRequest someRequest, HttpServletResponse someResponse) throws SkillNotFoundException, Exception {
    Assert.notNullArgument(someForm, "The form must not be null. Check if a form was assigned to this action and if it could be created using the default constructor");
    Assert.isValidArgument(someForm instanceof SkillForm, "Expected that " + someForm + " to be of type " + (class$org$orange$kicsa$presentation$view$SkillForm == null ? (class$org$orange$kicsa$presentation$view$SkillForm = class$("org.orange.kicsa.presentation.view.SkillForm")) : class$org$orange$kicsa$presentation$view$SkillForm));
    SkillForm skillForm = (SkillForm)someForm;
    if (this.log.isDebugEnabled()) {
      this.log.debug("form input = " + someForm);
    }

    SkillService skillService = (SkillService)this.skillServicePool.borrowObject();

    try {
      String skillIdParameter = someRequest.getParameter("detailledSkill");
      if (this.log.isDebugEnabled()) {
        this.log.debug("detailledSkill = \"" + skillIdParameter + "\"");
      }

      Skill skill;
      if (skillIdParameter != null) {
        int skillId = Assert.isInt(skillIdParameter, "Integer value expected for parameter detailledSkill");
        SkillKey skillKey = new SkillKey(skillId);
        skill = skillService.findByPrimaryKey(skillKey);
        skillForm.setSkill(skill);
      } else {
        skill = skillForm.getSkill();
        if (skill != null) {
          skillForm.setSkill(skill);
        }
      }

      AbilityService abilityService = (AbilityService)this.abilityServicePool.borrowObject();

      try {
        SortedMap abilities;
        if (someRequest.isUserInRole("EmployeeViewer")) {
          abilities = abilityService.findAbleEmployeesBySkill(skill);
        } else {
          abilities = abilityService.findAbleEntitiesBySkill(skill);
        }

        skillForm.setAbilities(abilities);
      } finally {
        this.abilityServicePool.returnObject(abilityService);
      }
    } finally {
      this.skillServicePool.returnObject(skillService);
    }

    if (this.log.isDebugEnabled()) {
      this.log.debug("form output = " + skillForm);
    }

    return mapping.findForward("FOUND");
  }
}
