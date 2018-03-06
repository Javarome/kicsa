package org.orange.kicsa.business.admin;

import java.io.Serializable;

public class AdminInfo implements Serializable {
  private String[] relationShipTypes;
  private String newRelationshipType;
  private String parentRelationshipType;
  private String[] levelDescriptions;
  private String[] levelNames;
  private int[] levelLevels;

  public AdminInfo() {
  }

  public String getRelationShipTypes(int index) {
    return this.relationShipTypes[index];
  }

  public void setRelationShipTypes(int index, String relationShipTypes) {
    this.relationShipTypes[index] = relationShipTypes;
  }

  public String[] getRelationShipTypes() {
    return this.relationShipTypes;
  }

  public void setRelationShipTypes(String[] relationShipTypes) {
    this.relationShipTypes = relationShipTypes;
  }

  public String getNewRelationshipType() {
    return this.newRelationshipType;
  }

  public void setNewRelationshipType(String newRelationshipType) {
    this.newRelationshipType = newRelationshipType;
  }

  public String getParentRelationshipType() {
    return this.parentRelationshipType;
  }

  public void setParentRelationshipType(String parentRelationshipType) {
    this.parentRelationshipType = parentRelationshipType;
  }

  public String getLevelDescriptions(int index) {
    return this.levelDescriptions[index];
  }

  public void setLevelDescriptions(int index, String levelDescriptions) {
    this.levelDescriptions[index] = levelDescriptions;
  }

  public String[] getLevelDescriptions() {
    return this.levelDescriptions;
  }

  public void setLevelDescriptions(String[] levelDescriptions) {
    this.levelDescriptions = levelDescriptions;
  }

  public String getLevelNames(int index) {
    return this.levelNames[index];
  }

  public void setLevelNames(int index, String levelNames) {
    this.levelNames[index] = levelNames;
  }

  public String[] getLevelNames() {
    return this.levelNames;
  }

  public void setLevelNames(String[] levelNames) {
    this.levelNames = levelNames;
  }

  public int getLevelLevels(int index) {
    return this.levelLevels[index];
  }

  public void setLevelLevels(int index, int levelLevels) {
    this.levelLevels[index] = levelLevels;
  }

  public int[] getLevelLevels() {
    return this.levelLevels;
  }

  public void setLevelLevels(int[] levelLevels) {
    this.levelLevels = levelLevels;
  }
}
