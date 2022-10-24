package ru.nsu.fit.makhov.graph;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class IncidenceMatrixGraphTests extends GraphTests {

  @BeforeEach
  public void setupTests() {
    super.graph = new IncidenceMatrixGraph<>();
    setup();
  }

  @Test
  public void removeVertexTest() {
    removeVertex();
  }

  @Test
  public void sortTest() {
    sort();
  }

  @Test
  public void removeEdgeTest() {
    removeEdge();
  }
}