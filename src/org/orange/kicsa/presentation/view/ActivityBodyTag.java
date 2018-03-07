package org.orange.kicsa.presentation.view;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTagSupport;
import org.orange.util.Preferences;

public class ActivityBodyTag extends BodyTagSupport {
  final String WORKFLOW_PREFIX = "workflow.";
  final String WORKFLOW_TRANSITION = ".transition";
  private String id;
  private Iterator transitionIterator;
  private String labelId;
  private String hrefId;

  public ActivityBodyTag() {
  }

  public int doStartTag() throws JspException {
    Preferences workflowPreferences = Preferences.systemNodeForPackage(class$org$orange$kicsa$Application == null ? (class$org$orange$kicsa$Application = class$("org.orange.kicsa.Application")) : class$org$orange$kicsa$Application);
    List transitions = new ArrayList();
    String transition = workflowPreferences.get("workflow.*.transition", (String)null);
    if (transition != null) {
      transitions.add(transition);
    }

    int i = 1;

    do {
      transition = workflowPreferences.get("workflow." + this.id + ".transition" + i, (String)null);
      if (transition != null) {
        transitions.add(transition);
      }

      ++i;
    } while(transition != null);

    this.transitionIterator = transitions.iterator();
    return this.iterate();
  }

  private int iterate() {
    byte result;
    if (this.transitionIterator.hasNext()) {
      String transition = (String)this.transitionIterator.next();
      int delimiter = transition.indexOf(",");
      String hrefIdValue;
      String labelIdValue;
      if (delimiter >= 0) {
        hrefIdValue = transition.substring(0, delimiter);
        labelIdValue = transition.substring(delimiter + 1);
      } else {
        hrefIdValue = transition;
        labelIdValue = transition;
      }

      super.pageContext.setAttribute(this.hrefId, hrefIdValue);
      super.pageContext.setAttribute(this.labelId, labelIdValue);
      result = 2;
    } else {
      result = 0;
    }

    return result;
  }

  public int doAfterBody() throws JspException {
    BodyContent body = this.getBodyContent();

    try {
      body.writeOut(this.getPreviousOut());
    } catch (IOException var3) {
      throw new JspTagException("ActivityBodyTag: " + var3.getMessage());
    }

    body.clearBody();
    return this.iterate();
  }

  public void setId(String id) {
    this.id = id;
  }

  public void setLabelId(String labelId) {
    this.labelId = labelId;
  }

  public void setHrefId(String hrefId) {
    this.hrefId = hrefId;
  }
}
