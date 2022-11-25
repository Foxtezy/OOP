package ru.nsu.fit.makhov.graph;

import java.util.Objects;

/**
 * Class which represents vertex in weighted, oriented graph.
 *
 * @param <T> type of names of vertexes.
 */
public class Vertex<T> {

  private T value;
  private final long key;

  public Vertex(T value, long key) {
    this.value = value;
    this.key = key;
  }

  public T getValue() {
    return value;
  }

  public long getKey() {
    return key;
  }

  public void setValue(T value) {
    this.value = value;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Vertex<?> vertex)) {
      return false;
    }
    return getKey() == vertex.getKey();
  }

  @Override
  public int hashCode() {
    return Objects.hash(getKey());
  }

  @Override
  public String toString() {
    return "(" + value + "," + key + ')';
  }
}
