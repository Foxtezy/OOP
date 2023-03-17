package ru.nsu.fit.makhov.pizzeria.order;

/**
 * Pizza order that have time of delivery.
 */
public class PizzaOrder extends Order {

  private final long deliveryTimeMs;

  public PizzaOrder(long orderId, long deliveryTimeMs) {
    super(orderId, OrderStatus.WAITING_FOR_COOKING);
    this.deliveryTimeMs = deliveryTimeMs;
  }

  public long getDeliveryTimeMs() {
    return deliveryTimeMs;
  }
}
