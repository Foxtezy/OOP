package ru.nsu.fit.makhov.pizzeria.orderprocessor;

import ru.nsu.fit.makhov.pizzeria.order.Order;

public interface OrderProcessor {

  void addNewOrder(Order pizzaOrder);
}
