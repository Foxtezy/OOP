package ru.nsu.fit.makhov.notebook.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.Writer;
import java.util.Map;
import ru.nsu.fit.makhov.notebook.models.Note;

public class JsonWriter {

  public void saveNotes(Writer writer, Map<String, Note> notes) {
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    gson.toJson(notes, writer);
  }

}
