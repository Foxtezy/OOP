package ru.nsu.fit.makhov.mythreadpool;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;

public class TaskWorker<T> implements Runnable {

  private final BlockingQueue<Callable<T>> inputQueue;

  private final List<T> outputList;

  private final Object monitor;

  public TaskWorker(BlockingQueue<Callable<T>> inputQueue, List<T> outputList, Object monitor) {
    this.inputQueue = inputQueue;
    this.outputList = outputList;
    this.monitor = monitor;
  }

  @Override
  public void run() {
    try {
      synchronized (monitor) {
        monitor.wait();
      }
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    while (true) {
      Callable<T> nextTask = inputQueue.poll();
      if (nextTask != null) {
        try {
          outputList.add(nextTask.call());
        } catch (Exception e) {
          e.printStackTrace();
        }
      } else {
        if (Thread.interrupted()) {
          return;
        }
        synchronized (monitor) {
          monitor.notify();
        }
      }
    }
  }
}
