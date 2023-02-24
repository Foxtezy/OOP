package ru.nsu.fit.makhov.pizzeria.workers;

import java.util.concurrent.BlockingQueue;
import ru.nsu.fit.makhov.pizzeria.order.OrderStatus;
import ru.nsu.fit.makhov.pizzeria.order.PizzaOrder;

public class BakeryWorker implements Runnable {

  private final long cookingTimeMs;

  private BlockingQueue<PizzaOrder> inputQueue;

  private BlockingQueue<PizzaOrder> outputQueue;

  public BakeryWorker(long cookingTimeMs) {
    this.cookingTimeMs = cookingTimeMs;
  }

  public void setInputQueue(
      BlockingQueue<PizzaOrder> inputQueue) {
    this.inputQueue = inputQueue;
  }

  public void setOutputQueue(
      BlockingQueue<PizzaOrder> outputQueue) {
    this.outputQueue = outputQueue;
  }

  @Override
  public void run() {
    try {
      while (true) {
        PizzaOrder nextTask = inputQueue.take();
        nextTask.setOrderStatus(OrderStatus.COOKING);
        Thread.sleep(cookingTimeMs);
        nextTask.setOrderStatus(OrderStatus.WAITING_FOR_DELIVERY);
        outputQueue.put(nextTask);
      }
    } catch (InterruptedException ignored) {}
  }
}
