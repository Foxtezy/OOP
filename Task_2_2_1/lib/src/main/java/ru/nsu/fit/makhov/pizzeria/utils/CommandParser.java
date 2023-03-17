package ru.nsu.fit.makhov.pizzeria.utils;

import java.util.List;
import ru.nsu.fit.makhov.pizzeria.order.PizzaOrder;

/**
 * Utility class that parse command from string.
 */
public final class CommandParser {

  private CommandParser() {
    throw new UnsupportedOperationException();
  }

  /**
   * Parse command to PizzaOrder. format: id deliveryTime.
   *
   * @param comm command.
   * @return pizza order.
   */
  public static PizzaOrder parseCommand(String comm) {
    comm = comm.trim();
    List<String> parseComm = List.of(comm.split(" "));
    return new PizzaOrder(Long.parseLong(parseComm.get(0)), Long.parseLong(parseComm.get(1)));
  }
}
