package ru.nsu.fit.makhov.pizzeria.workers;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import ru.nsu.fit.makhov.pizzeria.order.OrderStatus;
import ru.nsu.fit.makhov.pizzeria.order.PizzaOrder;

public class DeliveryWorker implements Runnable {

  private final int trunkSize;

  private BlockingQueue<PizzaOrder> storeQueue;

  public DeliveryWorker(int trunkSize) {
    this.trunkSize = trunkSize;
  }

  public void setStoreQueue(
      BlockingQueue<PizzaOrder> storeQueue) {
    this.storeQueue = storeQueue;
  }

  @Override
  public void run() {
    try {
      while (true) {
        List<PizzaOrder> inputTasks = new ArrayList<>();
        inputTasks.add(storeQueue.take());
        PizzaOrder nextTask = storeQueue.poll();
        for (int i = 1; i < trunkSize && nextTask != null; i++) {
          inputTasks.add(nextTask);
          nextTask = storeQueue.poll();
        }
        for (PizzaOrder order : inputTasks) {
          order.setOrderStatus(OrderStatus.IN_DELIVERY);
          Thread.sleep(order.getDeliveryTimeMs());
          order.setOrderStatus(OrderStatus.DELIVERED);
          System.out.printf("[%d], [%s]\n", order.getOrderId(), order.getOrderStatus());
        }
      }
    } catch (InterruptedException ignored) {
    }
  }
}
