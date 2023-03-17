package ru.nsu.fit.makhov.pizzeria.managers;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.stream.Collectors;
import ru.nsu.fit.makhov.pizzeria.exceptions.BadJsonException;
import ru.nsu.fit.makhov.pizzeria.json.JsonReader;
import ru.nsu.fit.makhov.pizzeria.main.PizzeriaConfig;
import ru.nsu.fit.makhov.pizzeria.order.Order;
import ru.nsu.fit.makhov.pizzeria.workers.BakeryWorker;

/**
 * Manager which starts and stops BakeryWorkers.
 */
public class BakeryManager implements Manager {

  private final List<Thread> bakers;

  private final List<BakeryWorker> bakeryWorkers;


  /**
   * Constructor.
   *
   * @param inputQueue queue of Orders.
   * @param storeQueue store queue.
   */
  public BakeryManager(BlockingQueue<Order> inputQueue, BlockingQueue<Order> storeQueue) {
    bakeryWorkers = JsonReader.getBakeryWorkers(PizzeriaConfig.BAKERS_JSON_NAME)
        .orElseThrow(BadJsonException::new);
    bakeryWorkers.forEach(b -> b.setInputQueue(inputQueue));
    bakeryWorkers.forEach(b -> b.setStoreQueue(storeQueue));
    bakeryWorkers.forEach(b -> b.setWorking(true));
    bakers = bakeryWorkers.stream().map(Thread::new).collect(Collectors.toList());
  }

  @Override
  public void start() {
    bakers.forEach(Thread::start);
  }

  @Override
  public void stop() {
    bakeryWorkers.forEach(b -> b.setWorking(false));
    for (Thread baker : bakers) {
      try {
        baker.join();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }


}
