package ru.nsu.fit.makhov.calc.operations;

import java.util.List;
import ru.nsu.fit.makhov.calc.Operation;

/**
 * Logarithm.
 */
@Operation(
    name = "log",
    numOfArgs = 2
)
public class Logarithm implements OperationInterface<List<Double>, Double> {

  @Override
  public Double apply(List<Double> args) {
    return Math.log(args.get(1)) / Math.log(args.get(0));
  }
}
