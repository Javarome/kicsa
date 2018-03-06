package org.orange.kicsa.service;

import java.io.PrintStream;
import java.io.PrintWriter;
import org.orange.util.NestedException;
import org.orange.util.NestedException.Helper;

public class ServiceException extends RuntimeException implements NestedException {
  private Throwable rootCause;

  public ServiceException(Throwable someRootCause) {
    this("", someRootCause);
  }

  public ServiceException(String someMessage, Throwable someRootCause) {
    super(someMessage);
    this.rootCause = someRootCause;
  }

  public ServiceException(String someMessage) {
    this(someMessage, (Throwable)null);
  }

  public String toString() {
    StringBuffer toStringBuffer = new StringBuffer(this.getClass().getName());
    if (this.getNestedThrowable() == null) {
      toStringBuffer.append(": ").append(this.getMessage());
    } else {
      toStringBuffer.append(" en raison de ").append(this.getNestedThrowable());
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
