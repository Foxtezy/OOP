package ru.nsu.fit.makhov;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
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
    /*List<Integer> list = new Random().ints().boxed().filter(n -> n > 0)
        .filter(IsPrime::apply).limit(100_000).collect(Collectors.toList());
    try (Writer writer = new FileWriter(new File("src/test/resources/nums.txt"))){
      for (Integer n : list) {
        writer.write(n.toString() + " ");
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    } */
    List<Integer> list = new ArrayList<>(100_000);
    try (Scanner reader = new Scanner(new File("src/test/resources/nums.txt"))) {
      while (reader.hasNextInt()) {
        list.add(reader.nextInt());
      }
    } catch (FileNotFoundException e) {
      throw new RuntimeException();
    }
    long start = Timer.startTimer();
    boolean res = primeChecker.isPrime(list);
    System.out.println("TIME " + primeChecker.getClass().getName() + ": " + Timer.stopTimer(start) + " ms");
    System.out.println("Result: " + res);
  }
}
