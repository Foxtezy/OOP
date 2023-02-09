package ru.nsu.fit.makhov.parallelstream;

import java.util.List;
import ru.nsu.fit.makhov.IsPrime;
import ru.nsu.fit.makhov.PrimeChecker;

public class ParallelStreamPrimeChecker implements PrimeChecker {

  @Override
  public boolean isPrime(List<Integer> nums) {
    List<Boolean> list = nums.parallelStream().map(IsPrime::apply).toList();
    return list.stream().anyMatch(r -> !r);
  }
}
