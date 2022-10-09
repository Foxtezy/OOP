package ru.nsu.fit.makhov.graph;

import java.util.Set;

public class Vertex<T> {

  private final T value;

  private final Set<T> adjSet;

  public Vertex(T value, Set<T> adjSet) {
    this.value = value;
    this.adjSet = adjSet;
  }

  public T getValue() {
    return value;
  }

  public Set<T> getAdjSet() {
    return adjSet;
  }

}
