package ru.nsu.fit.makhov.graph;

import org.junit.jupiter.api.BeforeEach;

class AdjacencyListGraphTests extends GraphTests {

  @BeforeEach
  public void setupTests() {
    super.graph = new AdjacencyListGraph<>();
    setup();
  }

}
