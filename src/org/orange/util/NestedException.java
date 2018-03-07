package org.orange.util;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.servlet.ServletException;

public interface NestedException {
  Throwable getNestedThrowable();

  public static class Helper {
    public Helper() {
    }

    public static void printNestedStackTrace(PrintStream s, NestedException ne) {
      Throwable rootCause = ne.getNestedThrowable();
      if (rootCause != null) {
        rootCause.printStackTrace(s);
      }

    }

    public static void printNestedStackTrace(PrintWriter w, NestedException ne) {
      Throwable rootCause = ne.getNestedThrowable();
      if (rootCause != null) {
        rootCause.printStackTrace(w);
      }

    }

    public static String toString(NestedException ne, String superToString) {
      Throwable rootCause = ne.getNestedThrowable();
      return rootCause == null ? superToString : rootCause.toString() + " - with nested exception:\n[" + superToString + "]";
    }

    public static void printStackTrace(PrintWriter w, Throwable t) {
      if (printStackTraceInternal(w, getNestedThrowable(t))) {
        t.printStackTrace(w);
      }

    }

    private static boolean printStackTraceInternal(PrintWriter w, Throwable t) {
      if (t == null) {
        return false;
      } else {
        if (printStackTraceInternal(w, getNestedThrowable(t))) {
          t.printStackTrace(w);
        }

        return true;
      }
    }

    public static Throwable getInitialNestedThrowable(Throwable t) {
      Throwable rootCause;
      while((rootCause = getNestedThrowable(t)) != null) {
        t = rootCause;
      }

      return t;
    }

    private static Throwable getNestedThrowable(Throwable t) {
      Throwable nested = null;
      if (t instanceof NestedException) {
        nested = ((NestedException)t).getNestedThrowable();
      } else if (t instanceof ServletException) {
        nested = ((ServletException)t).getRootCause();
      } else if (t instanceof SQLException) {
        nested = ((SQLException)t).getNextException();
      }

      return (Throwable)nested;
    }
  }
}
