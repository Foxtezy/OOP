/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package ru.nsu.fit.makhov;

import org.junit.jupiter.api.BeforeEach;
import ru.nsu.fit.makhov.multithread.MultithreadPrimeChecker;

class MultithreadingTest extends PrimeCheckerTest {

  @BeforeEach
  public void setupTests() {
    super.primeChecker = new MultithreadPrimeChecker();
  }
}
