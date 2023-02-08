package ru.nsu.fit.makhov;

import org.junit.jupiter.api.BeforeEach;
import ru.nsu.fit.makhov.singlethread.SingleThreadPrimeChecker;

public class SingleThreadTest extends PrimeCheckerTest {

  @BeforeEach
  public void setupTests() {
    super.primeChecker = new SingleThreadPrimeChecker();
  }
}
