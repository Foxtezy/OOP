package ru.nsu.fit.makhov.mythreadpool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Thread pool that execute Callable tasks.
 *
 * @param <T> Return values of Callable Tasks.
 */
public class ThreadPool<T> {

  private static final int SIZE_OF_QUEUES = 1000;

  private final Thread taskManager;

  private final List<T> resultList = new ArrayList<>();

  public ThreadPool(List<Callable<T>> tasks, int countOfThreads) {
    this(tasks, countOfThreads, SIZE_OF_QUEUES);
  }

  public ThreadPool(List<Callable<T>> tasks, int countOfThreads, int sizeOfQueues) {
    taskManager = new Thread(new TaskManager<>(tasks, resultList, countOfThreads, sizeOfQueues));
  }

  /**
   * Run tasks.
   */
  public void invokeAll() {
    taskManager.start();
  }

  /**
   * Get results of tasks.
   *
   * @return list of results.
   */
  public List<T> getResult() {
    try {
      taskManager.join();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    return resultList;
  }
}
