package ru.nsu.fit.makhov.graph;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AdjacencyMatrixGraphTests {

  Graph<String> graph;

  @BeforeEach
  public void setup() throws FileNotFoundException {
    graph = new AdjacencyMatrixGraph<>();
    File file = new File("src/test/resources/TestGraph.txt");
    Scanner scanner = new Scanner(file);
    int countVertex = scanner.nextInt();
    int countEdges = scanner.nextInt();
    for (int i = 0; i < countVertex; i++) {
      graph.addVertex(scanner.next());
    }
    for (int i = 0; i < countEdges; i++) {
      String departure = scanner.next();
      String destination = scanner.next();
      Double weight = scanner.nextDouble();
      graph.addEdge(new Edge<>(departure, destination, weight));
    }
  }

  @Test
  public void removeVertexTest() {
    graph.removeVertex("B");
    graph.addVertex("B");
    graph.addEdge(new Edge<>("A", "B", 1.0));
  }
}
