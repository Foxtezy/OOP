package ru.nsu.fit.makhov.pizzeria.managers;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.stream.Collectors;
import ru.nsu.fit.makhov.pizzeria.PizzeriaConfig;
import ru.nsu.fit.makhov.pizzeria.exceptions.BadJsonException;
import ru.nsu.fit.makhov.pizzeria.json.JsonReader;
import ru.nsu.fit.makhov.pizzeria.order.PizzaOrder;
import ru.nsu.fit.makhov.pizzeria.workers.BakeryWorker;

public class BakeryManager {

  private final List<Thread> bakers;

  private final List<BakeryWorker> bakeryWorkers;

  public BakeryManager(BlockingQueue<PizzaOrder> inputQueue, BlockingQueue<PizzaOrder> storeQueue) {
    bakeryWorkers = JsonReader.getBakeryWorkers(PizzeriaConfig.BAKERS_JSON_NAME)
        .orElseThrow(BadJsonException::new);
    bakeryWorkers.forEach(b -> b.setInputQueue(inputQueue));
    bakeryWorkers.forEach(b -> b.setStoreQueue(storeQueue));
    bakers = bakeryWorkers.stream().map(Thread::new).collect(Collectors.toList());
  }

  public void runBakery() {
    bakers.forEach(Thread::start);
  }

  public void stopBakery() {
    bakers.forEach(Thread::interrupt);
    for (Thread baker : bakers) {
      try {
        baker.join();
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
    }
  }


}
