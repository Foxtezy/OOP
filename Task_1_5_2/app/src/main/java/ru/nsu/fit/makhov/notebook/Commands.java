package ru.nsu.fit.makhov.notebook;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import ru.nsu.fit.makhov.notebook.json.JsonReader;
import ru.nsu.fit.makhov.notebook.json.JsonWriter;
import ru.nsu.fit.makhov.notebook.models.DTO;
import ru.nsu.fit.makhov.notebook.models.Note;

public class Commands {

  private static final String JSON_NAME = "src/main/resources/notes.json";

  private final JsonReader jsonReader = new JsonReader();

  private final JsonWriter jsonWriter = new JsonWriter();

  @Operation(name = "add", arity = 2)
  public void addNote(List<String> args) {
    Note newNote = new Note(args.get(1));
    Map<String, Note> notes = null;
    try (Reader reader = new FileReader(JSON_NAME)) {
      notes = jsonReader.getNotes(reader);
      if (notes == null) {
        notes = new HashMap<>();
      }
      notes.put(args.get(0), newNote);
    } catch (IOException e) {
      e.printStackTrace();
    }
    try (Writer writer = new FileWriter(JSON_NAME)) {
      jsonWriter.saveNotes(writer, notes);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Operation(name = "rm", arity = 1)
  public void rmNote(List<String> args) {
    Map<String, Note> notes = null;
    try (Reader reader = new FileReader(JSON_NAME)) {
      notes = jsonReader.getNotes(reader);
      if (notes == null || !notes.containsKey(args.get(0))) {
        throw new RuntimeException();
      }
      notes.remove(args.get(0));
    } catch (IOException e) {
      e.printStackTrace();
    }
    try (Writer writer = new FileWriter(JSON_NAME)) {
      jsonWriter.saveNotes(writer, notes);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Operation(name = "show", arity = 0)
  public List<DTO> showNotes(List<String> args) {
    Map<String, Note> notes = null;
    try (Reader reader = new FileReader(JSON_NAME)) {
      notes = jsonReader.getNotes(reader);
    } catch (IOException e) {
      e.printStackTrace();
    }
    if (notes == null) {
      notes = new HashMap<>();
    }
    return notes.entrySet().stream().map(entry -> new DTO(entry.getKey(),
        entry.getValue().getDate(), entry.getValue().getBody())).collect(
        Collectors.toList());
  }

  @Operation(name = "show", arity = 4)
  public List<DTO> showNotesRestrictions(List<String> args) {
    return null;
  }

}
