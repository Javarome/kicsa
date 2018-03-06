//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.orange.kicsa.business.skill;

import java.util.Collection;
import java.util.Iterator;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;
import org.apache.log4j.Logger;
import org.orange.kicsa.Application;
import org.orange.kicsa.integration.skill.SkillDAO;
import org.orange.util.integration.DAOException;

public class SkillTree implements TreeModel {
  private SkillDAO dao;
  private Skill root;
  private static Logger skillTreeLog;

  public SkillTree(Skill someRootSkill) throws DAOException {
    this.root = someRootSkill;

    try {
      this.dao = Application.getDAOFactory().createSkillDAO();
    } catch (Throwable var3) {
      throw new DAOException(var3);
    }
  }

  public Object getRoot() {
    return this.root;
  }

  public Object getChild(Object someParentSkill, int someIndex) {
    if (!(someParentSkill instanceof Skill)) {
      throw new RuntimeException("Le parent doit être de type " + (class$org$orange$kicsa$business$skill$Skill == null ? (class$org$orange$kicsa$business$skill$Skill = class$("org.orange.kicsa.business.skill.Skill")) : class$org$orange$kicsa$business$skill$Skill));
    } else {
      Object child = null;
      Collection childs = this.getChilds((Skill)someParentSkill);
      Iterator childsIterator = childs.iterator();

      for(int i = 0; i <= someIndex; ++i) {
        child = childsIterator.next();
      }

      return child;
    }
  }

  public Collection getParents(Skill someChild) {
    Collection parents = null;

    try {
      parents = this.dao.getParents(someChild);
      return parents;
    } catch (DAOException var4) {
      var4.printStackTrace();
      skillTreeLog.error(this.dao + " n'a pu récupérer les compétences parents de " + someChild + " en raison de " + var4);
      throw new RuntimeException(this.dao + " n'a pu récupérer les compétences parents de " + someChild + " en raison de " + var4);
    }
  }

  public int getChildCount(Object someParent) {
    int childCount = this.getChilds((Skill)someParent).size();
    return childCount;
  }

  public Collection getChilds(Skill someParent) {
    Collection childs = null;

    try {
      childs = this.dao.getChilds(someParent);
      return childs;
    } catch (DAOException var4) {
      throw new RuntimeException(this.dao + " n'a pu récupérer les compétences filles de " + someParent + " en raison de " + var4);
    }
  }

  public boolean isLeaf(Object someParent) {
    return this.getChilds((Skill)someParent).size() <= 0;
  }

  public void valueForPathChanged(TreePath path, Object obj) {
    System.out.println("valueForPathChanged (" + path + ", " + obj + ")");
  }

  public int getIndexOfChild(Object someParentSkill, Object someChildSkill) {
    if (!(someParentSkill instanceof Skill)) {
      throw new RuntimeException("Tree node was expected to be of type " + (class$org$orange$kicsa$business$skill$Skill == null ? (class$org$orange$kicsa$business$skill$Skill = class$("org.orange.kicsa.business.skill.Skill")) : class$org$orange$kicsa$business$skill$Skill));
    } else {
      int index = -1;
      int count = this.getChildCount(someParentSkill);

      for(int i = 0; i < count; ++i) {
        if (this.getChild(someParentSkill, i).equals(someChildSkill)) {
          index = i;
          break;
        }
      }

      return index;
    }
  }

  public void addTreeModelListener(TreeModelListener modelListener) {
    System.err.println("addTreeModelListener Not implemented");
  }

  public void removeTreeModelListener(TreeModelListener modelListener) {
    System.err.println("removeTreeModelListener Not implemented");
  }

  static {
    skillTreeLog = Logger.getLogger(class$org$orange$kicsa$business$skill$SkillTree == null ? (class$org$orange$kicsa$business$skill$SkillTree = class$("org.orange.kicsa.business.skill.SkillTree")) : class$org$orange$kicsa$business$skill$SkillTree);
  }
}
