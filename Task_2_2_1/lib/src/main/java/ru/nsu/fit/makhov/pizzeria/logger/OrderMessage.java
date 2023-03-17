package ru.nsu.fit.makhov.pizzeria.logger;

import ru.nsu.fit.makhov.pizzeria.order.Order;

/**
 * Message of order.
 */
public class OrderMessage {

  private final Order order;

  public OrderMessage(Order order) {
    this.order = order;
  }

  @Override
  public String toString() {
    return "OrderMessage{" + "order=" + order + '}';
  }
}
