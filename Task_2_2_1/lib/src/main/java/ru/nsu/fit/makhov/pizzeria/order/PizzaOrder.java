package ru.nsu.fit.makhov.pizzeria.order;

public class PizzaOrder {

  private final long orderId;
  private OrderStatus orderStatus = OrderStatus.WAITING_FOR_COOKING;
  private final long deliveryTimeMs;

  public PizzaOrder(long orderId, long deliveryTimeMs) {
    this.orderId = orderId;
    this.deliveryTimeMs = deliveryTimeMs;
  }

  public long getOrderId() {
    return orderId;
  }

  public OrderStatus getOrderStatus() {
    return orderStatus;
  }

  public long getDeliveryTimeMs() {
    return deliveryTimeMs;
  }

  public void setOrderStatus(OrderStatus orderStatus) {
    this.orderStatus = orderStatus;
  }
}
