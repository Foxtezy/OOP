package ru.nsu.fit.makhov.notebook.json;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.Reader;
import java.util.Map;
import ru.nsu.fit.makhov.notebook.models.NoteIn;

public class JsonReader {

  private JsonReader() {
  }

  public static Map<String, NoteIn> getNotes(Reader json) {
    Gson gson = new Gson();
    return gson.fromJson(json, new TypeToken<Map<String, NoteIn>>(){}.getType());
  }

}