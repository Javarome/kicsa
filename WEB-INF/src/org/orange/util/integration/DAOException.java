package org.orange.util.integration;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.sql.SQLException;
import org.orange.util.NestedException;
import org.orange.util.NestedException.Helper;

public class DAOException extends Exception implements NestedException {
  private Throwable nestedException;

  public DAOException(Throwable someRootCause) {
    this("", someRootCause);
  }

  public DAOException(String someMessage, Throwable someRootCause) {
    super(someMessage);
    this.nestedException = someRootCause;
  }

  public DAOException(String someMessage) {
    this(someMessage, (Throwable)null);
  }

  public Throwable getNestedThrowable() {
    return this.nestedException;
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

  public String toString() {
    StringBuffer toStringBuffer = new StringBuffer(this.getClass().getName());
    if (this.getNestedThrowable() == null) {
      toStringBuffer.append(": ").append(this.getMessage());
    } else {
      Throwable nestedThrowable = this.getNestedThrowable();
      if (nestedThrowable instanceof SQLException) {
        SQLException nestedSQLProblem = (SQLException)nestedThrowable;
        StringBuffer messageBuffer = new StringBuffer();
        if (nestedSQLProblem != null) {
          do {
            messageBuffer.append(" en raison de " + nestedSQLProblem.getClass().getName() + ": " + nestedSQLProblem.getMessage());
            nestedSQLProblem = nestedSQLProblem.getNextException();
          } while(nestedSQLProblem != null);
        }

        toStringBuffer.append(messageBuffer);
      } else {
        toStringBuffer.append(" en raison de ").append(nestedThrowable);
      }
    }

    return toStringBuffer.toString();
  }
}
