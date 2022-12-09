package ru.nsu.fit.makhov.notebook;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import ru.nsu.fit.makhov.notebook.json.JsonReader;
import ru.nsu.fit.makhov.notebook.json.JsonWriter;
import ru.nsu.fit.makhov.notebook.models.NoteOut;
import ru.nsu.fit.makhov.notebook.models.NoteIn;

public class Commands {

  private static final String JSON_NAME = "src/main/resources/notes.json";

  private final JsonReader jsonReader = new JsonReader();

  private final JsonWriter jsonWriter = new JsonWriter();


  public void addNote(List<String> args) {
    NoteIn newNoteIn = new NoteIn(args.get(1));
    Map<String, NoteIn> notes = null;
    try (Reader reader = new FileReader(JSON_NAME)) {
      notes = jsonReader.getNotes(reader);
      notes.put(args.get(0), newNoteIn);
    } catch (IOException e) {
      e.printStackTrace();
    }
    try (Writer writer = new FileWriter(JSON_NAME)) {
      jsonWriter.saveNotes(writer, notes);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }


  public void rmNote(List<String> args) {
    Map<String, NoteIn> notes = null;
    try (Reader reader = new FileReader(JSON_NAME)) {
      notes = jsonReader.getNotes(reader);
      if (!notes.containsKey(args.get(0))) {
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


  public List<NoteOut> showNotes(List<String> args) {
    if (args.size() == 0) {
      return showAllNotes();
    }
    if (args.size() >= 3) {
      return showNotesWithRestrictions(args);
    }
    return null;
  }

  public List<NoteOut> showAllNotes() {
    Map<String, NoteIn> notes = null;
    try (Reader reader = new FileReader(JSON_NAME)) {
      notes = jsonReader.getNotes(reader);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return notes.entrySet().stream().map(entry -> new NoteOut(entry.getKey(),
        entry.getValue().getDate(), entry.getValue().getBody())).sorted().collect(
        Collectors.toList());
  }

  public List<NoteOut> showNotesWithRestrictions(List<String> args) {
    DateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm");
    long start = 0;
    long end = 0;
    try {
      start = df.parse(args.get(0)).getTime();
      end = df.parse(args.get(1)).getTime();
    } catch (ParseException e) {
      e.printStackTrace();
    }
    Map<String, NoteIn> notes = null;
    try (Reader reader = new FileReader(JSON_NAME)) {
      notes = jsonReader.getNotes(reader);
    } catch (IOException e) {
      e.printStackTrace();
    }
    long finalStart = start;
    long finalEnd = end;
    List<NoteOut> noteOuts = notes.entrySet().stream()
        .filter(entry -> entry.getValue().getDate().getTime() > finalStart
            && entry.getValue().getDate().getTime() < finalEnd)
        .map(entry -> new NoteOut(entry.getKey(),
            entry.getValue().getDate(), entry.getValue().getBody())).sorted().toList();

    List<String> keyWords = new ArrayList<>(args);
    keyWords.remove(0);
    keyWords.remove(0);

    return noteOuts.stream().filter(note -> {
      int cnt = 0;
      for (String keyWord : keyWords) {
        if (note.getName().contains(keyWord)) {
          cnt++;
        }
      }
      return cnt == keyWords.size();
    }).collect(Collectors.toList());
  }
}
