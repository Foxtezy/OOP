package ru.nsu.fit.makhov.graph.utils;

/**
 * Pair os some objects.
 *
 * @param <T> type of first object.
 * @param <E> type of second object.
 */
public class Pair<T, E> {

  private final T key;

  private final E value;

  public Pair(T key, E value) {
    this.key = key;
    this.value = value;
  }

  public T getKey() {
    return key;
  }

  public E getValue() {
    return value;
  }

  @Override
  public String toString() {
    return key + "(" + value + ')';
  }
}
