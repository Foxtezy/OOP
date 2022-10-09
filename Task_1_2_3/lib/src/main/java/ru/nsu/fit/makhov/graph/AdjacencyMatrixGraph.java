package ru.nsu.fit.makhov.graph;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

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
    matrix.put(newName, matrix.remove(oldName));
    matrix.values().forEach(m -> m.put(newName, m.remove(oldName)));
    initialRow.put(newName, initialRow.remove(oldName));
  }

  @Override
  public void removeVertex(T name) {
    if (!matrix.containsKey(name)) {
      throw new IllegalArgumentException("This vertex is not exist");
    }
    initialRow.remove(name);
    matrix.remove(name);
    matrix.values().forEach(m -> m.remove(name));
  }

  @Override
  public Vertex<T> getVertex(T name) {
    if (!matrix.containsKey(name)) {
      throw new IllegalArgumentException("This vertex is not exist");
    }
    Map<T, Double> adjMap = matrix.get(name);
    Set<T> adjSet = adjMap.entrySet().stream().filter(m -> m.getValue() != INFINITY)
        .map(Entry::getKey).collect(Collectors.toSet());
    return new Vertex<>(name, adjSet);
  }

  @Override
  public void addEdge(Edge<T> newEdge) {
    if (!matrix.containsKey(newEdge.getDeparture())) {
      throw new NoSuchElementException("Departure vertex is not exist");
    }
    if (!matrix.containsKey(newEdge.getDestination())) {
      throw new NoSuchElementException("Destination vertex is not exist");
    }
    matrix.get(newEdge.getDeparture()).put(newEdge.getDestination(), newEdge.getWeight());
  }

  @Override
  public void removeEdge(Edge<T> edge) {
    if (!matrix.containsKey(edge.getDeparture())) {
      throw new NoSuchElementException("Departure vertex is not exist");
    }
    if (!matrix.containsKey(edge.getDestination())) {
      throw new NoSuchElementException("Destination vertex is not exist");
    }
    if (matrix.get(edge.getDeparture()).get(edge.getDestination()).isInfinite()) {
      throw new IllegalArgumentException("This edge is not exist");
    }
    matrix.get(edge.getDeparture()).put(edge.getDestination(), INFINITY);
  }

  @Override
  public Edge<T> getEdge(Edge<T> edge) {
    if (!matrix.containsKey(edge.getDeparture())) {
      throw new NoSuchElementException("Departure vertex is not exist");
    }
    if (!matrix.containsKey(edge.getDestination())) {
      throw new NoSuchElementException("Destination vertex is not exist");
    }
    if (matrix.get(edge.getDeparture()).get(edge.getDestination()).isInfinite()) {
      throw new IllegalArgumentException("This edge is not exist");
    }
    edge.setWeight(matrix.get(edge.getDeparture()).get(edge.getDestination()));
    return edge;
  }
}
