package org.orange.util.integration;

import java.sql.Connection;
import java.util.Properties;

public interface KeyFactory {
  int createInt(Connection var1);

  String createString(Connection var1);

  long createLong(Connection var1);

  void init(Properties var1);
}
