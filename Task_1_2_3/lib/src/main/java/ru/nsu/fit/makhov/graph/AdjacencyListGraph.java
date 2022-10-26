package ru.nsu.fit.makhov.graph;

import java.util.ArrayList;
import java.util.Comparator;
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

  Map<T, Map<T, Double>> adjList = new HashMap<>();

  public static final double INFINITY = Double.POSITIVE_INFINITY;

  @Override
  public void addVertex(T name) {
    if (adjList.containsKey(name)) {
      throw new IllegalArgumentException("This vertex is already exist");
    }
    adjList.put(name, new HashMap<>());
  }

  @Override
  public void editVertex(T oldName, T newName) {
    if (!adjList.containsKey(oldName)) {
      throw new IllegalArgumentException("This vertex is not exist");
    }
    adjList.put(newName, adjList.remove(oldName));
    adjList.values().stream().filter(m -> m.containsKey(oldName)).forEach(m -> m.put(newName, m.remove(oldName)));
  }

  @Override
  public void removeVertex(T name) {
    if (!adjList.containsKey(name)) {
      throw new IllegalArgumentException("This vertex is not exist");
    }
    adjList.remove(name);
    adjList.values().forEach(m -> m.remove(name));
  }

  @Override
  public Vertex<T> getVertex(T name) {
    if (!adjList.containsKey(name)) {
      throw new IllegalArgumentException("This vertex is not exist");
    }
    Set<T> adjSet = adjList.get(name).keySet();
    return new Vertex<>(name, adjSet);
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
  public List<Pair<T, Double>> sort(T src) {
    var grayVertexes = new ArrayList<>(adjList.keySet());
    Map<T, Double> distances = new HashMap<>();
    for (T v : grayVertexes) {
      distances.put(v, INFINITY);
    }
    distances.replace(src, 0.0);
    Comparator<Entry<T, Double>> mapCmp = (entry1, entry2) -> (
        (entry1.getValue() > entry2.getValue())
            ? 1 : entry1.getValue().equals(entry2.getValue()) ? 0 : -1);
    while (!grayVertexes.isEmpty()) {
      T u = distances.entrySet().stream().filter((entry) -> grayVertexes.contains(entry.getKey()))
          .min(mapCmp).get().getKey();
      grayVertexes.remove(u);
      for (Map.Entry<T, Double> v : adjList.get(u).entrySet()) {
        if (distances.get(v.getKey()) > distances.get(u) + v.getValue()) {
          distances.replace(v.getKey(), distances.get(u) + v.getValue());
        }
      }
    }
    Comparator<Pair<T, Double>> pairCmp = (p1, p2) -> ((p1.value() > p2.value())
        ? 1 : p1.value().equals(p2.value()) ? 0 : -1);
    return distances.entrySet().stream()
        .map((entry) -> new Pair<>(entry.getKey(), entry.getValue())).sorted(pairCmp)
        .collect(Collectors.toList());
  }


}
