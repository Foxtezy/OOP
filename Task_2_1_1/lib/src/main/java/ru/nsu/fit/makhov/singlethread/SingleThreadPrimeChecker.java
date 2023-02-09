package ru.nsu.fit.makhov.singlethread;

import java.util.List;
import ru.nsu.fit.makhov.IsPrime;
import ru.nsu.fit.makhov.PrimeChecker;

public class SingleThreadPrimeChecker implements PrimeChecker {

  @Override
  public boolean isPrime(List<Integer> nums) {
    List<Boolean> list = nums.stream().map(IsPrime::apply).toList();
    return list.stream().anyMatch(r -> !r);
  }
}
