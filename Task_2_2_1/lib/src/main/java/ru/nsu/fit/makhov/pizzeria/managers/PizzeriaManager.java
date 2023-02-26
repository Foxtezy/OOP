package ru.nsu.fit.makhov.pizzeria.managers;

/**
 * Manager which starts and stops Bakery and Delivery managers.
 */
public class PizzeriaManager implements Manager {

  private final Manager bakeryManager;

  private final Manager deliveryManager;


  public PizzeriaManager(Manager bakeryManager, Manager deliveryManager) {
    this.bakeryManager = bakeryManager;
    this.deliveryManager = deliveryManager;
  }

  @Override
  public void start() {
    bakeryManager.start();
    deliveryManager.start();
  }

  @Override
  public void stop() {
    bakeryManager.stop();
    deliveryManager.stop();
  }
}
