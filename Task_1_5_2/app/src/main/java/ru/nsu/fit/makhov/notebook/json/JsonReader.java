package ru.nsu.fit.makhov.notebook.json;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.Reader;
import java.util.Map;
import ru.nsu.fit.makhov.notebook.models.Note;

public class JsonReader {

  public Map<String, Note> getNotes(Reader json) {
    Gson gson = new Gson();
    return gson.fromJson(json, new TypeToken<Map<String, Note>>(){}.getType());
  }

}
