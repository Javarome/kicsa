package org.orange.util;

import java.io.PrintStream;
import java.io.PrintWriter;
import org.orange.util.NestedException.Helper;

public class BackingStoreException extends Exception implements NestedException {
  private Throwable rootCause;

  public BackingStoreException(Throwable someRootCause) {
    this("", someRootCause);
  }

  public BackingStoreException(String someMessage, Throwable someRootCause) {
    super(someMessage);
    this.rootCause = someRootCause;
  }

  public BackingStoreException(String someMessage) {
    this(someMessage, (Throwable)null);
  }

  public String toString() {
    StringBuffer toStringBuffer = new StringBuffer(this.getClass().getName());
    if (this.getNestedThrowable() == null) {
      toStringBuffer.append(": ").append(this.getMessage());
    } else {
      toStringBuffer.append(" because of ").append(this.getNestedThrowable());
    }

    return toStringBuffer.toString();
  }

  public Throwable getNestedThrowable() {
    return this.rootCause;
  }

  public void printStackTrace(PrintStream s) {
    synchronized(s) {
      Helper.printNestedStackTrace(s, this);
      super.printStackTrace(s);
    }
  }

  public void printStackTrace(PrintWriter w) {
    synchronized(w) {
      Helper.printNestedStackTrace(w, this);
      super.printStackTrace(w);
    }
  }
}
