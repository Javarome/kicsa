package org.orange.util;

public class AssertException extends RuntimeException {
  private boolean fatal;

  public AssertException(String someAssertion, boolean someFatalState) {
    super("L'assertion \"" + someAssertion + "\" aurait dû être vraie");
    this.fatal = someFatalState;
  }

  public AssertException(String someAssertion) {
    this(someAssertion, true);
  }

  public boolean isFatal() {
    return this.fatal;
  }
}
