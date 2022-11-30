package ru.nsu.fit.makhov.calc.operations;

import java.util.List;
import ru.nsu.fit.makhov.calc.Operation;

@Operation(
    name = "sin",
    numOfArgs = 1
)
public class Sinus implements OperationInterface<List<Double>, Double> {

  @Override
  public Double apply(List<Double> args) {
    return Math.sin(args.get(0));
  }
}
