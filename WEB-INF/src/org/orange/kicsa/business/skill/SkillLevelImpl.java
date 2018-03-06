package org.orange.kicsa.business.skill;

import java.io.Serializable;

public class SkillLevelImpl implements SkillLevel, Serializable {
  private int level;
  private String name;
  private String description;

  public SkillLevelImpl(int someLevel, String someName, String someDescription) {
    this.setLevel(someLevel);
    this.setName(someName);
    this.setDescription(someDescription);
  }

  public int getLevel() {
    return this.level;
  }

  public void setLevel(int someLevel) {
    this.level = someLevel;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return this.description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String toString() {
    return "SkillLevel {" + this.level + ", " + this.name + "}";
  }
}
