package ru.nsu.fit.makhov.calc.operations;

import java.util.Collection;
import java.util.function.Function;

public interface OperationInterface<C extends Collection<N>, N> extends Function<C, N>{

}
