package ru.nsu.fit.makhov.mythreadpool;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;

public class TaskManager<T> {

  public static final int SIZE_OF_QUEUES = 1000;

  private final List<Thread> threadList = new ArrayList<>();

  private final List<BlockingQueue<Callable<T>>> inputQueues = new ArrayList<>();

  private final List<List<T>> outputLists = new ArrayList<>();

  private Queue<Callable<T>> mainQueue;

  private final Object monitor = new Object();

  public TaskManager(int countOfThreads) {
    for (int i = 0; i < countOfThreads; i++) {
      BlockingQueue<Callable<T>> inputQueue = new ArrayBlockingQueue<>(SIZE_OF_QUEUES);
      List<T> outputList = new ArrayList<>();
      inputQueues.add(inputQueue);
      outputLists.add(outputList);
      Thread thread = new Thread(new TaskWorker<>(inputQueue, outputList, monitor));
      threadList.add(thread);
      thread.start();
    }
  }

  public TaskManager(int countOfThreads, int sizeOfQueues) {
    for (int i = 0; i < countOfThreads; i++) {
      BlockingQueue<Callable<T>> inputQueue = new ArrayBlockingQueue<>(sizeOfQueues);
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
      for (BlockingQueue<Callable<T>> inputQueue : inputQueues) {
        if (inputQueue.remainingCapacity() > 0 && !mainQueue.isEmpty()) {
          inputQueue.add(mainQueue.poll());
        } else {
          return;
        }
      }
    }
  }

  public List<T> invokeAll(List<Callable<T>> tasks) {
    mainQueue = new ArrayDeque<>(tasks);
    fillQueues();
    synchronized (monitor) {
      monitor.notifyAll();
    }
    try {
      while (!mainQueue.isEmpty()) {
        synchronized (monitor) {
          monitor.wait(1000);
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
    List<T> res = new ArrayList<>();
    outputLists.forEach(res::addAll);
    return res;
  }
}
