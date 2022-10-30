package ru.nsu.fit.makhov.calc;

import java.util.List;

/**
 * Class which represents operations in calculator.
 */
public class Operations {

  @Operation(
      name = "+",
      numOfArgs = 2
  )
  Double plus(List<Double> args) {
    return args.get(0) + args.get(1);
  }

  @Operation(
      name = "-",
      numOfArgs = 2
  )
  Double minus(List<Double> args) {
    return args.get(0) - args.get(1);
  }

  @Operation(
      name = "*",
      numOfArgs = 2
  )
  Double multiply(List<Double> args) {
    return args.get(0) * args.get(1);
  }

  @Operation(
      name = "/",
      numOfArgs = 2
  )
  Double division(List<Double> args) {
    return args.get(0) / args.get(1);
  }

  @Operation(
      name = "log",
      numOfArgs = 2
  )
  Double logarithm(List<Double> args) {
    return Math.log(args.get(1) / Math.log(args.get(0)));
  }

  @Operation(
      name = "pow",
      numOfArgs = 2
  )
  Double power(List<Double> args) {
    return Math.pow(args.get(0), (args.get(1)));
  }

  @Operation(
      name = "sqrt",
      numOfArgs = 1
  )
  Double sqrt(List<Double> args) {
    return Math.sqrt(args.get(0));
  }

  @Operation(
      name = "sin",
      numOfArgs = 1
  )
  Double sinus(List<Double> args) {
    return Math.sin(args.get(0));
  }

  @Operation(
      name = "cos",
      numOfArgs = 1
  )
  Double cosines(List<Double> args) {
    return Math.cos(args.get(0));
  }
}
