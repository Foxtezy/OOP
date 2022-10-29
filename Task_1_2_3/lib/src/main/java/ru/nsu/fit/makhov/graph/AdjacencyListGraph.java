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
public class AdjacencyListGraph<T> implements Graph<T> {

  private final Map<Vertex<T>, Map<Vertex<T>, Double>> adjList = new HashMap<>();

  private final Deque<Long> freeKeys = new ArrayDeque<>();

  private Long lastKey = (long) 0;

  public static final double INFINITY = Double.POSITIVE_INFINITY;

  @Override
  public Vertex<T> addVertex(T name) {
    Vertex<T> vertex;
    if (freeKeys.isEmpty()) {
      vertex = new Vertex<>(name, lastKey + 1);
      lastKey++;
    } else {
      vertex = new Vertex<>(name, freeKeys.pop());
    }
    adjList.put(vertex, new HashMap<>());
    return vertex;
  }

  @Override
  public Vertex<T> editVertex(Vertex<T> vertex, T newName) {
    if (!adjList.containsKey(vertex)) {
      throw new IllegalArgumentException("This vertex is not exist");
    }
    Vertex<T> newVertex = new Vertex<>(newName, vertex.getKey());
    adjList.put(newVertex, adjList.remove(vertex));
    adjList.values().stream().filter(m -> m.containsKey(vertex))
        .forEach(m -> m.put(newVertex, m.remove(vertex)));
    return newVertex;
  }

  @Override
  public void removeVertex(Vertex<T> vertex) {
    if (!adjList.containsKey(vertex)) {
      throw new IllegalArgumentException("This vertex is not exist");
    }
    adjList.remove(vertex);
    adjList.values().forEach(m -> m.remove(vertex));
    freeKeys.push(vertex.getKey());
  }

  @Override
  public Set<Vertex<T>> getVertexAdjSet(Vertex<T> vertex) {
    if (!adjList.containsKey(vertex)) {
      throw new IllegalArgumentException("This vertex is not exist");
    }
    return adjList.get(vertex).keySet();
  }

  @Override
  public boolean isEdgeExist(Edge<T> edge) {
    if (!adjList.containsKey(edge.getDeparture())) {
      return false;
    }
    if (!adjList.containsKey(edge.getDestination())) {
      return false;
    }
    if (adjList.get(edge.getDeparture()).get(edge.getDestination()).isInfinite()) {
      return false;
    }
    return true;
  }

  @Override
  public void addEdge(Edge<T> newEdge) {
    if (!adjList.containsKey(newEdge.getDeparture())) {
      throw new NoSuchElementException("Departure vertex is not exist");
    }
    if (!adjList.containsKey(newEdge.getDestination())) {
      throw new NoSuchElementException("Destination vertex is not exist");
    }
    adjList.get(newEdge.getDeparture()).put(newEdge.getDestination(), newEdge.getWeight());
  }

  @Override
  public void removeEdge(Edge<T> edge) {
    if (!isEdgeExist(edge)) {
      throw new NoSuchElementException("Edge is not exist");
    }
    adjList.get(edge.getDeparture()).remove(edge.getDestination());
  }

  @Override
  public Edge<T> getEdge(Edge<T> edge) {
    if (!isEdgeExist(edge)) {
      throw new NoSuchElementException("Edge is not exist");
    }
    edge.setWeight(adjList.get(edge.getDeparture()).get(edge.getDestination()));
    return edge;
  }

  @Override
  public List<Pair<Vertex<T>, Double>> sort(Vertex<T> src) {
    var grayVertexes = new ArrayList<>(adjList.keySet());
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
      for (Map.Entry<Vertex<T>, Double> v : adjList.get(u).entrySet()) {
        if (distances.get(v.getKey()) > distances.get(u) + v.getValue()) {
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
