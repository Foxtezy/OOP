package ru.nsu.fit.makhov.graph.utils;

/**
 * Pair of some objects.
 *
 * @param key   first object.
 * @param value second object.
 * @param <T>   type of first object.
 * @param <E>   type of second object.
 */
public record Pair<T, E>(T key, E value) {

  @Override
  public String toString() {
    return key + "(" + value + ')';
  }
}
