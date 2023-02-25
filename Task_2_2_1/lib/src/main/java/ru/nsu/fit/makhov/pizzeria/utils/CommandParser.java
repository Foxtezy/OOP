package ru.nsu.fit.makhov.pizzeria.utils;

import java.util.List;
import ru.nsu.fit.makhov.pizzeria.order.PizzaOrder;

public final class CommandParser {

  private CommandParser() {
    throw new UnsupportedOperationException();
  }

  public static PizzaOrder parseCommand(String comm) { //[1 2000] [2 3000] [3 2000]
    comm = comm.trim();
    List<String> parseComm = List.of(comm.split(" "));
    return new PizzaOrder(Long.parseLong(parseComm.get(0)), Long.parseLong(parseComm.get(1)));
  }
}
