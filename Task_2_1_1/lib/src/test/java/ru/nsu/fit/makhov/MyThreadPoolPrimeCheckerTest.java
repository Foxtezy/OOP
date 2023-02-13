package ru.nsu.fit.makhov;

import org.junit.jupiter.api.BeforeEach;
import ru.nsu.fit.makhov.mythreadpool.MyThreadPoolPrimeChecker;

public class MyThreadPoolPrimeCheckerTest extends PrimeCheckerTest {

  @BeforeEach
  public void setupTests() {
    super.primeChecker = new MyThreadPoolPrimeChecker(8);
  }
}
