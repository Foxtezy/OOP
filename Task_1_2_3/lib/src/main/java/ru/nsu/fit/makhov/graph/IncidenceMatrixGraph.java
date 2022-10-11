package ru.nsu.fit.makhov.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

public class IncidenceMatrixGraph<T> implements Graph<T> {

  public static final double INFINITY = Double.POSITIVE_INFINITY;
  private final Map<T, List<Double>> matrix = new HashMap<>();

  private final List<Double> initialRow = new ArrayList<>();

  @Override
  public void addVertex(T name) {
    if (matrix.containsKey(name)) {
      throw new IllegalArgumentException("This vertex is already exist");
    }
    matrix.put(name, new ArrayList<>(initialRow));
  }

  @Override
  public void editVertex(T oldName, T newName) {
    if (!matrix.containsKey(oldName)) {
      throw new IllegalArgumentException("This vertex is not exist");
    }
    matrix.put(newName, matrix.remove(oldName));
  }

  @Override
  public void removeVertex(T name) {
    if (!matrix.containsKey(name)) {
      throw new IllegalArgumentException("This vertex is not exist");
    }
    List<Double> removeVertexRow = new ArrayList<>(matrix.get(name));
    matrix.remove(name);
    for (int i = 0; i < removeVertexRow.size(); i++) {
      if (removeVertexRow.get(i) != INFINITY) {
        int finalI = i;
        matrix.values().forEach(l -> l.remove(finalI));
      }
    }
  }

  @Override
  public Vertex<T> getVertex(T name) {
    if (!matrix.containsKey(name)) {
      throw new IllegalArgumentException("This vertex is not exist");
    }
    List<Double> vertexRow = matrix.get(name);
    Set<T> adjSet = new HashSet<>();
    for (int i = 0; i < vertexRow.size(); i++) {
      if (vertexRow.get(i) < 0 && vertexRow.get(i) != INFINITY) {
        int finalI = i;
        List<T> vertex = matrix.entrySet().stream()
            .filter(entry -> entry.getValue().get(finalI) != INFINITY && entry.getKey() != name)
            .map(Entry::getKey).toList();
        adjSet.add(vertex.get(0));
      }
    }
    return new Vertex<>(name, adjSet);
  }

  @Override
  public boolean isEdgeExist(Edge<T> edge) {
    return false;
  }

  @Override
  public void addEdge(Edge<T> newEdge) {

  }

  @Override
  public void removeEdge(Edge<T> edge) {

  }

  @Override
  public Edge<T> getEdge(Edge<T> edge) {
    return null;
  }
}
