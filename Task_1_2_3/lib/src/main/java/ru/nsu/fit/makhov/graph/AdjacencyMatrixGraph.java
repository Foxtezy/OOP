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
public class AdjacencyMatrixGraph<T> implements Graph<T> {

  public static final double INFINITY = Double.POSITIVE_INFINITY;
  private final Map<T, Map<T, Double>> matrix = new HashMap<>();

  Map<T, Double> initialRow = new HashMap<>();

  @Override
  public void addVertex(T name) {
    if (matrix.containsKey(name)) {
      throw new IllegalArgumentException("This vertex is already exist");
    }
    matrix.put(name, new HashMap<>(initialRow));
    matrix.values().forEach(map -> map.put(name, INFINITY));
    initialRow.put(name, INFINITY);
    matrix.get(name).put(name, 0.0);
  }

  @Override
  public void editVertex(T oldName, T newName) {
    if (!matrix.containsKey(oldName)) {
      throw new IllegalArgumentException("This vertex is not exist");
    }
    matrix.put(newName, matrix.remove(oldName));
    matrix.values().forEach(map -> map.put(newName, map.remove(oldName)));
  }

  @Override
  public void removeVertex(T name) {
    if (!matrix.containsKey(name)) {
      throw new IllegalArgumentException("This vertex is not exist");
    }
    matrix.remove(name);
    matrix.values().forEach(map -> map.remove(name));
    initialRow.remove(name);
  }

  @Override
  public Vertex<T> getVertex(T name) {
    if (!matrix.containsKey(name)) {
      throw new IllegalArgumentException("This vertex is not exist");
    }
    Map<T, Double> row = matrix.get(name);
    Set<T> adjSet = row.entrySet().stream().filter(entry -> entry.getValue() != INFINITY)
        .map(Entry::getKey).collect(Collectors.toSet());
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
  public List<Pair<T, Double>> sort(T src) {
    Map<T, Double> distances = new HashMap<>(initialRow);
    distances.replace(src, 0.0);
    var grayVertexes = new ArrayList<>(initialRow.keySet());
    Comparator<Entry<T, Double>> mapCmp = (entry1, entry2) -> (
        (entry1.getValue() > entry2.getValue())
            ? 1 : entry1.getValue().equals(entry2.getValue()) ? 0 : -1);
    while (!grayVertexes.isEmpty()) {
      T u = distances.entrySet().stream().filter((entry) -> grayVertexes.contains(entry.getKey()))
          .min(mapCmp).get().getKey();
      grayVertexes.remove(u);
      for (Map.Entry<T, Double> v : matrix.get(u).entrySet()) {
        if (v.getValue() != INFINITY
            && distances.get(v.getKey()) > distances.get(u) + v.getValue()) {
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
