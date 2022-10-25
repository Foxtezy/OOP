package ru.nsu.fit.makhov.calc;

import java.lang.reflect.Method;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * Listener of expirations.
 */
public class ExpirationListener {

  private final Deque<Double> stackOfNumbers = new ArrayDeque<>();

  private static final Map<String, Method> functions = new HashMap<>();

  private static final Operations operations = new Operations();

  static {
    for (Method m : operations.getClass().getDeclaredMethods()) {
      if (m.isAnnotationPresent(Operation.class)) {
        Operation cmd = m.getAnnotation(Operation.class);
        functions.put(cmd.name(), m);
      }
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
      Method func = functions.get(expiration.get(i));
      if (func == null) {
        throw new NoSuchOperationException(expiration.get(i));
      }
      List<Double> listOfArgs = new ArrayList<>();
      int numOfArgs = func.getAnnotation(Operation.class).numOfArgs();
      for (int j = 0; j < numOfArgs; j++) {
        try {
          listOfArgs.add(stackOfNumbers.pop());
        } catch (NoSuchElementException e) {
          throw new ExpirationException();
        }
      }
      try {
        Double res = (Double) func.invoke(operations, listOfArgs);
        stackOfNumbers.push(res);
      } catch (ReflectiveOperationException e) {
        e.printStackTrace();
      }
    }
    if (stackOfNumbers.size() != 1) {
      throw new ExpirationException();
    }
    return stackOfNumbers.pop();
  }


}
