package ru.nsu.fit.makhov.notebook;

import com.beust.jcommander.Parameter;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import ru.nsu.fit.makhov.notebook.models.NoteOut;

public class CommandListener {

  private static final Map<String, Method> commands = new HashMap<>();

  private static final Commands commandsClass = new Commands();

  static {
    for (Method m : commandsClass.getClass().getDeclaredMethods()) {
      if (m.isAnnotationPresent(Operation.class)) {
        Operation cmd = m.getAnnotation(Operation.class);
        commands.put(cmd.name(), m);
      }
    }
  }

  @SuppressWarnings("unchecked cast")
  public List<NoteOut> onMessageReceived(Args args) {

    String parameterName = null;

    for (Field f : args.getClass().getDeclaredFields()) {
      if (f.isAnnotationPresent(Parameter.class)
          && f.getAnnotation(Parameter.class).names().length != 0) {
        f.setAccessible(true);
        try {
          if ((boolean) f.get(args)) {
            parameterName = f.getName();
            break;
          }
        } catch (IllegalAccessException e) {
          e.printStackTrace();
        }
      }
    }

    Method command = commands.get(parameterName);
    List<NoteOut> list = null;
    try {
      //list = (List<NoteOut>) command.invoke(commandsClass, args.getArguments());
    } catch (Exception e) {
      e.printStackTrace();
    }
    return list;
  }
}
