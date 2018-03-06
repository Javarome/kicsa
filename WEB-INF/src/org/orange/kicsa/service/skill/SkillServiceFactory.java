package org.orange.kicsa.service.skill;

import org.apache.commons.pool.BasePoolableObjectFactory;

public class SkillServiceFactory extends BasePoolableObjectFactory {
  public SkillServiceFactory() {
  }

  public Object makeObject() {
    return SkillServiceImpl.getInstance();
  }
}
