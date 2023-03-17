package ru.nsu.fit.makhov.pizzeria.workers;

import java.util.concurrent.BlockingQueue;
import ru.nsu.fit.makhov.pizzeria.order.Order;
import ru.nsu.fit.makhov.pizzeria.order.OrderStatus;

/**
 * Class that represents runnable bakery worker.
 */
public class BakeryWorker implements Runnable {

  private final long cookingTimeMs;

  private volatile boolean isWorking;

  private BlockingQueue<Order> inputQueue;

  private BlockingQueue<Order> storeQueue;

  public BakeryWorker(long cookingTimeMs) {
    this.cookingTimeMs = cookingTimeMs;
  }

  public void setInputQueue(BlockingQueue<Order> inputQueue) {
    this.inputQueue = inputQueue;
  }

  public void setStoreQueue(BlockingQueue<Order> storeQueue) {
    this.storeQueue = storeQueue;
  }

  public void setWorking(boolean working) {
    isWorking = working;
  }

  @Override
  public void run() {
    try {
      while (true) {
        if (inputQueue.isEmpty() && !isWorking) {
          return;
        }
        Order nextTask = inputQueue.take();
        nextTask.setOrderStatus(OrderStatus.COOKING);
        Thread.sleep(cookingTimeMs);
        nextTask.setOrderStatus(OrderStatus.WAITING_FOR_DELIVERY);
        storeQueue.put(nextTask);
      }
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
