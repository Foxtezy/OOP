package ru.nsu.fit.makhov.graph;

import java.util.List;
import java.util.Map;

public interface Graph<T> {

  void addVertex(T name);

  void editVertex(T oldName, T newName);

  void removeVertex(T name);

  Vertex<T> getVertex(T name);

  boolean isEdgeExist(Edge<T> edge);

  void addEdge(Edge<T> newEdge);

  void removeEdge(Edge<T> edge);

  Edge<T> getEdge(Edge<T> edge);

  List<Pair<T, Double>> sort(T src);

}
