package ru.nsu.fit.makhov.graph;

public interface Graph<T> {

  void addVertex(T name);

  void editVertex(T oldName, T newName);

  void removeVertex(T name);

  Vertex<T> getVertex(T name);

  void addEdge();

  void editEdge();

  void removeEdge();

  void getEdge();

  interface Vertex<T> {
    T getValue();
  }

  interface Edge {

  }

}
