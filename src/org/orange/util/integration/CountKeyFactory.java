package org.orange.util.integration;

import java.sql.Connection;
import java.util.Properties;

public class CountKeyFactory implements KeyFactory {
  public CountKeyFactory() {
  }

  public int createInt(Connection someConnection) {
    throw new RuntimeException("Non implémenté");
  }

  public String createString(Connection someConnection) {
    throw new RuntimeException("Non implémenté");
  }

  public long createLong(Connection someConnection) {
    throw new RuntimeException("Non implémenté");
  }

  public void init(Properties someProperties) {
    throw new RuntimeException("Non implémenté");
  }
}
