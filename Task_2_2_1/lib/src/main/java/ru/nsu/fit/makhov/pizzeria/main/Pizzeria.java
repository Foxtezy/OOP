package ru.nsu.fit.makhov.pizzeria.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import ru.nsu.fit.makhov.pizzeria.order.PizzaOrder;
import ru.nsu.fit.makhov.pizzeria.utils.CommandParser;

/**
 * Example of Pizzeria.
 */
public class Pizzeria {


  /**
   * Main.
   *
   * @param args command line args.
   */
  public static void main(String[] args) {
    PizzeriaInitialisation pizzeriaInitialisation = new PizzeriaInitialisation(2, 3);
    pizzeriaInitialisation.getPizzeriaManager().start();
    try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
      while (true) {
        String comm = reader.readLine();
        if (comm.equals("stop")) {
          break;
        }
        PizzaOrder order = CommandParser.parseCommand(comm);
        pizzeriaInitialisation.getOrderProcessor().addNewOrder(order);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    pizzeriaInitialisation.getPizzeriaManager().stop();
  }
}
