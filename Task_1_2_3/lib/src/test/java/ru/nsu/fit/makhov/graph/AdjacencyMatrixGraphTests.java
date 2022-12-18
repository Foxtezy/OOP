package ru.nsu.fit.makhov.graph;

import org.junit.jupiter.api.BeforeEach;

public class AdjacencyMatrixGraphTests extends GraphTests {


  @BeforeEach
  public void setupTests() {
    super.graph = new AdjacencyMatrixGraph<>();
    setup();
  }

}
