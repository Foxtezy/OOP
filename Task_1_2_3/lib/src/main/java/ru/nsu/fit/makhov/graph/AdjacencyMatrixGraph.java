package ru.nsu.fit.makhov.graph;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashMap;
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
public class AdjacencyMatrixGraph<T> implements Graph<T> {

  public static final double INFINITY = Double.POSITIVE_INFINITY;
  private final Map<Vertex<T>, Map<Vertex<T>, Double>> matrix = new HashMap<>();

  Map<Vertex<T>, Double> initialRow = new HashMap<>();

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
    matrix.put(vertex, new HashMap<>(initialRow));
    matrix.values().forEach(map -> map.put(vertex, INFINITY));
    initialRow.put(vertex, INFINITY);
    matrix.get(vertex).put(vertex, 0.0);
    return vertex;
  }

  @Override
  public Vertex<T> editVertex(Vertex<T> vertex, T newName) {
    if (!matrix.containsKey(vertex)) {
      throw new IllegalArgumentException("This vertex is not exist");
    }
    Vertex<T> newVertex = new Vertex<>(newName, vertex.getKey());
    matrix.put(newVertex, matrix.remove(vertex));
    matrix.values().forEach(map -> map.put(newVertex, map.remove(vertex)));
    initialRow.put(newVertex, initialRow.remove(vertex));
    return newVertex;
  }

  @Override
  public void removeVertex(Vertex<T> vertex) {
    if (!matrix.containsKey(vertex)) {
      throw new IllegalArgumentException("This vertex is not exist");
    }
    matrix.remove(vertex);
    matrix.values().forEach(map -> map.remove(vertex));
    initialRow.remove(vertex);
    freeKeys.push(vertex.getKey());
  }

  @Override
  public Set<Vertex<T>> getVertexAdjSet(Vertex<T> vertex) {
    if (!matrix.containsKey(vertex)) {
      throw new IllegalArgumentException("This vertex is not exist");
    }
    Map<Vertex<T>, Double> row = matrix.get(vertex);
    Set<Vertex<T>> adjSet = row.entrySet().stream().filter(entry -> entry.getValue() != INFINITY)
        .map(Entry::getKey).collect(Collectors.toSet());
    adjSet.remove(vertex);
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
    if (matrix.get(edge.getDestination()).get(edge.getDeparture()).isInfinite()) {
      return false;
    }
    return true;
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
    if (!isEdgeExist(edge)) {
      throw new NoSuchElementException("Edge is not exist");
    }
    matrix.get(edge.getDeparture()).put(edge.getDestination(), INFINITY);
  }

  @Override
  public Edge<T> getEdge(Edge<T> edge) {
    if (!isEdgeExist(edge)) {
      throw new NoSuchElementException("Edge is not exist");
    }
    edge.setWeight(matrix.get(edge.getDeparture()).get(edge.getDestination()));
    return edge;
  }

  @Override
  public List<Pair<Vertex<T>, Double>> sort(Vertex<T> src) {
    Map<Vertex<T>, Double> distances = new HashMap<>(initialRow);
    distances.replace(src, 0.0);
    var grayVertexes = new ArrayList<>(initialRow.keySet());
    Comparator<Entry<Vertex<T>, Double>> mapCmp = (entry1, entry2) -> (
        (entry1.getValue() > entry2.getValue())
            ? 1 : entry1.getValue().equals(entry2.getValue()) ? 0 : -1);
    while (!grayVertexes.isEmpty()) {
      Vertex<T> u = distances.entrySet().stream()
          .filter((entry) -> grayVertexes.contains(entry.getKey()))
          .min(mapCmp).get().getKey();
      grayVertexes.remove(u);
      for (Map.Entry<Vertex<T>, Double> v : matrix.get(u).entrySet()) {
        if (v.getValue() != INFINITY
            && distances.get(v.getKey()) > distances.get(u) + v.getValue()) {
          distances.replace(v.getKey(), distances.get(u) + v.getValue());
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
