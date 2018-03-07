package org.orange.kicsa.presentation.view;

import javax.servlet.jsp.tagext.TagData;
import javax.servlet.jsp.tagext.TagExtraInfo;
import javax.servlet.jsp.tagext.VariableInfo;

public class RelationshipBodyTagExtraInfo extends TagExtraInfo {
  static final String DIRECTION = "direction";
  static final String TO_DIRECTION = "to";
  static final String FROM_DIRECTION = "from";
  static final String TYPE = "type";
  static final String PARENTS_TYPE = "parents";
  static final String CHILDS_TYPE = "childs";
  static final String RELATIONSHIP_ID = "relationshipId";

  public RelationshipBodyTagExtraInfo() {
  }

  public VariableInfo[] getVariableInfo(TagData data) {
    VariableInfo relationshipVariable = new VariableInfo(data.getAttributeString("relationshipId"), (class$org$orange$kicsa$business$skill$Skill == null ? (class$org$orange$kicsa$business$skill$Skill = class$("org.orange.kicsa.business.skill.Skill")) : class$org$orange$kicsa$business$skill$Skill).getName(), true, 0);
    VariableInfo[] infoArray = new VariableInfo[]{relationshipVariable};
    return infoArray;
  }

  public boolean isValid(TagData data) {
    boolean valid = true;
    String directionArgument = data.getAttributeString("direction");
    if (directionArgument != null && directionArgument != TagData.REQUEST_TIME_VALUE) {
      valid = directionArgument.toLowerCase().equals("to") || directionArgument.toLowerCase().equals("from");
    }

    return valid;
  }
}
