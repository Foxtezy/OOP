package ru.nsu.fit.makhov;


public class IsPrime {

  public static Boolean apply(Integer num) {
    for (int i = 2; i < Math.sqrt(num) + 1; i++) {
      if (num % i == 0) {
        return false;
      }
    }
    return true;
  }
}
