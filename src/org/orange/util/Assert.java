package org.orange.util;

import java.util.Iterator;
import org.apache.log4j.Logger;

public class Assert {
  private static boolean enabled = true;
  private static boolean logEnabled = true;
  private static Logger log;

  public Assert() {
  }

  public static int isInt(String someString, String someAssertion) {
    int intValue = -1;

    try {
      intValue = Integer.parseInt(someString);
    } catch (NumberFormatException var4) {
      if (enabled) {
        fail(someAssertion);
      }
    }

    return intValue;
  }

  public static void notNull(Object someObject, String someAssertion) {
    if (enabled && someObject == null) {
      fail(someAssertion);
    }

  }

  public static void notNullArgument(Object someObject, String someAssertion) {
    if (enabled && someObject == null) {
      fail((RuntimeException)(new IllegalArgumentException(someAssertion)));
    }

  }

  public static void notEmptyArgument(Iterator someIterator, String someAssertion) {
    if (enabled && !someIterator.hasNext()) {
      fail((RuntimeException)(new IllegalArgumentException(someAssertion)));
    }

  }

  public static void isValidArgument(boolean someExpression, String someAssertion) {
    if (enabled && !someExpression) {
      fail((RuntimeException)(new IllegalArgumentException(someAssertion)));
    }

  }

  public static void isValid(boolean someExpression, String someAssertion) {
    if (enabled && !someExpression) {
      fail(someAssertion);
    }

  }

  private static void fail(RuntimeException someException) {
    if (logEnabled) {
      log.error(someException);
    }

    throw someException;
  }

  private static void fail(String someAssertion) {
    AssertException assertFailed = new AssertException(someAssertion);
    fail((RuntimeException)assertFailed);
  }

  static {
    log = Logger.getLogger(class$org$orange$util$Assert == null ? (class$org$orange$util$Assert = class$("org.orange.util.Assert")) : class$org$orange$util$Assert);
  }
}
