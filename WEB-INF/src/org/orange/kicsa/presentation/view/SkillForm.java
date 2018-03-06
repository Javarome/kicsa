package org.orange.kicsa.presentation.view;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.SortedMap;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.orange.kicsa.business.skill.Relationship;
import org.orange.kicsa.business.skill.Skill;

public class SkillForm extends ActionForm implements Serializable {
  private static Logger log;
  private int ownerId;
  private Collection owners;
  private Collection relationships;
  private Skill skill;
  private SortedMap abilities;

  public SkillForm() {
  }

  public Skill getSkill() {
    return this.skill;
  }

  public void setSkill(Skill someSkill) {
    this.skill = someSkill;
  }

  public ActionErrors validate(ActionMapping mapping, HttpServletRequest servletRequest) {
    ActionErrors errors = super.validate(mapping, servletRequest);
    if (errors == null) {
      errors = new ActionErrors();
    }

    if (this.relationships != null) {
      Iterator relationshipsIterator = this.getRelationships().iterator();

      while(relationshipsIterator.hasNext()) {
        Relationship relationship = (Relationship)relationshipsIterator.next();
        if (relationship.getDestinationKey().getId() != 0 && relationship.getType().length() == 0) {
          errors.add("skill.relationship.type", new ActionError("error.relationshipType.required"));
        }
      }
    }

    return errors;
  }

  public Collection getOwners() {
    return this.owners;
  }

  public void setOwners(Collection someOwners) {
    this.owners = someOwners;
  }

  public Collection getRelationships() {
    return this.relationships;
  }

  public void setRelationships(Collection someRelationships) {
    this.relationships = someRelationships;
  }

  public String toString() {
    return "SkillForm {skill=" + this.getSkill() + ", relationships=" + this.getRelationships() + ", owners=" + this.getOwners() + ", abilities=" + this.abilities + "}";
  }

  public SortedMap getAbilities() {
    return this.abilities;
  }

  public void setAbilities(SortedMap abilities) {
    this.abilities = abilities;
  }

  public void reset(ActionMapping someMapping, HttpServletRequest someServletRequest) {
    super.reset(someMapping, someServletRequest);
    if (this.relationships != null) {
      Iterator relationshipsIterator = this.relationships.iterator();

      while(relationshipsIterator.hasNext()) {
        Relationship relationship = (Relationship)relationshipsIterator.next();
        if (relationship.getDestinationName().length() == 0) {
          relationship.setNew(true);
        } else {
          relationship.setDirty(true);
        }
      }
    }

  }

  static {
    log = Logger.getLogger(class$org$orange$kicsa$presentation$view$SkillForm == null ? (class$org$orange$kicsa$presentation$view$SkillForm = class$("org.orange.kicsa.presentation.view.SkillForm")) : class$org$orange$kicsa$presentation$view$SkillForm);
  }
}
