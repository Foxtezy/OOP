package ru.nsu.fit.makhov;

import org.junit.jupiter.api.BeforeEach;
import ru.nsu.fit.makhov.parallelstream.ParallelStreamPrimeChecker;

class ParallelStreamTest extends PrimeCheckerTest {

  @BeforeEach
  public void setupTests() {
    super.primeChecker = new ParallelStreamPrimeChecker();
  }
}
