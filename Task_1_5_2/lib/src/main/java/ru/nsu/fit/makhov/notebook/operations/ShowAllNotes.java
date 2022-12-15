package ru.nsu.fit.makhov.notebook.operations;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import ru.nsu.fit.makhov.notebook.json.JsonReader;
import ru.nsu.fit.makhov.notebook.models.NoteIn;
import ru.nsu.fit.makhov.notebook.models.NoteOut;

/**
 * Show all notes.
 */
@Operation(
    name = "show",
    numOfArgs = 0
)
public class ShowAllNotes implements NoteOperation {

  @Override
  public Optional<List<NoteOut>> execute(String jsonName, List<String> args) {
    Map<String, NoteIn> notes;
    try (Reader reader = new FileReader(jsonName)) {
      notes = JsonReader.getNotes(reader).orElseThrow(RuntimeException::new);
    } catch (IOException e) {
      throw new RuntimeException();
    }
    return Optional.of(notes.entrySet().stream().map(entry -> new NoteOut(entry.getKey(),
        entry.getValue().getDate(), entry.getValue().getBody())).sorted().collect(
        Collectors.toList()));
  }
}
