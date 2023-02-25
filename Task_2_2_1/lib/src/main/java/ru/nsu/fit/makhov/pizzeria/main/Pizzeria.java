package ru.nsu.fit.makhov.pizzeria.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import ru.nsu.fit.makhov.pizzeria.order.PizzaOrder;
import ru.nsu.fit.makhov.pizzeria.utils.CommandParser;

public class Pizzeria {


  public static void main(String[] args) {
    PizzeriaStarter pizzeriaStarter = new PizzeriaStarter(2, 3);
    pizzeriaStarter.getPizzeriaManager().start();
    try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
      while (true) {
        String comm = reader.readLine();
        if (comm.equals("stop")) {
          break;
        }
        PizzaOrder order = CommandParser.parseCommand(comm);
        pizzeriaStarter.getOrderProcessor().addNewOrder(order);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    pizzeriaStarter.getPizzeriaManager().stop();
  }
}
