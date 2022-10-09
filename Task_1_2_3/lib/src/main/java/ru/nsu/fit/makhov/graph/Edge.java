package ru.nsu.fit.makhov.graph;

public class Edge<T> {

  private final T departure;
  private final T destination;

  private Double weight = Double.POSITIVE_INFINITY;

  public Edge(T departure, T destination) {
    this.departure = departure;
    this.destination = destination;
  }

  public Edge(T departure, T destination, Double weight) {
    this.departure = departure;
    this.destination = destination;
    this.weight = weight;
  }

  public T getDeparture() {
    return departure;
  }

  public T getDestination() {
    return destination;
  }

  public Double getWeight() {
    return weight;
  }

  public void setWeight(Double weight) {
    this.weight = weight;
  }

}
