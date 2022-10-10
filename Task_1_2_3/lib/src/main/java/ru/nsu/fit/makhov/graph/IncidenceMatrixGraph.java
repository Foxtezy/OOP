package ru.nsu.fit.makhov.graph;

import java.util.ArrayList;
import java.util.List;

public class IncidenceMatrixGraph<T> implements Graph<T>{

  public static final double INFINITY = Double.POSITIVE_INFINITY;
  private final List<List<Double>> matrix = new ArrayList<>();
  @Override
  public void addVertex(T name) {

  }

  @Override
  public void editVertex(T oldName, T newName) {

  }

  @Override
  public void removeVertex(T name) {

  }

  @Override
  public Vertex<T> getVertex(T name) {
    return null;
  }

  @Override
  public boolean isEdgeExist(Edge<T> edge) {
    return false;
  }

  @Override
  public void addEdge(Edge<T> newEdge) {

  }

  @Override
  public void removeEdge(Edge<T> edge) {

  }

  @Override
  public Edge<T> getEdge(Edge<T> edge) {
    return null;
  }
}
