package org.orange.kicsa.business.ability;

import java.io.Serializable;
import org.orange.kicsa.business.Editable.Status;
import org.orange.kicsa.business.skill.SkillKey;

public class EntityAbilityImpl extends AbstractAbility implements EntityAbility, Serializable {
  private String name;
  private String URI;

  public EntityAbilityImpl(String someEntityName, String someEntityURI, SkillKey someSkillKey, String someSkillName, int someLevel, Status someStatus) {
    super(someSkillKey, someSkillName, someLevel, someStatus);
    this.name = someEntityName;
    this.URI = someEntityURI;
  }

  public EntityAbilityImpl(String someEntityName, String someEntityURI, SkillKey someSkillKey, String someSkillName, int someLevel) {
    this(someEntityName, someEntityURI, someSkillKey, someSkillName, someLevel, Status.NEW);
  }

  public String toString() {
    return "EntityAbilityImpl { " + super.toString() + ", " + this.name + ", " + this.URI + " }";
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getURI() {
    return this.URI;
  }

  public void setURI(String URI) {
    this.URI = URI;
  }
}
