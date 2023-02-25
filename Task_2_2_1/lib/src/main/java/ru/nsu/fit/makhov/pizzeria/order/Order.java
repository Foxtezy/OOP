package ru.nsu.fit.makhov.pizzeria.order;

public abstract class Order {

  public Order(long orderId) {
    this.orderId = orderId;
  }

  private final long orderId;

  private OrderStatus orderStatus;

  public OrderStatus getOrderStatus() {
    return orderStatus;
  }

  public long getOrderId() {
    return orderId;
  }

  public void setOrderStatus(OrderStatus orderStatus) {
    this.orderStatus = orderStatus;
  }
}
