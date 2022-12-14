package ru.nsu.fit.makhov.notebook.operations;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import ru.nsu.fit.makhov.notebook.json.JsonReader;
import ru.nsu.fit.makhov.notebook.json.JsonWriter;
import ru.nsu.fit.makhov.notebook.models.NoteIn;
import ru.nsu.fit.makhov.notebook.models.NoteOut;

@Operation(
    name = "rm",
    numOfArgs = 1
)
public class RemoveNote implements NoteOperation {

  @Override
  public Optional<List<NoteOut>> execute(List<String> args) {
    Map<String, NoteIn> notes = null;
    try (Reader reader = new FileReader(JSON_NAME)) {
      notes = JsonReader.getNotes(reader);
      if (!notes.containsKey(args.get(0))) {
        throw new RuntimeException();
      }
      notes.remove(args.get(0));
    } catch (IOException e) {
      e.printStackTrace();
    }
    try (Writer writer = new FileWriter(JSON_NAME)) {
      JsonWriter.saveNotes(writer, notes);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return Optional.empty();
  }
}
