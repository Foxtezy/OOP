package ru.nsu.fit.makhov;

import java.util.function.Predicate;

public class IsPrime implements Predicate<Long> {

  @Override
  public boolean test(Long num) {
    for (int i = 2; i < Math.sqrt(num) + 1; i++) {
      if (num % i == 0) {
        return false;
      }
    }
    return true;
  }
}
