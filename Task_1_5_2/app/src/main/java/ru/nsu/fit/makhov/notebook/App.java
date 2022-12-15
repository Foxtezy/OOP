package ru.nsu.fit.makhov.notebook;

import com.beust.jcommander.JCommander;
import java.util.List;
import java.util.Optional;
import ru.nsu.fit.makhov.notebook.args.AbstractArgs;
import ru.nsu.fit.makhov.notebook.args.DefaultArgs;
import ru.nsu.fit.makhov.notebook.exceptions.BadInputException;
import ru.nsu.fit.makhov.notebook.exceptions.NoteNotFoundException;
import ru.nsu.fit.makhov.notebook.exceptions.OperationNotFoundException;
import ru.nsu.fit.makhov.notebook.models.NoteOut;

/**
 * Main note class.
 */
public class App {

  public static final String JSON_NAME = "app/src/main/resources/notes.json";

  /**
   * Main.
   */
  public static void main(String[] args) {
    String[] testArgs = {"--show", "14.12.2019 7:00", "17.12.2023 13:00"};
    AbstractArgs arguments = new DefaultArgs();
    JCommander.newBuilder()
        .addObject(arguments)
        .build()
        .parse(testArgs);
    OperationListener operationListener = new OperationListener(JSON_NAME, arguments);
    try {
      Optional<List<NoteOut>> notes = operationListener.onOperationReceived();
      notes.ifPresent(System.out::println);
    } catch (BadInputException e) {
      System.err.println("BAD INPUT");
    } catch (NoteNotFoundException e) {
      System.err.println("NOTE NOT FOUND");
    } catch (OperationNotFoundException e) {
      System.err.println("OPERATION NOT FOUND");
    } catch (RuntimeException e) {
      System.err.println("ERROR");
    }
  }
}
