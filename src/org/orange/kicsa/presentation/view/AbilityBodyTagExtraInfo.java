package org.orange.kicsa.presentation.view;

import javax.servlet.jsp.tagext.TagData;
import javax.servlet.jsp.tagext.TagExtraInfo;
import javax.servlet.jsp.tagext.VariableInfo;

public class AbilityBodyTagExtraInfo extends TagExtraInfo {
  static final String DIRECTION = "direction";
  static final String TO_DIRECTION = "to";
  static final String FROM_DIRECTION = "from";
  static final String TYPE = "type";
  static final String PARENTS_TYPE = "parents";
  static final String CHILDS_TYPE = "childs";
  static final String ABILITY_ID = "abilityId";

  public AbilityBodyTagExtraInfo() {
  }

  public VariableInfo[] getVariableInfo(TagData data) {
    VariableInfo abilityVariable = new VariableInfo(data.getAttributeString("abilityId"), (class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String).getName(), true, 0);
    VariableInfo[] infoArray = new VariableInfo[]{abilityVariable};
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
