package ru.nsu.fit.makhov.notebook.models;

import java.util.Objects;

public record OperationDescription(String name, int numOfArgs) {

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
