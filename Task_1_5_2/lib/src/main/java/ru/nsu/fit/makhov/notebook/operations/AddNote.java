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

/**
 * Add new note.
 */
@Operation(
    name = "add",
    numOfArgs = 2
)
public class AddNote implements NoteOperation {

  @Override
  public Optional<List<NoteOut>> execute(String jsonName, List<String> args) {
    NoteIn newNoteIn = new NoteIn(args.get(1));
    Map<String, NoteIn> notes;
    try (Reader reader = new FileReader(jsonName)) {
      notes = JsonReader.getNotes(reader).orElseThrow(RuntimeException::new);
      notes.put(args.get(0), newNoteIn);
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
