package ru.nsu.fit.makhov.graph.utils;

public record Pair<T, E>(T key, E value) {

  @Override
  public String toString() {
    return key +
        "(" + value +
        ')';
  }
}
