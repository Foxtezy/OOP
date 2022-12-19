package ru.nsu.fit.makhov.notebook.operations;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import ru.nsu.fit.makhov.notebook.exceptions.NoteNotFoundException;
import ru.nsu.fit.makhov.notebook.json.JsonReader;
import ru.nsu.fit.makhov.notebook.json.JsonWriter;
import ru.nsu.fit.makhov.notebook.models.NoteIn;
import ru.nsu.fit.makhov.notebook.models.NoteOut;

/**
 * Remove note.
 */
@Operation(
    name = "rm",
    numOfArgs = 1
)
public class RemoveNote implements NoteOperation {

  @Override
  public Optional<List<NoteOut>> execute(String jsonName, List<String> args) {
    Map<String, NoteIn> notes;
    try (Reader reader = new FileReader(jsonName)) {
      notes = JsonReader.getNotes(reader).orElseThrow(RuntimeException::new);
      if (!notes.containsKey(args.get(0))) {
        throw new NoteNotFoundException();
      }
      notes.remove(args.get(0));
    } catch (IOException e) {
      throw new RuntimeException();
    }
    try (Writer writer = new FileWriter(jsonName)) {
      JsonWriter.saveNotes(writer, notes);
    } catch (IOException e) {
      throw new RuntimeException();
    }
    return Optional.empty();
  }
}
