package org.orange.kicsa.presentation.view;

import javax.servlet.jsp.tagext.TagData;
import javax.servlet.jsp.tagext.TagExtraInfo;
import javax.servlet.jsp.tagext.VariableInfo;

public class ActivityBodyTagExtraInfo extends TagExtraInfo {
  public ActivityBodyTagExtraInfo() {
  }

  public VariableInfo[] getVariableInfo(TagData data) {
    VariableInfo hrefVariable = new VariableInfo(data.getAttributeString("hrefId"), (class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String).getName(), true, 0);
    VariableInfo labelVariable = new VariableInfo(data.getAttributeString("labelId"), (class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String).getName(), true, 0);
    VariableInfo[] infoArray = new VariableInfo[]{hrefVariable, labelVariable};
    return infoArray;
  }

  public boolean isValid(TagData data) {
    return super.isValid(data);
  }
}
