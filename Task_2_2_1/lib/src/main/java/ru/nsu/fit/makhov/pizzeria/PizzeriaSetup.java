package ru.nsu.fit.makhov.pizzeria;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import ru.nsu.fit.makhov.pizzeria.managers.BakeryManager;
import ru.nsu.fit.makhov.pizzeria.managers.DeliveryManager;
import ru.nsu.fit.makhov.pizzeria.order.PizzaOrder;

public class PizzeriaSetup {

  private final int inputQueueSize;

  private final int storeQueueSize;


  public PizzeriaSetup(int inputQueueSize, int storeQueueSize) {
    this.inputQueueSize = inputQueueSize;
    this.storeQueueSize = storeQueueSize;
  }

  public PizzeriaManager setup() {
    BlockingQueue<PizzaOrder> inputQueue = new LinkedBlockingQueue<>(inputQueueSize);
    BlockingQueue<PizzaOrder> storeQueue = new LinkedBlockingQueue<>(storeQueueSize);
    BakeryManager bakeryManager = new BakeryManager(inputQueue, storeQueue);
    DeliveryManager deliveryManager = new DeliveryManager(storeQueue);
    return new PizzeriaManager(bakeryManager, deliveryManager, inputQueue);
  }
}
