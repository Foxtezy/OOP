package ru.nsu.fit.makhov.calc.operations;

import java.util.Collection;
import java.util.function.Function;

/**
 * This interface must be implemented by all classes of operations.
 *
 * @param <C> Collection which contains arguments of operation (the number of arguments must match
 *            the number of arguments in the Operation annotation)
 * @param <N> return type
 */
public interface OperationInterface<C extends Collection<N>, N> extends Function<C, N> {

}
