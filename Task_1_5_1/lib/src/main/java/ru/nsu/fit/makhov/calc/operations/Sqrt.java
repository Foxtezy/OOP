package ru.nsu.fit.makhov.calc.operations;

import java.util.List;
import ru.nsu.fit.makhov.calc.Operation;

/**
 * Sqrt.
 */
@Operation(
    name = "sqrt",
    numOfArgs = 1
)
public class Sqrt implements OperationInterface<List<Double>, Double> {

  @Override
  public Double apply(List<Double> args) {
    return Math.sqrt(args.get(0));
  }
}
