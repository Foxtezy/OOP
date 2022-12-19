package ru.nsu.fit.makhov.notebook;

import com.beust.jcommander.Parameter;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import ru.nsu.fit.makhov.notebook.args.AbstractArgs;
import ru.nsu.fit.makhov.notebook.exceptions.BadInputException;
import ru.nsu.fit.makhov.notebook.models.NoteOut;

/**
 * Class that analise args of command line.
 */
public class OperationListener {

  private final AbstractArgs args;

  private final List<Field> operationFlags;

  private final String jsonName;

  /**
   * Constructor.
   *
   * @param jsonName path to json of notes.
   * @param args     argument class.
   */
  public OperationListener(String jsonName, AbstractArgs args) {
    this.jsonName = jsonName;
    this.args = args;
    operationFlags = Arrays.stream(args.getClass().getDeclaredFields())
        .filter(f -> f.isAnnotationPresent(Parameter.class) && f.getType().equals(boolean.class))
        .toList();
    operationFlags.forEach(f -> f.setAccessible(true));
  }

  /**
   * Invoke operation.
   *
   * @return optional of NodeOuts.
   */
  public Optional<List<NoteOut>> onOperationReceived() {
    List<Field> trueField = operationFlags.stream().filter(f -> {
      try {
        return (Boolean) f.get(args);
      } catch (IllegalAccessException e) {
        throw new RuntimeException();
      }
    }).toList();
    if (trueField.size() != 1) {
      throw new BadInputException();
    }
    return Invoker.invoke(jsonName, trueField.get(0).getName(), args.getArgs());
  }
}
