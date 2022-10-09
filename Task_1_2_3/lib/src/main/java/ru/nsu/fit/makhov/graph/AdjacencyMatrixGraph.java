package ru.nsu.fit.makhov.graph;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

public class AdjacencyMatrixGraph<T> implements Graph<T> {

  public static final double INFINITY = Double.POSITIVE_INFINITY;

  private static final int MAX_CAPACITY = 1000;
  private final double[][] matrix = new double[MAX_CAPACITY][MAX_CAPACITY];

  private final Map<Integer, T> mapIndexToName = new HashMap<>();
  private final Map<T, Integer> mapNameToIndex = new HashMap<>();

  private int size = 0;

  public AdjacencyMatrixGraph() {
    for (int i = 0; i < MAX_CAPACITY; i++) {
      for (int j = 0; j < MAX_CAPACITY; j++) {
        matrix[i][j] = INFINITY;
      }
    }
  }

  @Override
  public void addVertex(T name) {
    if (mapNameToIndex.containsKey(name)) {
      throw new IllegalArgumentException("This vertex is already exist");
    }
    mapIndexToName.put(size, name);
    mapNameToIndex.put(name, size);
    matrix[size][size] = 0;
    size++;
  }

  @Override
  public void editVertex(T oldName, T newName) {
    if (!mapNameToIndex.containsKey(oldName)) {
      throw new IllegalArgumentException("This vertex is not exist");
    }

    mapNameToIndex.put(newName, mapNameToIndex.remove(oldName));
    mapIndexToName.replace(mapNameToIndex.get(newName), newName);
  }

  @Override
  public Vertex<T> getVertex(T name) {
    if (!mapNameToIndex.containsKey(name)) {
      throw new IllegalArgumentException("This vertex is not exist");
    }
    int pos = mapNameToIndex.get(name);
    Set<T> adjSet = new HashSet<>();
    for (int i = 0; i < size; i++) {
      if (matrix[pos][i] != INFINITY) {
        adjSet.add(mapIndexToName.get(i));
      }
    }
    adjSet.remove(name);
    return new Vertex<>(name, adjSet);
  }

  @Override
  public void addEdge(Edge<T> newEdge) {
    if (!mapNameToIndex.containsKey(newEdge.getDeparture())) {
      throw new NoSuchElementException("Departure vertex is not exist");
    }
    if (!mapNameToIndex.containsKey(newEdge.getDestination())) {
      throw new NoSuchElementException("Destination vertex is not exist");
    }
    int indDeparture = mapNameToIndex.get(newEdge.getDeparture());
    int indDestination = mapNameToIndex.get(newEdge.getDestination());
    matrix[indDeparture][indDestination] = newEdge.getWeight();
  }

  @Override
  public void removeEdge(Edge<T> edge) {
    if (!mapNameToIndex.containsKey(edge.getDeparture())) {
      throw new NoSuchElementException("Departure vertex is not exist");
    }
    if (!mapNameToIndex.containsKey(edge.getDestination())) {
      throw new NoSuchElementException("Destination vertex is not exist");
    }
    int indDeparture = mapNameToIndex.get(edge.getDeparture());
    int indDestination = mapNameToIndex.get(edge.getDestination());
    if (matrix[indDestination][indDeparture] == INFINITY) {
      throw new IllegalArgumentException("This edge is not exist");
    }
    matrix[indDestination][indDeparture] = INFINITY;
  }

  @Override
  public Edge<T> getEdge(Edge<T> edge) {
    if (!mapNameToIndex.containsKey(edge.getDeparture())) {
      throw new NoSuchElementException("Departure vertex is not exist");
    }
    if (!mapNameToIndex.containsKey(edge.getDestination())) {
      throw new NoSuchElementException("Destination vertex is not exist");
    }
    int indDeparture = mapNameToIndex.get(edge.getDeparture());
    int indDestination = mapNameToIndex.get(edge.getDestination());
    if (matrix[indDestination][indDeparture] == INFINITY) {
      throw new IllegalArgumentException("This edge is not exist");
    }
    edge.setWeight(matrix[indDestination][indDeparture]);
    return edge;
  }
}
