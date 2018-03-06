package org.orange.kicsa.presentation.view;

import java.io.Serializable;
import java.util.Collection;
import java.util.SortedMap;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.orange.kicsa.business.skill.Skill;

public class SkillSearchForm extends ActionForm implements Serializable {
  private static Logger log;
  private int ownerId;
  private Collection owners;
  private Collection relationships;
  private Skill skill;
  private SortedMap abilities;

  public SkillSearchForm() {
  }

  public Skill getSkill() {
    return this.skill;
  }

  public void setSkill(Skill someSkill) {
    this.skill = someSkill;
  }

  public ActionErrors validate(ActionMapping mapping, HttpServletRequest servletRequest) {
    ActionErrors errors = super.validate(mapping, servletRequest);
    return errors;
  }

  public Collection getOwners() {
    return this.owners;
  }

  public void setOwners(Collection someOwners) {
    this.owners = someOwners;
  }

  public Collection getRelationships() {
    if (log.isDebugEnabled()) {
      log.debug("getRelationships() = " + this.relationships);
    }

    return this.relationships;
  }

  public void setRelationships(Collection someRelationships) {
    if (log.isDebugEnabled()) {
      log.debug("setRelationships (" + someRelationships + ")");
    }

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

  static {
    log = Logger.getLogger(class$org$orange$kicsa$presentation$view$SkillForm == null ? (class$org$orange$kicsa$presentation$view$SkillForm = class$("org.orange.kicsa.presentation.view.SkillForm")) : class$org$orange$kicsa$presentation$view$SkillForm);
  }
}
