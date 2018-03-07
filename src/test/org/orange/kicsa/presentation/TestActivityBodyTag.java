package test.org.orange.kicsa.presentation;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyContent;
import junit.framework.Assert;
import junit.framework.TestCase;
import org.orange.kicsa.presentation.view.ActivityBodyTag;

public class TestActivityBodyTag extends TestCase {
  public TestActivityBodyTag(String name) {
    super(name);
  }

  protected void setUp() {
  }

  protected void tearDown() {
  }

  public void testTag() {
    try {
      PageContext pageContext = null;
      ActivityBodyTag activityTag = new ActivityBodyTag();
      activityTag.setPageContext((PageContext)pageContext);
      activityTag.setHrefId("transitionHref");
      activityTag.setLabelId("transitionLabel");
      BodyContent out = ((PageContext)pageContext).pushBody();
      activityTag.setBodyContent(out);
      activityTag.doInitBody();

      int result;
      do {
        result = activityTag.doAfterBody();
        if (result == 2) {
          ;
        }
      } while(result == 2);

      activityTag.doAfterBody();
      activityTag.doEndTag();
      ((PageContext)pageContext).popBody();
      activityTag.release();
    } catch (JspException var5) {
      Assert.fail(var5.toString());
    }

  }
}
