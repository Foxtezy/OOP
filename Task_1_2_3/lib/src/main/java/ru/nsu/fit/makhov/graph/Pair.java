package ru.nsu.fit.makhov.graph;

public class Pair<T, E> {
  private final T key;
  private final E value;

  public T key() {
    return key;
  }

  public E value() {
    return value;
  }

  public Pair(T key, E value) {
    this.key = key;
    this.value = value;
  }

  @Override
  public String toString() {
    return key +
        "(" + value +
        ')';
  }
}
