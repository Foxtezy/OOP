package ru.nsu.fit.makhov.multithread;

import java.util.concurrent.Callable;
import ru.nsu.fit.makhov.IsPrime;

public class IsPrimeTask implements Callable<Boolean> {

  private final long num;

  public IsPrimeTask(long num) {
    this.num = num;
  }

  @Override
  public Boolean call() {
    return new IsPrime().test(num);
  }
}
