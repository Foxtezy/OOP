package ru.nsu.fit.makhov.graph;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;
import ru.nsu.fit.makhov.graph.utils.Pair;

/**
 * Class which represents directed, weighted graphs using adjacency list.
 *
 * @param <T> type of names of vertexes
 */
public class IncidenceMatrixGraph<T> implements Graph<T> {

  public static final double INFINITY = Double.POSITIVE_INFINITY;
  private final Map<Vertex<T>, List<Double>> matrix = new HashMap<>();

  private final List<Double> initialRow = new LinkedList<>();

  private final Deque<Long> freeKeys = new ArrayDeque<>();

  private Long lastKey = (long) 0;

  @Override
  public Vertex<T> addVertex(T name) {
    Vertex<T> vertex;
    if (freeKeys.isEmpty()) {
      vertex = new Vertex<>(name, lastKey + 1);
      lastKey++;
    } else {
      vertex = new Vertex<>(name, freeKeys.pop());
    }
    matrix.put(vertex, new LinkedList<>(initialRow));
    return vertex;
  }

  @Override
  public Vertex<T> editVertex(Vertex<T> vertex, T newName) {
    if (!matrix.containsKey(vertex)) {
      throw new IllegalArgumentException("This vertex is not exist");
    }
    Vertex<T> newVertex = new Vertex<>(newName, vertex.getKey());
    matrix.put(newVertex, matrix.remove(vertex));
    return newVertex;
  }

  @Override
  public void removeVertex(Vertex<T> vertex) {
    if (!matrix.containsKey(vertex)) {
      throw new IllegalArgumentException("This vertex is not exist");
    }
    List<Double> removeVertexRow = new ArrayList<>(matrix.get(vertex));
    matrix.remove(vertex);
    for (int i = removeVertexRow.size() - 1; i >= 0; i--) {
      if (removeVertexRow.get(i) != INFINITY) {
        int finalI = i;
        matrix.values().forEach(l -> l.remove(finalI));
      }
    }
  }

  @Override
  public Set<Vertex<T>> getVertexAdjSet(Vertex<T> vertex) {
    if (!matrix.containsKey(vertex)) {
      throw new IllegalArgumentException("This vertex is not exist");
    }
    List<Double> vertexRow = matrix.get(vertex);
    Set<Vertex<T>> adjSet = new HashSet<>();
    for (int i = 0; i < vertexRow.size(); i++) {
      if (vertexRow.get(i) < 0 && vertexRow.get(i) != INFINITY) {
        int finalI = i;
        List<Vertex<T>> v = matrix.entrySet().stream()
            .filter(entry -> entry.getValue().get(finalI) != INFINITY
                && entry.getValue().get(finalI) > 0)
            .map(Entry::getKey).toList();
        adjSet.add(v.get(0));
      }
    }
    return adjSet;
  }

  @Override
  public boolean isEdgeExist(Edge<T> edge) {
    if (!matrix.containsKey(edge.getDeparture())) {
      return false;
    }
    if (!matrix.containsKey(edge.getDestination())) {
      return false;
    }
    Vertex<T> departure = edge.getDeparture();
    Vertex<T> destination = edge.getDestination();
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
    Vertex<T> departure = newEdge.getDeparture();
    Vertex<T> destination = newEdge.getDestination();
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
    Vertex<T> departure = edge.getDeparture();
    Vertex<T> destination = edge.getDestination();
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
    Vertex<T> departure = edge.getDeparture();
    Vertex<T> destination = edge.getDestination();
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
  public List<Pair<Vertex<T>, Double>> sort(Vertex<T> src) {
    var grayVertexes = new ArrayList<>(matrix.keySet());
    Map<Vertex<T>, Double> distances = new HashMap<>();
    for (Vertex<T> v : grayVertexes) {
      distances.put(v, INFINITY);
    }
    distances.replace(src, 0.0);
    Comparator<Entry<Vertex<T>, Double>> mapCmp = (entry1, entry2) -> (
        (entry1.getValue() > entry2.getValue())
            ? 1 : entry1.getValue().equals(entry2.getValue()) ? 0 : -1);
    while (!grayVertexes.isEmpty()) {
      Vertex<T> u = distances.entrySet().stream()
          .filter((entry) -> grayVertexes.contains(entry.getKey()))
          .min(mapCmp).get().getKey();
      grayVertexes.remove(u);
      for (int i = 0; i < matrix.get(u).size(); i++) {
        Pair<Vertex<T>, Double> v = null;
        if (matrix.get(u).get(i) < 0) {
          for (Entry<Vertex<T>, List<Double>> row : matrix.entrySet()) {
            if (row.getValue().get(i) != INFINITY && row.getValue().get(i) > 0) {
              v = new Pair<>(row.getKey(), row.getValue().get(i));
              break;
            }
          }
        }
        if (v != null && distances.get(v.key()) > distances.get(u) + v.value()) {
          distances.replace(v.key(), distances.get(u) + v.value());
        }
      }
    }
    Comparator<Pair<Vertex<T>, Double>> pairCmp = (p1, p2) -> ((p1.value() > p2.value())
        ? 1 : p1.value().equals(p2.value()) ? 0 : -1);
    return distances.entrySet().stream()
        .map((entry) -> new Pair<>(entry.getKey(), entry.getValue())).sorted(pairCmp)
        .collect(Collectors.toList());
  }


}
