package ru.nsu.fit.makhov.multithread;

import java.util.concurrent.Callable;
import ru.nsu.fit.makhov.IsPrime;

/**
 * Task for checking a number for primality.
 */
public class IsPrimeTask implements Callable<Boolean> {

  private final int num;

  public IsPrimeTask(int num) {
    this.num = num;
  }

  @Override
  public Boolean call() {
    return IsPrime.apply(num);
  }
}
