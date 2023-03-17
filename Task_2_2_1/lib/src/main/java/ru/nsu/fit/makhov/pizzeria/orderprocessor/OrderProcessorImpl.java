package ru.nsu.fit.makhov.pizzeria.orderprocessor;

import java.util.concurrent.BlockingQueue;
import ru.nsu.fit.makhov.pizzeria.order.Order;

/**
 * Processor that add pizza order to input queue.
 */
public class OrderProcessorImpl implements OrderProcessor {

  private final BlockingQueue<Order> inputQueue;

  public OrderProcessorImpl(BlockingQueue<Order> inputQueue) {
    this.inputQueue = inputQueue;
  }

  @Override
  public void addNewOrder(Order pizzaOrder) {
    try {
      inputQueue.put(pizzaOrder);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
