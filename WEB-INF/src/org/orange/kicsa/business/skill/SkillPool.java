package org.orange.kicsa.business.skill;

import org.apache.commons.pool.PoolableObjectFactory;
import org.apache.commons.pool.impl.StackObjectPool;
import org.orange.kicsa.service.ServiceException;
import org.orange.util.Preferences;

public class SkillPool extends StackObjectPool {
  public static final String INIT_COUNT = "initCount";
  public static final int INIT_COUNT_DEFAULT = 1;
  public static final String MAX_COUNT = "maxCount";
  public static final int MAX_COUNT_DEFAULT = 1;
  public static final String POOLABLE_OBJECT_FACTORY_CLASS_NAME = "poolableObjectFactory";
  public static final String POOLABLE_OBJECT_FACTORY_CLASS_NAME_DEFAULT;
  private static SkillPool instance;
  private int initCount;
  private int maxCount;

  protected SkillPool() {
  }

  public int getInitCount() {
    return this.initCount;
  }

  public void setInitCount(int someInitCount) {
    this.initCount = someInitCount;
  }

  public int getMaxCount() {
    return this.maxCount;
  }

  public void setMaxCount(int someMaxCount) {
    this.maxCount = someMaxCount;
  }

  public void setPoolableObjectFactoryClassName(String somePoolableObjectFactoryClassName) {
    try {
      Class poolableObjectFactoryClass = Class.forName(somePoolableObjectFactoryClassName);
      PoolableObjectFactory poolableObjectFactory = (PoolableObjectFactory)poolableObjectFactoryClass.newInstance();
      this.setFactory(poolableObjectFactory);
    } catch (Exception var4) {
      throw new ServiceException(var4);
    }
  }

  public static SkillPool getInstance() {
    if (instance == null) {
      Class var0 = class$org$orange$kicsa$business$skill$SkillPool == null ? (class$org$orange$kicsa$business$skill$SkillPool = class$("org.orange.kicsa.business.skill.SkillPool")) : class$org$orange$kicsa$business$skill$SkillPool;
      synchronized(var0) {
        if (instance == null) {
          instance = new SkillPool();
          Preferences prefs = Preferences.systemNodeForPackage(class$org$orange$kicsa$business$skill$SkillPool == null ? (class$org$orange$kicsa$business$skill$SkillPool = class$("org.orange.kicsa.business.skill.SkillPool")) : class$org$orange$kicsa$business$skill$SkillPool);
          instance.setInitCount(prefs.getInt("initCount", 1));
          instance.setMaxCount(prefs.getInt("maxCount", 1));
          instance.setPoolableObjectFactoryClassName(prefs.get("poolableObjectFactory", POOLABLE_OBJECT_FACTORY_CLASS_NAME_DEFAULT));
        }
      }
    }

    return instance;
  }

  static {
    POOLABLE_OBJECT_FACTORY_CLASS_NAME_DEFAULT = (class$org$apache$commons$pool$BasePoolableObjectFactory == null ? (class$org$apache$commons$pool$BasePoolableObjectFactory = class$("org.apache.commons.pool.BasePoolableObjectFactory")) : class$org$apache$commons$pool$BasePoolableObjectFactory).getName();
    instance = null;
  }
}
