package ru.nsu.fit.makhov.multithread;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import ru.nsu.fit.makhov.PrimeChecker;
import org.junit.platform.commons.function.Try;

public class MultithreadPrimeChecker implements PrimeChecker {

  private final int countOfThreads;

  public MultithreadPrimeChecker(int countOfThreads) {
    this.countOfThreads = countOfThreads;
  }

  @Override
  public boolean isPrime(List<Integer> nums) {
    ExecutorService executor = Executors.newFixedThreadPool(countOfThreads);
    List<Callable<Boolean>> tasks = nums.stream().map(IsPrimeTask::new)
        .collect(Collectors.toList());
    List<Future<Boolean>> results = new ArrayList<>();
    try {
      results = executor.invokeAll(tasks);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return results.stream().map(f -> Try.call(f::get))
        .map(Try::toOptional)
        .filter(Optional::isPresent)
        .map(Optional::get)
        .anyMatch(r -> !r);
  }
}
