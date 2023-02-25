package ru.nsu.fit.makhov.pizzeria.main;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import ru.nsu.fit.makhov.pizzeria.managers.BakeryManager;
import ru.nsu.fit.makhov.pizzeria.managers.DeliveryManager;
import ru.nsu.fit.makhov.pizzeria.managers.Manager;
import ru.nsu.fit.makhov.pizzeria.managers.PizzeriaManager;
import ru.nsu.fit.makhov.pizzeria.order.Order;
import ru.nsu.fit.makhov.pizzeria.orderprocessor.OrderProcessor;
import ru.nsu.fit.makhov.pizzeria.orderprocessor.OrderProcessorImpl;

public class PizzeriaStarter {

  private final Manager pizzeriaManager;

  private final OrderProcessor orderProcessor;

  public PizzeriaStarter(int inputQueueSize, int storeQueueSize) {
    BlockingQueue<Order> inputQueue = new LinkedBlockingQueue<>(inputQueueSize);
    BlockingQueue<Order> storeQueue = new LinkedBlockingQueue<>(storeQueueSize);
    BakeryManager bakeryManager = new BakeryManager(inputQueue, storeQueue);
    DeliveryManager deliveryManager = new DeliveryManager(storeQueue);
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
