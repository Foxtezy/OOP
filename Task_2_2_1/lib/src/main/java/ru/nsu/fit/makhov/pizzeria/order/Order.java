package ru.nsu.fit.makhov.pizzeria.order;

/**
 * Abstract order that have an id and status.
 */
public abstract class Order {

  private final long orderId;

  private OrderStatus orderStatus;

  public Order(long orderId, OrderStatus orderStatus) {
    this.orderId = orderId;
    this.orderStatus = orderStatus;
  }

  public void setOrderStatus(OrderStatus orderStatus) {
    this.orderStatus = orderStatus;
  }

  @Override
  public String toString() {
    return "Order{" + "orderId=" + orderId + ", orderStatus=" + orderStatus + '}';
  }
}
