package ru.nsu.fit.makhov.graph;

import org.junit.jupiter.api.Test;

class AdjacencyMatrixGraphTests {

  @Test
  public void sampleTest() {
    Graph<String> graph = new AdjacencyMatrixGraph<>();
    graph.addVertex("A");
    graph.addVertex("B");
    graph.addVertex("AB");
    graph.addVertex("BB");
    graph.addEdge(new Edge<>("A", "B", 10.0));
    graph.addEdge(new Edge<>("B", "AB", 10.0));
    graph.addEdge(new Edge<>("B", "BB", 10.0));
    graph.addEdge(new Edge<>("AB", "BB", 10.0));
  }
}
