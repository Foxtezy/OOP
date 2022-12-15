package ru.nsu.fit.makhov.graph;

import java.util.List;
import java.util.Set;
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
   * @return new vertex.
   */
  Vertex<T> addVertex(T name);

  /**
   * Edit name of vertex.
   *
   * @param vertex  vertex.
   * @param newName new name of vertex.
   * @return new vertex.
   */
  Vertex<T> editVertex(Vertex<T> vertex, T newName);

  /**
   * Remove vertex from the graph.
   *
   * @param vertex vertex.
   */
  void removeVertex(Vertex<T> vertex);

  /**
   * Get adjacency set of vertex.
   *
   * @param vertex vertex.
   * @return adjSet.
   */
  Set<Vertex<T>> getVertexAdjSet(Vertex<T> vertex);

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
   * @return a list of pairs with the vertex and its distance from src.
   */
  List<Pair<Vertex<T>, Double>> sort(Vertex<T> src);

}
