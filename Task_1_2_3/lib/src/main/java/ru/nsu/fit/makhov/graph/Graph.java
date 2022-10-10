package ru.nsu.fit.makhov.graph;

import java.util.List;

public interface Graph<T> {

  void addVertex(T name);

  void editVertex(T oldName, T newName);

  void removeVertex(T name);

  Vertex<T> getVertex(T name);

  boolean isEdgeExist(Edge<T> edge);

  void addEdge(Edge<T> newEdge);

  boolean removeEdge(Edge<T> edge);

  Edge<T> getEdge(Edge<T> edge);

}
