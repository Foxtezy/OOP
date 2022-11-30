package ru.nsu.fit.makhov.calc.operations;

import java.util.List;
import java.util.function.Function;
import ru.nsu.fit.makhov.calc.Operation;

/**
 * Class which represents operations in calculator.
 */
public class DefaultOperations {

  @Operation(
      name = "log",
      numOfArgs = 2
  )
  public Function<List<Double>, Double> logarithm = args -> Math.log(args.get(1)) / Math.log(
      args.get(0));

  @Operation(
      name = "pow",
      numOfArgs = 2
  )
  public Function<List<Double>, Double> power = args -> Math.pow(args.get(0), (args.get(1)));

  @Operation(
      name = "sqrt",
      numOfArgs = 1
  )
  public Function<List<Double>, Double> sqrt = args -> Math.sqrt(args.get(0));

  @Operation(
      name = "sin",
      numOfArgs = 1
  )
  public Function<List<Double>, Double> sinus = args -> Math.sin(args.get(0));

  @Operation(
      name = "cos",
      numOfArgs = 1
  )
  public Function<List<Double>, Double> cosines = args -> Math.cos(args.get(0));
}
