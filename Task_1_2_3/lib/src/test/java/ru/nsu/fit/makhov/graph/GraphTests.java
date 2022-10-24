package ru.nsu.fit.makhov.graph;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;
import org.junit.jupiter.api.Assertions;
import ru.nsu.fit.makhov.graph.utils.Pair;

public abstract class GraphTests {

  protected Graph<String> graph;

  protected void setup() {
    File file = new File("src/test/resources/TestGraph.txt");
    try (Scanner scanner = new Scanner(file)) {
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

  protected void removeVertex() {
    graph.removeVertex("E");
    Assertions.assertEquals(
        Arrays.asList(new Pair<>("C", 0.0), new Pair<>("D", 2.0), new Pair<>("F", 5.0),
            new Pair<>("B", 10.0), new Pair<>("G", 10.0), new Pair<>("A", 14.0)), graph.sort("C"));
  }

  protected void removeEdge() {
    graph.removeEdge(new Edge<>("C", "D"));
    Assertions.assertEquals(
        Arrays.asList(new Pair<>("C", 0.0), new Pair<>("E", 4.0), new Pair<>("F", 5.0),
            new Pair<>("G", 9.0), new Pair<>("A", 34.0), new Pair<>("B", 39.0), new Pair<>("D", 46.0)), graph.sort("C"));
  }

  protected void sort() {
    Assertions.assertEquals(
        Arrays.asList(new Pair<>("C", 0.0), new Pair<>("D", 2.0), new Pair<>("E", 4.0),
            new Pair<>("F", 5.0),
            new Pair<>("G", 9.0), new Pair<>("B", 10.0), new Pair<>("A", 14.0)), graph.sort("C"));
  }


}
