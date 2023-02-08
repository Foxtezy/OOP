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
import ru.nsu.fit.makhov.utils.Timer;

public class MultithreadPrimeChecker implements PrimeChecker {

  @Override
  public boolean isPrime(List<Integer> nums) {
    ExecutorService executor = Executors.newFixedThreadPool(10);
    List<Callable<Boolean>> tasks = nums.stream().map(IsPrimeTask::new)
        .collect(Collectors.toList());
    List<Future<Boolean>> results = new ArrayList<>();
    try {
      long start = Timer.startTimer();
      results = executor.invokeAll(tasks);
      System.out.println(Timer.stopTimer(start));
    } catch (Exception e) {
      e.printStackTrace();
    }
    return results.stream().map(f -> Try.call(f::get))
        .map(Try::toOptional)
        .filter(Optional::isPresent)
        .map(Optional::get)
        .anyMatch(r -> r == false);
  }
}
