package ru.nsu.fit.makhov.pizzeria.managers;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.stream.Collectors;
import ru.nsu.fit.makhov.pizzeria.PizzeriaConfig;
import ru.nsu.fit.makhov.pizzeria.exceptions.BadJsonException;
import ru.nsu.fit.makhov.pizzeria.json.JsonReader;
import ru.nsu.fit.makhov.pizzeria.order.PizzaOrder;

public class BakeryManager {

  private final List<Thread> bakers;

  public BakeryManager(BlockingQueue<PizzaOrder> inputQueue, BlockingQueue<PizzaOrder> storeQueue) {
    var bakerWorkers = JsonReader.getBakeryWorkers(PizzeriaConfig.BAKERS_JSON_NAME)
        .orElseThrow(BadJsonException::new);
    bakerWorkers.forEach(b -> b.setInputQueue(inputQueue));
    bakerWorkers.forEach(b -> b.setOutputQueue(storeQueue));
    bakers = bakerWorkers.stream().map(Thread::new).collect(Collectors.toList());
  }

  public void runBakery() {
    bakers.forEach(Thread::run);
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
