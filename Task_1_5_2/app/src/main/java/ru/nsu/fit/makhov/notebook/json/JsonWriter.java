package ru.nsu.fit.makhov.notebook.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.Writer;
import java.util.Map;
import ru.nsu.fit.makhov.notebook.models.NoteIn;

public class JsonWriter {

  private JsonWriter() {
  }

  public static void saveNotes(Writer writer, Map<String, NoteIn> notes) {
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    gson.toJson(notes, writer);
  }

}
