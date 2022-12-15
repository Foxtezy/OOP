package ru.nsu.fit.makhov.notebook;

import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.ScanResult;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Optional;
import ru.nsu.fit.makhov.notebook.exceptions.OperationNotFoundException;
import ru.nsu.fit.makhov.notebook.models.NoteOut;
import ru.nsu.fit.makhov.notebook.models.OperationDescription;
import ru.nsu.fit.makhov.notebook.operations.NoteOperation;
import ru.nsu.fit.makhov.notebook.operations.Operation;

/**
 * The class that calls the operations.
 */
public class Invoker {

  private static final Map<OperationDescription, NoteOperation> operations = new HashMap<>();

  private Invoker() {
  }

  static {
    try (ScanResult scanResult = new ClassGraph().acceptPackages(
        "ru.nsu.fit.makhov.notebook").scan()) {
      List<? extends Class<?>> classes = scanResult.getAllClasses()
          .stream().map(ClassInfo::loadClass)
          .filter(c -> c.isAnnotationPresent(Operation.class)).toList();
      for (Class<?> operationClass : classes) {
        Operation cmd = operationClass.getAnnotation(Operation.class);
        NoteOperation operation = (NoteOperation) operationClass
            .getDeclaredConstructor().newInstance();
        operations.put(new OperationDescription(cmd.name(), cmd.numOfArgs(), cmd.varArg()),
            operation);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Invoke operation.
   *
   * @param jsonName path to json with notes.
   * @param name     name of operation.
   * @param args     args of operation.
   * @return optional list of NoteOuts.
   */
  public static Optional<List<NoteOut>> invoke(String jsonName, String name, List<String> args) {
    NoteOperation noteOperation = operations.entrySet().stream()
        .filter(m -> Objects.equals(m.getKey().getName(), name))
        .filter(m -> m.getKey().getNumOfArgs() == args.size()
            || (m.getKey().getNumOfArgs() < args.size() && m.getKey().isVarArg()))
        .map(Entry::getValue).findFirst().orElseThrow(OperationNotFoundException::new);
    return noteOperation.execute(jsonName, args);
  }
}
