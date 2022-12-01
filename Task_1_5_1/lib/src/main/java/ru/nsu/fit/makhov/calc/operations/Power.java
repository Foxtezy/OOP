package ru.nsu.fit.makhov.calc.operations;

import java.util.List;
import ru.nsu.fit.makhov.calc.Operation;

/**
 * Power.
 */
@Operation(
    name = "pow",
    numOfArgs = 2
)
public class Power implements OperationInterface<List<Double>, Double> {

  @Override
  public Double apply(List<Double> args) {
    return Math.pow(args.get(0), (args.get(1)));
  }
}
