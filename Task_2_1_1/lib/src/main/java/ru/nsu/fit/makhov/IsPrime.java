package ru.nsu.fit.makhov;

/**
 * A class with a static method for checking whether a number is prime.
 */
public class IsPrime {

  /**
   * Checking whether a number is prime.
   *
   * @param num number
   * @return is number prime
   */
  public static Boolean apply(Integer num) {
    for (int i = 2; i < Math.sqrt(num) + 1; i++) {
      if (num % i == 0) {
        return false;
      }
    }
    return true;
  }
}
