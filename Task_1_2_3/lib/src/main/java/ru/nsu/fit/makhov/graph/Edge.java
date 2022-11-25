package ru.nsu.fit.makhov.graph;

import java.util.Objects;

/**
 * Class which represents edge in weighted, oriented graph.
 *
 * @param <T> type of names of vertexes.
 */
public class Edge<T> {

  private final Vertex<T> departure;
  private final Vertex<T> destination;

  private Double weight = Double.POSITIVE_INFINITY;

  public Edge(Vertex<T> departure, Vertex<T> destination) {
    this.departure = departure;
    this.destination = destination;
  }

  /**
   * Constructor with weight.
   *
   * @param departure   departure vertex.
   * @param destination destination vertex.
   * @param weight      weight of the vertex.
   */
  public Edge(Vertex<T> departure, Vertex<T> destination, Double weight) {
    this.departure = departure;
    this.destination = destination;
    this.weight = weight;
  }

  public Vertex<T> getDeparture() {
    return departure;
  }

  public Vertex<T> getDestination() {
    return destination;
  }

  public Double getWeight() {
    return weight;
  }

  public void setWeight(Double weight) {
    this.weight = weight;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Edge<?> edge)) {
      return false;
    }
    return getDeparture().equals(edge.getDeparture()) && getDestination().equals(
        edge.getDestination()) && Objects.equals(getWeight(), edge.getWeight());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getDeparture(), getDestination(), getWeight());
  }
}
