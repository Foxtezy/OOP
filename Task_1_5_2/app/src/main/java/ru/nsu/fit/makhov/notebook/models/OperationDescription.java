package ru.nsu.fit.makhov.notebook.models;

import java.util.Objects;

public class OperationDescription {

  private final String name;
  private final int numOfArgs;

  private int minNumOfArgs;

  public OperationDescription(String name, int numOfArgs) {
    this.name = name;
    this.numOfArgs = numOfArgs;
  }

  public OperationDescription(String name, int numOfArgs, int minNumOfArgs) {
    this.name = name;
    this.numOfArgs = numOfArgs;
    this.minNumOfArgs = minNumOfArgs;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof OperationDescription that)) {
      return false;
    }
    return numOfArgs == that.numOfArgs && name.equals(that.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, numOfArgs);
  }
}
