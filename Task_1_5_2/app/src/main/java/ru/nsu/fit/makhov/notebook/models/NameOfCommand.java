package ru.nsu.fit.makhov.notebook.models;

import java.util.Objects;

public class NameOfCommand {
  private String name;
  private Integer arity;

  public NameOfCommand(String name, Integer arity) {
    this.name = name;
    this.arity = arity;
  }

  public String getName() {
    return name;
  }

  public Integer getArity() {
    return arity;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof NameOfCommand that)) {
      return false;
    }
    return getName().equals(that.getName()) && getArity().equals(that.getArity());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getName(), getArity());
  }
}
