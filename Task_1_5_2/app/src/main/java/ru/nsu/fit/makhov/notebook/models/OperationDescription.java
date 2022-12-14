package ru.nsu.fit.makhov.notebook.models;

import java.util.Objects;

public class OperationDescription {

  private final String name;
  private final int numOfArgs;

  private final boolean varArg;

  public OperationDescription(String name, int numOfArgs, boolean varArg) {
    this.name = name;
    this.numOfArgs = numOfArgs;
    this.varArg = varArg;
  }

  public String getName() {
    return name;
  }

  public int getNumOfArgs() {
    return numOfArgs;
  }

  public boolean isVarArg() {
    return varArg;
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
