package ru.nsu.fit.makhov.pizzeria.managers;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.stream.Collectors;
import ru.nsu.fit.makhov.pizzeria.PizzeriaConfig;
import ru.nsu.fit.makhov.pizzeria.exceptions.BadJsonException;
import ru.nsu.fit.makhov.pizzeria.json.JsonReader;
import ru.nsu.fit.makhov.pizzeria.order.PizzaOrder;
import ru.nsu.fit.makhov.pizzeria.workers.DeliveryWorker;

public class DeliveryManager {

  private final List<Thread> drivers;

  private final List<DeliveryWorker> deliveryWorkers;

  public DeliveryManager(BlockingQueue<PizzaOrder> storeQueue) {
    deliveryWorkers = JsonReader.getDeliveryWorkers(PizzeriaConfig.DRIVERS_JSON_NAME)
        .orElseThrow(BadJsonException::new);
    deliveryWorkers.forEach(b -> b.setStoreQueue(storeQueue));
    drivers = deliveryWorkers.stream().map(Thread::new).collect(Collectors.toList());
  }

  public void runDelivery() {
    drivers.forEach(Thread::start);
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
