package ru.nsu.fit.makhov.pizzeria.order;

/**
 * Statuses of order.
 */
public enum OrderStatus {

  WAITING_FOR_COOKING,
  COOKING,
  WAITING_FOR_DELIVERY,
  IN_DELIVERY,
  DELIVERED
}
