package ru.nsu.fit.makhov.graph;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import ru.nsu.fit.makhov.graph.utils.Pair;

@Disabled
public abstract class GraphTests {

  protected Graph<String> graph;

  private HashMap<String, Vertex<String>> initVertexes;

  protected void setup() {
    initVertexes = new HashMap<>();
    File file = new File("src/test/resources/TestGraph.txt");
    try (Scanner scanner = new Scanner(file)) {
      int countVertex = scanner.nextInt();
      int countEdges = scanner.nextInt();
      for (int i = 0; i < countVertex; i++) {
        String name = scanner.next();
        initVertexes.put(name, graph.addVertex(name));
      }
      for (int i = 0; i < countEdges; i++) {
        String departure = scanner.next();
        String destination = scanner.next();
        Double weight = scanner.nextDouble();
        graph.addEdge(
            new Edge<>(initVertexes.get(departure), initVertexes.get(destination), weight));
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Test
  protected void removeVertexTest() {
    graph.removeVertex(initVertexes.get("E"));
    Assertions.assertEquals(
        Arrays.asList(new Pair<>(initVertexes.get("C"), 0.0),
            new Pair<>(initVertexes.get("D"), 2.0), new Pair<>(initVertexes.get("F"), 5.0),
            new Pair<>(initVertexes.get("B"), 10.0), new Pair<>(initVertexes.get("G"), 10.0),
            new Pair<>(initVertexes.get("A"), 14.0)), graph.sort(initVertexes.get("C")));
  }

  @Test
  protected void removeEdgeTest() {
    graph.removeEdge(new Edge<>(initVertexes.get("C"), initVertexes.get("D")));
    Assertions.assertEquals(
        Arrays.asList(new Pair<>(initVertexes.get("C"), 0.0),
            new Pair<>(initVertexes.get("E"), 4.0), new Pair<>(initVertexes.get("F"), 5.0),
            new Pair<>(initVertexes.get("G"), 9.0), new Pair<>(initVertexes.get("A"), 34.0),
            new Pair<>(initVertexes.get("B"), 39.0),
            new Pair<>(initVertexes.get("D"), 46.0)), graph.sort(initVertexes.get("C")));
  }

  @Test
  protected void getEdgeTest() {
    Edge<String> edge = new Edge<>(initVertexes.get("E"), initVertexes.get("C"), 4.0);
    Assertions.assertEquals(edge,
        graph.getEdge(new Edge<>(initVertexes.get("E"), initVertexes.get("C"))));
  }

  @Test
  protected void getVertexTest() {
    Set<Vertex<String>> vertexAdjSet = Set.of(initVertexes.get("A"), initVertexes.get("D"));
    Assertions.assertEquals(vertexAdjSet, graph.getVertexAdjSet(initVertexes.get("B")));
  }

  @Test
  protected void editVertexTest() {
    Vertex<String> v = graph.editVertex(initVertexes.get("A"), "ABBA");
    Assertions.assertEquals(
        Arrays.asList(new Pair<>(initVertexes.get("C"), 0.0),
            new Pair<>(initVertexes.get("D"), 2.0), new Pair<>(initVertexes.get("E"), 4.0),
            new Pair<>(initVertexes.get("F"), 5.0), new Pair<>(initVertexes.get("G"), 9.0),
            new Pair<>(initVertexes.get("B"), 10.0),
            new Pair<>(v, 14.0)), graph.sort(initVertexes.get("C")));
  }

  @Test
  protected void sortTest() {
    Assertions.assertEquals(
        Arrays.asList(new Pair<>(initVertexes.get("C"), 0.0),
            new Pair<>(initVertexes.get("D"), 2.0), new Pair<>(initVertexes.get("E"), 4.0),
            new Pair<>(initVertexes.get("F"), 5.0), new Pair<>(initVertexes.get("G"), 9.0),
            new Pair<>(initVertexes.get("B"), 10.0),
            new Pair<>(initVertexes.get("A"), 14.0)), graph.sort(initVertexes.get("C")));
  }

  @Test
  protected void addExistVertexTest() {
    Vertex<String> v = graph.addVertex("A");
    graph.addEdge(new Edge<>(initVertexes.get("A"), v, 100.0));
    Assertions.assertEquals(
        Arrays.asList(new Pair<>(initVertexes.get("C"), 0.0),
            new Pair<>(initVertexes.get("D"), 2.0), new Pair<>(initVertexes.get("E"), 4.0),
            new Pair<>(initVertexes.get("F"), 5.0), new Pair<>(initVertexes.get("G"), 9.0),
            new Pair<>(initVertexes.get("B"), 10.0),
            new Pair<>(initVertexes.get("A"), 14.0), new Pair<>(v, 114.0)),
        graph.sort(initVertexes.get("C")));
  }

}
