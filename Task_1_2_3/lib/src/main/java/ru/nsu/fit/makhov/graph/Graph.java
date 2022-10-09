package ru.nsu.fit.makhov.graph;

import java.util.List;

public interface Graph<T> {

  void addVertex(T name);

  void editVertex(T oldName, T newName);

  default void removeVertex(T name) {
    throw new UnsupportedOperationException("remove");
  }

  Vertex<T> getVertex(T name);

  void addEdge(Edge<T> newEdge);

  void removeEdge(Edge<T> edge);

  Edge<T> getEdge(Edge<T> edge);

}
