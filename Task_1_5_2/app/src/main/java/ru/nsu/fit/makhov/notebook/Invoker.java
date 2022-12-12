package ru.nsu.fit.makhov.notebook;

import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.ScanResult;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import ru.nsu.fit.makhov.notebook.exceptions.OperationNotFoundException;
import ru.nsu.fit.makhov.notebook.models.NoteOut;
import ru.nsu.fit.makhov.notebook.models.OperationDescription;
import ru.nsu.fit.makhov.notebook.operations.NoteOperation;

public class Invoker {

  private static final Map<OperationDescription, NoteOperation> operations = new HashMap<>();


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
        operations.put(new OperationDescription(cmd.name(), cmd.numOfArgs(), cmd.minNumOfArgs()),
            operation);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static Optional<List<NoteOut>> invoke(String name, List<String> args) { // TODO: 13.12.2022 vararg minNumOfArgs check
    NoteOperation noteOperation = Optional.ofNullable(
        operations.get(new OperationDescription(name, args.size()))).orElseGet(() -> operations.get(new OperationDescription(name, -1)));
    if (noteOperation == null) {
      throw new OperationNotFoundException();
    }
    return noteOperation.execute(args);
  }
}
