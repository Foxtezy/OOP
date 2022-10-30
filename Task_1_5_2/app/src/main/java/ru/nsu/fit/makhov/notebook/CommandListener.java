package ru.nsu.fit.makhov.notebook;

import com.beust.jcommander.Parameter;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import ru.nsu.fit.makhov.notebook.models.DTO;
import ru.nsu.fit.makhov.notebook.models.NameOfCommand;

public class CommandListener {

  private static final Map<NameOfCommand, Method> commands = new HashMap<>();

  private static final Commands commandsClass = new Commands();

  static {
    for (Method m : commandsClass.getClass().getDeclaredMethods()) {
      if (m.isAnnotationPresent(Operation.class)) {
        Operation cmd = m.getAnnotation(Operation.class);
        commands.put(new NameOfCommand(cmd.name(), cmd.arity()), m);
      }
    }
  }

  public List<DTO> onMessageReceived(Args args) {
    Field[] fields = args.getClass().getDeclaredFields();
    List<DTO> list = null;
    for (Field field : fields) {
      field.setAccessible(true);
      try {
        if (field.getAnnotation(Parameter.class).names().length != 0 && (boolean) field.get(args)) {
          String name = field.getName();
          List<String> arguments = args.getArguments();
          NameOfCommand nameOfCommand = new NameOfCommand(name, arguments.size());
          Method command = commands.get(nameOfCommand);
          list = (List<DTO>) command.invoke(commandsClass, args.getArguments());
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    return list;
  }
}
