package ru.nsu.fit.makhov.notebook;

import com.beust.jcommander.Parameter;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import ru.nsu.fit.makhov.notebook.args.AbstractArgs;
import ru.nsu.fit.makhov.notebook.exceptions.BadInput;
import ru.nsu.fit.makhov.notebook.models.NoteOut;

public class OperationListener {

  private final AbstractArgs args;

  private final List<Field> operationFlags;

  public OperationListener(AbstractArgs args) {
    this.args = args;
    operationFlags = Arrays.stream(args.getClass().getDeclaredFields())
        .filter(f -> f.isAnnotationPresent(Parameter.class) && f.getType().equals(boolean.class))
        .toList();
    operationFlags.forEach(f -> f.setAccessible(true));
  }

  public Optional<List<NoteOut>> onOperationReceived() {
    List<Field> trueField = operationFlags.stream().filter(f -> {
      try {
        return (Boolean) f.get(args);
      } catch (IllegalAccessException e) {
        throw new RuntimeException(e);
      }
    }).toList();
    if (trueField.size() != 1) {
      throw new BadInput();
    }
    return Invoker.invoke(trueField.get(0).getName(), args.getArgs());
  }
}
