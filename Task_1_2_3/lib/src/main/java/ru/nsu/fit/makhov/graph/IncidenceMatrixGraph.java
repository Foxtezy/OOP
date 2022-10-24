package ru.nsu.fit.makhov.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import java.util.Set;

public class IncidenceMatrixGraph<T> implements Graph<T> {

  public static final double INFINITY = Double.POSITIVE_INFINITY;
  private final Map<T, List<Double>> matrix = new HashMap<>();

  private final List<Double> initialRow = new LinkedList<>();

  @Override
  public void addVertex(T name) {
    if (matrix.containsKey(name)) {
      throw new IllegalArgumentException("This vertex is already exist");
    }
    matrix.put(name, new LinkedList<>(initialRow));
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
    if (!matrix.containsKey(edge.getDeparture())) {
      return false;
    }
    if (!matrix.containsKey(edge.getDestination())) {
      return false;
    }
    T departure = edge.getDeparture();
    T destination = edge.getDestination();
    for (int i = 0; i < matrix.get(departure).size(); i++) {
      if (matrix.get(departure).get(i) < 0 && matrix.get(departure).get(i) != INFINITY) {
        if (matrix.get(destination).get(i) != INFINITY) {
          return true;
        }
      }
    }
    return false;
  }

  @Override
  public void addEdge(Edge<T> newEdge) {
    if (!matrix.containsKey(newEdge.getDeparture())) {
      throw new NoSuchElementException("Departure vertex is not exist");
    }
    if (!matrix.containsKey(newEdge.getDestination())) {
      throw new NoSuchElementException("Destination vertex is not exist");
    }
    T departure = newEdge.getDeparture();
    T destination = newEdge.getDestination();
    Double weight = newEdge.getWeight();
    matrix.values().forEach(l -> l.add(INFINITY));
    matrix.get(departure).set(matrix.get(departure).size() - 1, (-1) * weight);
    matrix.get(destination).set(matrix.get(destination).size() - 1, weight);
    initialRow.add(INFINITY);
  }

  @Override
  public void removeEdge(Edge<T> edge) {
    if (!isEdgeExist(edge)) {
      throw new NoSuchElementException("Edge is not exist");
    }
    T departure = edge.getDeparture();
    T destination = edge.getDestination();
    for (int i = 0; i < matrix.get(departure).size(); i++) {
      if (matrix.get(departure).get(i) != INFINITY && matrix.get(departure).get(i) < 0
          && matrix.get(destination).get(i) != INFINITY) {
        int finalI = i;
        matrix.values().forEach(l -> l.remove(finalI));
      }
    }
    initialRow.remove(0);
  }

  @Override
  public Edge<T> getEdge(Edge<T> edge) {
    if (!isEdgeExist(edge)) {
      throw new NoSuchElementException("Edge is not exist");
    }
    T departure = edge.getDeparture();
    T destination = edge.getDestination();
    int index = 0;
    for (int i = 0; i < matrix.get(departure).size(); i++) {
      if (matrix.get(departure).get(i) != INFINITY && matrix.get(departure).get(i) < 0
          && matrix.get(destination).get(i) != INFINITY) {
        index = i;
      }
    }
    return new Edge<>(departure, destination, matrix.get(destination).get(index));
  }

  @Override
  public List<Pair<T, Double>> sort(T src) {
    return null;
  }


}
