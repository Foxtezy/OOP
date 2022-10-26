package ru.nsu.fit.makhov.graph;

import java.util.Objects;
import java.util.Set;

/**
 * Class which represents vertex in weighted, oriented graph.
 *
 * @param <T> type of names of vertexes.
 */
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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Vertex<?> vertex)) {
      return false;
    }
    return getValue().equals(vertex.getValue()) && getAdjSet().equals(vertex.getAdjSet());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getValue(), getAdjSet());
  }
}
