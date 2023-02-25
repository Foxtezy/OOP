package ru.nsu.fit.makhov.pizzeria.order;

public class PizzaOrder extends Order {

  private final long deliveryTimeMs;

  public PizzaOrder(long orderId, long deliveryTimeMs) {
    super(orderId);
    this.deliveryTimeMs = deliveryTimeMs;
    setOrderStatus(OrderStatus.WAITING_FOR_COOKING);
  }

  public long getDeliveryTimeMs() {
    return deliveryTimeMs;
  }
}
