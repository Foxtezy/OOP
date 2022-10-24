package ru.nsu.fit.makhov.graph;

import java.util.List;
import ru.nsu.fit.makhov.graph.utils.Pair;

/**
 * Interface which represents directed, weighted graphs.
 *
 * @param <T> type of names of vertexes
 */
public interface Graph<T> {

  /**
   * Adds new vertex to the graph.
   *
   * @param name name of vertex.
   */
  void addVertex(T name);

  /**
   * Edit name of vertex.
   *
   * @param oldName old name of vertex.
   * @param newName new name of vertex.
   */
  void editVertex(T oldName, T newName);

  /**
   * Remove vertex from the graph.
   *
   * @param name name of vertex.
   */
  void removeVertex(T name);

  /**
   * Get vertex.
   *
   * @param name name of vertex.
   * @return vertex.
   */
  Vertex<T> getVertex(T name);

  /**
   * Returns true if edge exist.
   *
   * @param edge edge.
   * @return is edge exist.
   */
  boolean isEdgeExist(Edge<T> edge);

  /**
   * Adds new edge to the graph.
   *
   * @param newEdge new edge.
   */
  void addEdge(Edge<T> newEdge);

  /**
   * Removes edge from the graph.
   *
   * @param edge edge.
   */
  void removeEdge(Edge<T> edge);

  /**
   * Get edge.
   *
   * @param edge name of edge.
   * @return edge.
   */
  Edge<T> getEdge(Edge<T> edge);

  /**
   * Topological sort of the vertex.
   *
   * @param src vertex relative to which to sort.
   * @return a list of pairs with the name of the vertex and its distance from src.
   */
  List<Pair<T, Double>> sort(T src);

}
