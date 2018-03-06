package org.orange.kicsa.service.skill;

import org.apache.commons.pool.PoolableObjectFactory;
import org.apache.commons.pool.impl.StackObjectPool;
import org.apache.log4j.Logger;
import org.orange.kicsa.service.ServiceException;
import org.orange.util.Assert;
import org.orange.util.Preferences;

public class SkillServicePool extends StackObjectPool {
  public static final String INIT_COUNT = "initCount";
  public static final int INIT_COUNT_DEFAULT = 1;
  public static final String MAX_COUNT = "maxCount";
  public static final int MAX_COUNT_DEFAULT = 1;
  public static final String POOLABLE_OBJECT_FACTORY_CLASS_NAME = "poolableObjectFactory";
  public static final String POOLABLE_OBJECT_FACTORY_CLASS_NAME_DEFAULT;
  private static final Logger log;
  private static SkillServicePool instance;
  private int initCount;
  private int maxCount;

  protected SkillServicePool() {
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
    PoolableObjectFactory poolableObjectFactory;
    try {
      Class poolableObjectFactoryClass = Class.forName(somePoolableObjectFactoryClassName);
      poolableObjectFactory = (PoolableObjectFactory)poolableObjectFactoryClass.newInstance();
      this.setFactory(poolableObjectFactory);
    } catch (Exception var4) {
      throw new ServiceException(var4);
    }

    Assert.notNull(poolableObjectFactory, "La fabrique d'objets poolables ne peut être nulle");
  }

  public static SkillServicePool getInstance() {
    if (instance == null) {
      Class var0 = class$org$orange$kicsa$service$skill$SkillServicePool == null ? (class$org$orange$kicsa$service$skill$SkillServicePool = class$("org.orange.kicsa.service.skill.SkillServicePool")) : class$org$orange$kicsa$service$skill$SkillServicePool;
      synchronized(var0) {
        if (instance == null) {
          instance = new SkillServicePool();
          Preferences prefs = Preferences.systemNodeForPackage(class$org$orange$kicsa$service$skill$SkillServicePool == null ? (class$org$orange$kicsa$service$skill$SkillServicePool = class$("org.orange.kicsa.service.skill.SkillServicePool")) : class$org$orange$kicsa$service$skill$SkillServicePool);
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
    log = Logger.getLogger(class$org$orange$kicsa$service$skill$SkillServicePool == null ? (class$org$orange$kicsa$service$skill$SkillServicePool = class$("org.orange.kicsa.service.skill.SkillServicePool")) : class$org$orange$kicsa$service$skill$SkillServicePool);
    instance = null;
  }
}
