package ru.nsu.fit.makhov.pizzeria.managers;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.stream.Collectors;
import ru.nsu.fit.makhov.pizzeria.PizzeriaConfig;
import ru.nsu.fit.makhov.pizzeria.exceptions.BadJsonException;
import ru.nsu.fit.makhov.pizzeria.json.JsonReader;
import ru.nsu.fit.makhov.pizzeria.order.PizzaOrder;

public class DeliveryManager {

  private final List<Thread> drivers;

  public DeliveryManager(BlockingQueue<PizzaOrder> storeQueue) {
    var bakerWorkers = JsonReader.getDeliveryWorkers(PizzeriaConfig.DRIVERS_JSON_NAME)
        .orElseThrow(BadJsonException::new);
    bakerWorkers.forEach(b -> b.setInputQueue(storeQueue));
    drivers = bakerWorkers.stream().map(Thread::new).collect(Collectors.toList());
  }

  public void runDelivery() {
    drivers.forEach(Thread::run);
  }

  public void stopDelivery() {
    drivers.forEach(Thread::interrupt);
    for (Thread driver : drivers) {
      try {
        driver.join();
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
    }
  }
}
