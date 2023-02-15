package ru.nsu.fit.makhov.mythreadpool;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;
import ru.nsu.fit.makhov.PrimeChecker;
import ru.nsu.fit.makhov.multithread.IsPrimeTask;

/**
 * Class that checks whether there are composite numbers in the list using my thread pool.
 */
public class MyThreadPoolPrimeChecker implements PrimeChecker {

  private final int countOfThreads;

  public MyThreadPoolPrimeChecker(int countOfThreads) {
    this.countOfThreads = countOfThreads;
  }

  @Override
  public boolean isPrime(List<Integer> nums) {
    TaskManager<Boolean> threadPool = new TaskManager<>(countOfThreads);
    List<Callable<Boolean>> tasks = nums.stream().map(IsPrimeTask::new)
        .collect(Collectors.toList());
    List<Boolean> results = threadPool.invokeAll(tasks);
    return results.stream().anyMatch(r -> !r);
  }
}
