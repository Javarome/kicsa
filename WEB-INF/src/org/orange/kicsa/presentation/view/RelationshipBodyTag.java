package org.orange.kicsa.presentation.view;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTagSupport;
import org.orange.kicsa.business.skill.Skill;
import org.orange.kicsa.service.skill.SkillService;
import org.orange.kicsa.service.skill.SkillServicePool;

public class RelationshipBodyTag extends BodyTagSupport {
  private String skill;
  private Iterator relationshipIterator;
  private static SkillServicePool skillServicePool;
  private String relationshipId;
  private String direction = "to";
  private String type;

  public RelationshipBodyTag() {
    skillServicePool = SkillServicePool.getInstance();
  }

  public int doStartTag() throws JspException {
    try {
      Skill foundSkill = (Skill)super.pageContext.findAttribute(this.skill);
      SkillService skillService = (SkillService)skillServicePool.borrowObject();

      Collection relationships;
      try {
        if (this.direction.equals("to")) {
          if (this.type.equals("parents")) {
            relationships = skillService.findParents(foundSkill);
          } else {
            relationships = skillService.findDestinations(foundSkill, this.type);
          }
        } else {
          if (!this.direction.equals("from")) {
            throw new JspException("Relationship direction \"" + this.direction + "\" is not supported");
          }

          if (this.type.equals("childs")) {
            relationships = skillService.findChilds(foundSkill);
          } else {
            relationships = skillService.findSources(foundSkill, this.type);
          }
        }
      } finally {
        skillServicePool.returnObject(skillService);
      }

      this.relationshipIterator = relationships.iterator();
      return this.iterate();
    } catch (Exception var9) {
      throw new JspException(var9.getClass().getName() + ": " + var9.getMessage());
    }
  }

  private int iterate() {
    byte result;
    if (this.relationshipIterator.hasNext()) {
      Skill relationshipValue = (Skill)this.relationshipIterator.next();
      super.pageContext.setAttribute(this.relationshipId, relationshipValue);
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
      throw new JspTagException("RelationshipBodyTag: " + var3.getClass().getName() + ":" + var3.getMessage());
    }

    body.clearBody();
    return this.iterate();
  }

  public void setSkill(String skill) {
    this.skill = skill;
  }

  public void setRelationshipId(String relationshipId) {
    this.relationshipId = relationshipId;
  }

  public String getDirection() {
    return this.direction;
  }

  public void setDirection(String direction) {
    this.direction = direction;
  }

  public void setType(String someType) {
    if (someType.equals("childs")) {
      this.direction = "from";
    }

    this.type = someType;
  }
}
