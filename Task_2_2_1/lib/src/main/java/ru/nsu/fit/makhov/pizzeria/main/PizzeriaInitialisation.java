package ru.nsu.fit.makhov.pizzeria.main;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import ru.nsu.fit.makhov.pizzeria.logger.OrderLogger;
import ru.nsu.fit.makhov.pizzeria.managers.BakeryManager;
import ru.nsu.fit.makhov.pizzeria.managers.DeliveryManager;
import ru.nsu.fit.makhov.pizzeria.managers.Manager;
import ru.nsu.fit.makhov.pizzeria.managers.PizzeriaManager;
import ru.nsu.fit.makhov.pizzeria.order.Order;
import ru.nsu.fit.makhov.pizzeria.orderprocessor.OrderProcessor;
import ru.nsu.fit.makhov.pizzeria.orderprocessor.OrderProcessorImpl;

/**
 * A class that initializes a pizzeria and creates a PizzeriaManager to start a pizzeria and an
 * OrderProcessor to send an order.
 */
public class PizzeriaInitialisation {

  private final Manager pizzeriaManager;

  private final OrderProcessor orderProcessor;

  /**
   * Init pizzeria.
   *
   * @param inputQueueSize size of queue of new orders.
   * @param storeQueueSize size of store.
   */
  public PizzeriaInitialisation(int inputQueueSize, int storeQueueSize) {
    BlockingQueue<Order> inputQueue = new LinkedBlockingQueue<>(inputQueueSize);
    BlockingQueue<Order> storeQueue = new LinkedBlockingQueue<>(storeQueueSize);
    OrderLogger orderLogger = new OrderLogger(new LinkedBlockingQueue<>(100));
    Manager bakeryManager = new BakeryManager(inputQueue, storeQueue);
    Manager deliveryManager = new DeliveryManager(storeQueue, orderLogger);
    pizzeriaManager = new PizzeriaManager(bakeryManager, deliveryManager);
    orderProcessor = new OrderProcessorImpl(inputQueue);
  }

  public Manager getPizzeriaManager() {
    return pizzeriaManager;
  }

  public OrderProcessor getOrderProcessor() {
    return orderProcessor;
  }
}
