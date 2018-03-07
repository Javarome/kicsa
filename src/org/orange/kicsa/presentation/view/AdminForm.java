package org.orange.kicsa.presentation.view;

import java.util.Iterator;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.orange.kicsa.business.skill.RelationshipType;
import org.orange.kicsa.business.skill.Skill;

public class AdminForm extends ActionForm {
  private Map relationshipTypes;
  private Skill rootSkill;

  public AdminForm() {
  }

  public Map getRelationshipTypes() {
    return this.relationshipTypes;
  }

  public void setRelationshipTypes(Map relationshipTypes) {
    this.relationshipTypes = relationshipTypes;
  }

  public Skill getRootSkill() {
    return this.rootSkill;
  }

  public void setRootSkill(Skill rootSkill) {
    this.rootSkill = rootSkill;
  }

  public ActionErrors validate(ActionMapping mapping, HttpServletRequest someRequest) {
    ActionErrors errors = super.validate(mapping, someRequest);
    if (someRequest.getMethod().equals("POST")) {
      if (errors == null) {
        errors = new ActionErrors();
      }

      if (this.getRelationshipTypes() == null || this.getRelationshipTypes().keySet().size() <= 0) {
        errors.add("relationshipTypes", new ActionError("error.relationshipTypes.required"));
      }
    }

    return errors;
  }

  public void reset(ActionMapping someMapping, HttpServletRequest someServletRequest) {
    super.reset(someMapping, someServletRequest);
    if (this.relationshipTypes != null) {
      Iterator relationshipTypesIterator = this.relationshipTypes.values().iterator();

      while(relationshipTypesIterator.hasNext()) {
        RelationshipType relationshipType = (RelationshipType)relationshipTypesIterator.next();
        relationshipType.setParent(false);
        if (relationshipType.getName().length() == 0) {
          relationshipType.setNew(true);
        } else {
          relationshipType.setDirty(true);
        }
      }
    }
  }
}
