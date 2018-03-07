package org.orange.kicsa.service.ability;

import org.apache.commons.pool.BasePoolableObjectFactory;

public class AbilityServiceFactory extends BasePoolableObjectFactory {
  public AbilityServiceFactory() {
  }

  public Object makeObject() {
    return AbilityServiceImpl.getInstance();
  }
}
