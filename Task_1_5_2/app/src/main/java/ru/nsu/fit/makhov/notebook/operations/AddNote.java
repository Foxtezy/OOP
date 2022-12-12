package ru.nsu.fit.makhov.notebook.operations;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import ru.nsu.fit.makhov.notebook.Operation;
import ru.nsu.fit.makhov.notebook.json.JsonReader;
import ru.nsu.fit.makhov.notebook.json.JsonWriter;
import ru.nsu.fit.makhov.notebook.models.NoteIn;
import ru.nsu.fit.makhov.notebook.models.NoteOut;

@Operation(
    name = "add",
    numOfArgs = 2
)
public class AddNote implements NoteOperation{

  @Override
  public Optional<List<NoteOut>> execute(List<String> args) {
    NoteIn newNoteIn = new NoteIn(args.get(1));
    Map<String, NoteIn> notes = null;
    try (Reader reader = new FileReader(JSON_NAME)) {
      notes = JsonReader.getNotes(reader);
      notes.put(args.get(0), newNoteIn);
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
