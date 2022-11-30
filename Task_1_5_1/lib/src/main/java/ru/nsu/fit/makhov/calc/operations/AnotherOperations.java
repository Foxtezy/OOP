package ru.nsu.fit.makhov.calc.operations;

import java.util.List;
import java.util.function.Function;
import ru.nsu.fit.makhov.calc.Operation;

public class AnotherOperations {
    @Operation(
        name = "+",
        numOfArgs = 2
    )
    public Function<List<Double>, Double> plus = args -> args.get(0) + args.get(1);

    @Operation(
        name = "-",
        numOfArgs = 2
    )
    public Function<List<Double>, Double> minus = args -> args.get(0) - args.get(1);

    @Operation(
        name = "*",
        numOfArgs = 2
    )
    public Function<List<Double>, Double> multiply = args -> args.get(0) * args.get(1);

    @Operation(
        name = "/",
        numOfArgs = 2
    )
    public Function<List<Double>, Double> division = args -> args.get(0) / args.get(1);
}
