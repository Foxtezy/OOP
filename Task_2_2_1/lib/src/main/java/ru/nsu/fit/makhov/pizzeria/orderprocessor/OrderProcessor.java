package ru.nsu.fit.makhov.pizzeria.orderprocessor;

import ru.nsu.fit.makhov.pizzeria.order.Order;

/**
 * Processor that can add new Order.
 */
public interface OrderProcessor {

  void addNewOrder(Order order);
}
