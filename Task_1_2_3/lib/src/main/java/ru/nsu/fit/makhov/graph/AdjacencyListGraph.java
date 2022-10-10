package ru.nsu.fit.makhov.graph;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

public class AdjacencyListGraph<T> implements Graph<T> {

  Map<T, Map<T, Double>> adjList = new HashMap<>();

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
    adjList.values().forEach(m -> m.put(newName, m.remove(oldName)));
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
}
