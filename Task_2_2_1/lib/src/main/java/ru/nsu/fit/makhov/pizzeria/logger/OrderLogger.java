package ru.nsu.fit.makhov.pizzeria.logger;

import java.util.concurrent.BlockingQueue;
import ru.nsu.fit.makhov.pizzeria.order.Order;

/**
 * Logger that can log order message.
 */
public class OrderLogger implements Runnable {

  private final BlockingQueue<OrderMessage> logQueue;

  public OrderLogger(BlockingQueue<OrderMessage> logQueue) {
    this.logQueue = logQueue;
  }

  public void log(Order order) throws InterruptedException {
    logQueue.put(new OrderMessage(order));
  }


  @Override
  public void run() {
    while (!Thread.currentThread().isInterrupted() || !logQueue.isEmpty()) {
      try {
        OrderMessage orderMessage = logQueue.take();
        System.out.println(orderMessage);
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
      }
    }
  }
}
