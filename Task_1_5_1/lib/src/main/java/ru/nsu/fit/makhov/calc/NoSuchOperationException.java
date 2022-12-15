package ru.nsu.fit.makhov.calc;

/**
 * Throw if operation is not exist.
 */
public class NoSuchOperationException extends RuntimeException {

  public NoSuchOperationException(String operation) {
    super(operation);
  }
}
