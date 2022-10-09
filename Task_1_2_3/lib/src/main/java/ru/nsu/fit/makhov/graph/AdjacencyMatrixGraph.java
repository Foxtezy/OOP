package ru.nsu.fit.makhov.graph;

import java.util.HashMap;
import java.util.Map;

public class AdjacencyMatrixGraph<T> implements Graph<T> {

  public static final double INFINITY = Double.POSITIVE_INFINITY;

  Map<T, Map<T, Double>> matrix = new HashMap<>();

  Map<T, Double> initialRow = new HashMap<>();

  @Override
  public void addVertex(T name) {
    if (matrix.containsKey(name)) {
      throw new IllegalArgumentException("This vertex is already exist");
    }
    matrix.values().forEach(m -> m.put(name, INFINITY));
    initialRow.put(name, INFINITY);
    Map<T, Double> newRow = new HashMap<>(initialRow);
    newRow.put(name, 0.0);
    matrix.put(name, newRow);
  }

  @Override
  public void editVertex(T oldName, T newName) {
    if (!matrix.containsKey(oldName)) {
      throw new IllegalArgumentException("This vertex is not exist");
    }
    matrix.values().forEach(m -> m.put(newName, m.remove(oldName)));
    matrix.put(newName, matrix.remove(oldName));
    initialRow.put(newName, initialRow.remove(oldName));
  }

  @Override
  public void removeVertex(T name) {
    if (!matrix.containsKey(name)) {
      throw new IllegalArgumentException("This vertex is not exist");
    }
    matrix.values().forEach(m -> m.remove(name));
    initialRow.remove(name);
    matrix.remove(name);
  }

  @Override
  public Vertex<T> getVertex(T name) {
    return new AdjacencyMatrixVertex<>(name);
  }

  @Override
  public void addEdge() {

  }

  @Override
  public void editEdge() {

  }

  @Override
  public void removeEdge() {

  }

  @Override
  public void getEdge() {

  }

  public static class AdjacencyMatrixVertex<T> implements Vertex<T> {

    private final T value;

    public AdjacencyMatrixVertex(T value) {
      this.value = value;
    }

    public T getValue() {
      return value;
    }
  }

  public static class AdjacencyMatrixEdge implements Edge {

  }

}
