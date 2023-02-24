package ru.nsu.fit.makhov.pizzeria;

import java.util.concurrent.BlockingQueue;
import ru.nsu.fit.makhov.pizzeria.managers.BakeryManager;
import ru.nsu.fit.makhov.pizzeria.managers.DeliveryManager;
import ru.nsu.fit.makhov.pizzeria.order.PizzaOrder;

public class PizzeriaManager {

  private final BakeryManager bakeryManager;

  private final DeliveryManager deliveryManager;

  private final BlockingQueue<PizzaOrder> inputQueue;

  public PizzeriaManager(BakeryManager bakeryManager, DeliveryManager deliveryManager,
      BlockingQueue<PizzaOrder> inputQueue) {
    this.bakeryManager = bakeryManager;
    this.deliveryManager = deliveryManager;
    this.inputQueue = inputQueue;
  }

  public void startPizzeria() {
    bakeryManager.runBakery();
    deliveryManager.runDelivery();
  }

  public void stopPizzeria() {
    bakeryManager.stopBakery();
    deliveryManager.stopDelivery();
  }

  public void addNewOrder(PizzaOrder pizzaOrder) {
    try {
      inputQueue.put(pizzaOrder);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
