package ru.nsu.fit.makhov.pizzeria.workers;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import ru.nsu.fit.makhov.pizzeria.order.Order;
import ru.nsu.fit.makhov.pizzeria.order.OrderStatus;
import ru.nsu.fit.makhov.pizzeria.order.PizzaOrder;

/**
 * Class that represents runnable delivery worker.
 */
public class DeliveryWorker implements Runnable {

  private final int trunkSize;

  private volatile boolean isWorking;

  private BlockingQueue<Order> storeQueue;

  public DeliveryWorker(int trunkSize) {
    this.trunkSize = trunkSize;
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
        if (storeQueue.isEmpty() && !isWorking) {
          return;
        }
        List<Order> inputTasks = new ArrayList<>();
        inputTasks.add(storeQueue.take());
        Order nextTask = storeQueue.poll();
        for (int i = 1; i < trunkSize && nextTask != null; i++) {
          inputTasks.add(nextTask);
          nextTask = storeQueue.poll();
        }
        for (Order order : inputTasks) {
          if (order instanceof PizzaOrder pizzaOrder) {
            order.setOrderStatus(OrderStatus.IN_DELIVERY);
            Thread.sleep(pizzaOrder.getDeliveryTimeMs());
            order.setOrderStatus(OrderStatus.DELIVERED);
            System.out.printf("[%d], [%s]\n", order.getOrderId(), order.getOrderStatus());
          }
        }
      }
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
