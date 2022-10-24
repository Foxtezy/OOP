package ru.nsu.fit.makhov.graph;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GraphTests {

  Graph<String> adjacencyMatrixGraph = new AdjacencyMatrixGraph<>();
  Graph<String> adjacencyListGraph = new AdjacencyListGraph<>();


  @BeforeEach
  public void setup() throws FileNotFoundException {
    init(adjacencyMatrixGraph);
    init(adjacencyListGraph);
  }

  private void init(Graph<String> graph) {
    File file = new File("src/test/resources/TestGraph.txt");
    try (Scanner scanner = new Scanner(file);) {
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
    } catch (IOException e) {
      e.printStackTrace();
    }
  }


  @Test
  public void sortTest() {
    System.out.println(adjacencyListGraph.sort("C"));
    System.out.println(adjacencyMatrixGraph.sort("C"));
  }
}
