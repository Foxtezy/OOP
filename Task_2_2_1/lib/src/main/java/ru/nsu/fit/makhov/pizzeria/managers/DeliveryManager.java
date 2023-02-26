package ru.nsu.fit.makhov.pizzeria.managers;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.stream.Collectors;
import ru.nsu.fit.makhov.pizzeria.exceptions.BadJsonException;
import ru.nsu.fit.makhov.pizzeria.json.JsonReader;
import ru.nsu.fit.makhov.pizzeria.main.PizzeriaConfig;
import ru.nsu.fit.makhov.pizzeria.order.Order;
import ru.nsu.fit.makhov.pizzeria.workers.DeliveryWorker;

/**
 * Manager which starts and stops BakeryWorkers.
 */
public class DeliveryManager implements Manager {

  private final List<Thread> drivers;

  private final List<DeliveryWorker> deliveryWorkers;


  /**
   * Constructor.
   *
   * @param storeQueue store queue.
   */
  public DeliveryManager(BlockingQueue<Order> storeQueue) {
    deliveryWorkers = JsonReader.getDeliveryWorkers(
            PizzeriaConfig.DRIVERS_JSON_NAME)
        .orElseThrow(BadJsonException::new);
    deliveryWorkers.forEach(b -> b.setStoreQueue(storeQueue));
    deliveryWorkers.forEach(b -> b.setWorking(true));
    drivers = deliveryWorkers.stream().map(Thread::new).collect(Collectors.toList());
  }

  @Override
  public void start() {
    drivers.forEach(Thread::start);
  }

  @Override
  public void stop() {
    deliveryWorkers.forEach(d -> d.setWorking(false));
    for (Thread driver : drivers) {
      try {
        driver.join();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }
}
