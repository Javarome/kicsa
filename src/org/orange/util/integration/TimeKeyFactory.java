package org.orange.util.integration;

import java.sql.Connection;
import java.util.Properties;

public class TimeKeyFactory implements KeyFactory {
  public TimeKeyFactory() {
  }

  public int createInt(Connection someConnection) {
    throw new RuntimeException("Non supporté");
  }

  public String createString(Connection someConnection) {
    return String.valueOf(this.createLong(someConnection));
  }

  public long createLong(Connection someConnection) {
    return System.currentTimeMillis();
  }

  public void init(Properties someProperties) {
    throw new RuntimeException("Non implémenté");
  }
}
