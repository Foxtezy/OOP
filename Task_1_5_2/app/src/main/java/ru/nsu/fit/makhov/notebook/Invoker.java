package ru.nsu.fit.makhov.notebook;

import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.ScanResult;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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
        operations.put(new OperationDescription(cmd.name(), cmd.numOfArgs()),
            operation);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static <T> Optional<T> invoke(String name, Collection<String> args) {
    Optional<NoteOperation> noteOperation = Optional.ofNullable(
        operations.get(new OperationDescription(name, args.size())));
  }
}
