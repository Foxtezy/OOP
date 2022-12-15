/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package ru.nsu.fit.makhov.notebook;

import com.beust.jcommander.JCommander;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.nsu.fit.makhov.notebook.args.AbstractArgs;
import ru.nsu.fit.makhov.notebook.args.DefaultArgs;
import ru.nsu.fit.makhov.notebook.json.JsonReader;
import ru.nsu.fit.makhov.notebook.json.JsonWriter;
import ru.nsu.fit.makhov.notebook.models.NoteIn;
import ru.nsu.fit.makhov.notebook.models.NoteOut;

class NotebookTests {

  private static final String TEST_JSON_NAME = "src/test/resources/notes.json";


  @BeforeEach
  void setup() {
    try (Writer writer = new FileWriter(TEST_JSON_NAME)) {
      JsonWriter.saveNotes(writer, Map.of("n1", new NoteIn("test note 1")));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private Optional<List<NoteOut>> show() {
    String[] testArgs2 = {"--show"};
    AbstractArgs arguments = new DefaultArgs();
    JCommander.newBuilder()
        .addObject(arguments)
        .build()
        .parse(testArgs2);
    OperationListener operationListener = new OperationListener(TEST_JSON_NAME, arguments);
    return operationListener.onOperationReceived();
  }

  @Test
  void addNote() {
    String[] testArgs = {"--add", "n2", "test note 2"};
    AbstractArgs arguments = new DefaultArgs();
    JCommander.newBuilder()
        .addObject(arguments)
        .build()
        .parse(testArgs);
    OperationListener operationListener = new OperationListener(TEST_JSON_NAME, arguments);
    operationListener.onOperationReceived();
    Assertions.assertEquals(Optional.of(
        Arrays.asList(new NoteOut("n1", "test note 1"), new NoteOut("n2", "test note 2"))), show());
  }

  @Test
  void removeNote() {
    String[] testArgs = {"--rm", "n1"};
    addNote();
    AbstractArgs arguments = new DefaultArgs();
    JCommander.newBuilder()
        .addObject(arguments)
        .build()
        .parse(testArgs);
    OperationListener operationListener = new OperationListener(TEST_JSON_NAME, arguments);
    operationListener.onOperationReceived();
    Assertions.assertEquals(Optional.of(List.of(new NoteOut("n2", "test note 2"))), show());
  }


  @Test
  void showNotesWithRestrictions() {
    String[] testArgs = {"--show", "14.12.2019 7:00", "17.12.2099 13:00", "2"};
    addNote();
    AbstractArgs arguments = new DefaultArgs();
    JCommander.newBuilder()
        .addObject(arguments)
        .build()
        .parse(testArgs);
    OperationListener operationListener = new OperationListener(TEST_JSON_NAME, arguments);
    Assertions.assertEquals(Optional.of(List.of(new NoteOut("n2", "test note 2"))), operationListener.onOperationReceived());
  }


}
