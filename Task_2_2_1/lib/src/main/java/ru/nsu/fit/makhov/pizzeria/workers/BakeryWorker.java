package ru.nsu.fit.makhov.pizzeria.workers;

import java.util.concurrent.BlockingQueue;
import ru.nsu.fit.makhov.pizzeria.order.Order;
import ru.nsu.fit.makhov.pizzeria.order.OrderStatus;

public class BakeryWorker implements Runnable {

  private final long cookingTimeMs;

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

  @Override
  public void run() {
    try {
      while (true) {
        Order nextTask = inputQueue.take();
        nextTask.setOrderStatus(OrderStatus.COOKING);
        Thread.sleep(cookingTimeMs);
        nextTask.setOrderStatus(OrderStatus.WAITING_FOR_DELIVERY);
        storeQueue.put(nextTask);
      }
    } catch (InterruptedException ignored) {
    }
  }
}
