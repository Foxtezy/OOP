package ru.nsu.fit.makhov.pizzeria;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import org.junit.jupiter.api.Test;
import ru.nsu.fit.makhov.pizzeria.main.PizzeriaInitialisation;
import ru.nsu.fit.makhov.pizzeria.utils.CommandParser;

class PizzeriaTest {
  @Test
  void pizzeriaTest() {
    PizzeriaInitialisation pizzeriaInitialisation = new PizzeriaInitialisation(3, 3);
    pizzeriaInitialisation.getPizzeriaManager().start();
    try (Scanner reader = new Scanner(new File("src/test/resources/orders.txt"))) {
      while (reader.hasNextInt()) {
        pizzeriaInitialisation.getOrderProcessor().addNewOrder(CommandParser.parseCommand(reader.nextLine()));
      }
    } catch (FileNotFoundException e) {
      throw new RuntimeException();
    }
    pizzeriaInitialisation.getPizzeriaManager().stop();
  }
}
