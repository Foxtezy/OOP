package ru.nsu.fit.makhov.calc;

import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.ScanResult;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import ru.nsu.fit.makhov.calc.operations.OperationInterface;

/**
 * Listener of expirations.
 */
public class ExpirationListener {

  private final Deque<Double> stackOfNumbers = new ArrayDeque<>();

  private static final Map<String, OperationInterface<List<Double>, Double>> operations =
      new HashMap<>();


  static {
    try (ScanResult scanResult = new ClassGraph().acceptPackages(
        "ru.nsu.fit.makhov.calc").scan()) {
      List<? extends Class<?>> classes = scanResult.getAllClasses()
          .stream().map(ClassInfo::loadClass)
          .filter(c -> c.isAnnotationPresent(Operation.class)).toList();
      for (Class<?> classInfo : classes) {
        Operation cmd = classInfo.getAnnotation(Operation.class);
        @SuppressWarnings("unchecked cast")
        OperationInterface<List<Double>, Double> operation =
            (OperationInterface<List<Double>, Double>) classInfo
                .getDeclaredConstructor().newInstance();
        operations.put(cmd.name(), operation);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }


  }

  private List<String> parse(String str) {
    str = str.trim();
    str = str.replaceAll("\\s\\s+", " ");
    return Arrays.asList(str.split(" "));
  }

  private boolean isDouble(String s) {
    try {
      Double.parseDouble(s);
      return true;
    } catch (NumberFormatException e) {
      return false;
    }
  }

  /**
   * Calculate expiration.
   *
   * @param expr expiration.
   * @return answer.
   */
  public Double calcExpiration(String expr) {
    List<String> expiration = parse(expr);
    for (int i = expiration.size() - 1; i >= 0; i--) {
      if (isDouble(expiration.get(i))) {
        Double num = Double.parseDouble(expiration.get(i));
        stackOfNumbers.push(num);
        continue;
      }
      OperationInterface<List<Double>, Double> operation = operations.get(expiration.get(i));
      if (operation == null) {
        throw new NoSuchOperationException(expiration.get(i));
      }
      List<Double> listOfArgs = new ArrayList<>();
      int numOfArgs = operation.getClass().getAnnotation(Operation.class).numOfArgs();
      for (int j = 0; j < numOfArgs; j++) {
        try {
          listOfArgs.add(stackOfNumbers.pop());
        } catch (NoSuchElementException e) {
          throw new ExpirationException();
        }
      }
      Double res = operation.apply(listOfArgs);
      stackOfNumbers.push(res);
    }
    if (stackOfNumbers.size() != 1) {
      throw new ExpirationException();
    }
    return stackOfNumbers.pop();
  }


}
