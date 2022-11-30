package ru.nsu.fit.makhov.calc.operations;

import java.util.List;
import ru.nsu.fit.makhov.calc.Operation;

@Operation(
    name = "-",
    numOfArgs = 2
)
public class Minus implements OperationInterface<List<Double>, Double>{

    @Override
    public Double apply(List<Double> args) {
        return args.get(0) - args.get(1);
    }
}
