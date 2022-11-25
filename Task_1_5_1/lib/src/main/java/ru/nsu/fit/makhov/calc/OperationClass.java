package ru.nsu.fit.makhov.calc;

import java.util.List;
import java.util.function.Function;

/**
 * Class that represent function and num of args.
 */
public class OperationClass {

  private final int numOfArgs;

  private final Function<List<Double>, Double> function;

  public OperationClass(int numOfArgs, Function<List<Double>, Double> function) {
    this.numOfArgs = numOfArgs;
    this.function = function;
  }

  public int getNumOfArgs() {
    return numOfArgs;
  }

  public Function<List<Double>, Double> getFunction() {
    return function;
  }
}
