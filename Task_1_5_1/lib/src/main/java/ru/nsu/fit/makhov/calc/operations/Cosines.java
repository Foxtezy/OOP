package ru.nsu.fit.makhov.calc.operations;

import java.util.List;
import ru.nsu.fit.makhov.calc.Operation;

/**
 * Cosines.
 */
@Operation(
    name = "cos",
    numOfArgs = 1
)
public class Cosines implements OperationInterface<List<Double>, Double> {

  @Override
  public Double apply(List<Double> args) {
    return Math.cos(args.get(0));
  }
}
