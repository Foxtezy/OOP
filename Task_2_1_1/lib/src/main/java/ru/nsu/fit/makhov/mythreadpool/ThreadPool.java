package ru.nsu.fit.makhov.mythreadpool;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * My thread pool receiving Callable.
 *
 * @param <T> return type of Callable.
 */
public class ThreadPool<T> {

  ConcurrentLinkedQueue<Callable<T>> tasksQueue = new ConcurrentLinkedQueue<>();
  CopyOnWriteArrayList<T> results = new CopyOnWriteArrayList<>();
  List<Thread> threadList = new ArrayList<>();
  private volatile boolean shutdown = false;

  /**
   * Constructor.
   *
   * @param countOfThreads count of threads.
   */
  public ThreadPool(int countOfThreads) {
    for (int i = 0; i < countOfThreads; i++) {
      Thread thread = new Thread(new TaskWorker());
      thread.start();
      threadList.add(thread);
    }
  }

  public void invoke(Callable<T> task) {
    tasksQueue.add(task);
  }

  public void invokeAll(Collection<Callable<T>> tasks) {
    tasksQueue.addAll(tasks);
  }

  /**
   * Waits for all threads and returns the results of the work.
   *
   * @return result list.
   */
  public List<T> shutdown() {
    shutdown = true;
    threadList.forEach(t -> {
      try {
        t.join();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    });
    return results;
  }

  public List<T> shutdownNow() {
    threadList.forEach(Thread::interrupt);
    return results;
  }

  private final class TaskWorker implements Runnable {

    @Override
    public void run() {
      while (!Thread.interrupted()) {
        Callable<T> nextTask = tasksQueue.poll();
        if (nextTask != null) {
          try {
            T result = nextTask.call();
            results.add(result);
          } catch (Exception e) {
            e.printStackTrace();
          }
        } else {
          if (shutdown) {
            return;
          }
        }
      }
    }
  }
}
