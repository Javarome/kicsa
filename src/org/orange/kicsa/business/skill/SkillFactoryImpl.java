package org.orange.kicsa.business.skill;

import java.util.SortedMap;
import java.util.TreeMap;
import org.apache.commons.pool.BasePoolableObjectFactory;
import org.orange.kicsa.business.Editable.Status;
import org.orange.util.BackingStoreException;
import org.orange.util.Preferences;
import org.orange.util.integration.DAOException;

public class SkillFactoryImpl extends BasePoolableObjectFactory implements SkillFactory {
  private SortedMap rootLevels;
  public static final String ROOT_ID = "skill.root.id";
  public static final int ROOT_ID_DEFAULT = 1;
  public static final String ROOT_NAME = "skill.root.name";
  public static final String ROOT_NAME_DEFAULT = "Compétences";
  public static final String ROOT_DESCRIPTION = "skill.root.description";
  public static final String ROOT_DESCRIPTION_DEFAULT = null;
  public static final String ROOT_COMMENTS = "skill.root.comments";
  public static final String ROOT_COMMENTS_DEFAULT = null;
  public static final String ROOT_LEVEL_NAME = "skill.root.level.name";
  public static final String ROOT_LEVEL_DESCRIPTION = "skill.root.level.description";
  private Skill root;
  private SkillTree tree;

  public Object makeObject() {
    return new SkillImpl("", "", "", this.rootLevels);
  }

  public SkillFactoryImpl() throws DAOException {
    try {
      Preferences preferences = Preferences.systemNodeForPackage(class$org$orange$kicsa$business$skill$SkillFactory == null ? (class$org$orange$kicsa$business$skill$SkillFactory = class$("org.orange.kicsa.business.skill.SkillFactory")) : class$org$orange$kicsa$business$skill$SkillFactory);
      int rootId = preferences.getInt("skill.root.id", 1);
      new SkillKey(rootId);
      String rootName = preferences.get("skill.root.name", "Compétences");
      String rootComments = preferences.get("skill.root.comments", ROOT_COMMENTS_DEFAULT);
      String rootDescription = preferences.get("skill.root.description", ROOT_DESCRIPTION_DEFAULT);
      this.rootLevels = new TreeMap();
      String rootLevelName = null;

      for(int rootLevelIndex = 1; (rootLevelName = preferences.get("skill.root.level.name" + rootLevelIndex, (String)null)) != null; ++rootLevelIndex) {
        String rootLevelDescription = preferences.get("skill.root.level.description" + rootLevelIndex, (String)null);
        this.rootLevels.put(String.valueOf(rootLevelIndex), new SkillLevelImpl(rootLevelIndex, rootLevelName, rootLevelDescription));
      }

      this.root = new SkillImpl(rootId, rootName, rootDescription, rootComments, this.rootLevels, Status.SYNC);
      this.tree = new SkillTree(this.root);
    } catch (RuntimeException e) {
      e.printStackTrace();
      throw e;
    }
  }

  public int getRootId() {
    return this.root.getId();
  }

  public SkillKey getRootKey() {
    return this.root.getKey();
  }

  public Skill getRoot() {
    return this.root;
  }

  public void setRoot(Skill someRoot) throws BackingStoreException {
    Preferences preferences = Preferences.systemNodeForPackage(class$org$orange$kicsa$business$skill$SkillFactory == null ? (class$org$orange$kicsa$business$skill$SkillFactory = class$("org.orange.kicsa.business.skill.SkillFactory")) : class$org$orange$kicsa$business$skill$SkillFactory);
    preferences.putInt("skill.root.id", someRoot.getId());
    preferences.flush();
    this.root = someRoot;
  }

  public SkillTree getTree() {
    return this.tree;
  }
}
