package ru.nsu.fit.makhov;

import java.util.List;
import java.util.Random;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import ru.nsu.fit.makhov.utils.Timer;

@Disabled
public abstract class PrimeCheckerTest {

  protected PrimeChecker primeChecker;

  @Test
  protected void simpleTest1() {
    Assertions.assertTrue(primeChecker.isPrime(List.of(6, 8, 7, 13, 9, 4)));
  }

  @Test
  protected void simpleTest2() {
    Assertions.assertFalse(primeChecker.isPrime(
        List.of(6997901, 6997927, 6997937, 6997967, 6998009, 6998029, 6998039, 6998051, 6998053)));
  }

  @Test
  protected void timeTest() {
    List<Integer> list = new Random().ints().map(Math::abs)
        .limit(100000).boxed().toList();
    long start = Timer.startTimer();
    primeChecker.isPrime(list);
    System.out.println("TIME " + primeChecker.getClass().getName() + ": " + Timer.stopTimer(start) + " ms");
  }
}
