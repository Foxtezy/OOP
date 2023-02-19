package ru.nsu.fit.makhov.mythreadpool;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Task manager that executes callable tasks.
 *
 * @param <T> Return values of Callable Tasks.
 */
public class TaskManager<T> implements Runnable {

  private final List<Thread> threadList = new ArrayList<>();

  private final List<BlockingQueue<Callable<T>>> inputQueues = new ArrayList<>();

  private final List<List<T>> outputLists = new ArrayList<>();

  private final Queue<Callable<T>> mainQueue;

  private final List<T> resultList;

  private final Object monitor = new Object();

  /**
   * Constructor.
   *
   * @param countOfThreads the number of threads in which Callable tasks are executed.
   * @param sizeOfQueues   Queue size of each thread.
   */
  public TaskManager(List<Callable<T>> tasks, List<T> resultList, int countOfThreads,
      int sizeOfQueues) {
    this.resultList = resultList;
    mainQueue = new ArrayDeque<>(tasks);
    for (int i = 0; i < countOfThreads; i++) {
      BlockingQueue<Callable<T>> inputQueue = new LinkedBlockingQueue<>(sizeOfQueues);
      List<T> outputList = new ArrayList<>();
      inputQueues.add(inputQueue);
      outputLists.add(outputList);
      Thread thread = new Thread(new TaskWorker<>(inputQueue, outputList, monitor));
      threadList.add(thread);
      thread.start();
    }
  }

  private void fillQueues() {
    while (!mainQueue.isEmpty()) {
      boolean fullQueues = true;
      for (BlockingQueue<Callable<T>> inputQueue : inputQueues) {
        if (inputQueue.remainingCapacity() > 0 && !mainQueue.isEmpty()) {
          inputQueue.add(mainQueue.poll());
          fullQueues = false;
        }
      }
      if (fullQueues) {
        return;
      }
    }
  }


  @Override
  public void run() {
    fillQueues();
    synchronized (monitor) {
      monitor.notifyAll();
    }
    try {
      while (!mainQueue.isEmpty()) {
        synchronized (monitor) {
          monitor.wait();
        }
        fillQueues();
      }
      threadList.forEach(Thread::interrupt);
      for (Thread thread : threadList) {
        thread.join();
      }
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    outputLists.forEach(resultList::addAll);
  }
}
