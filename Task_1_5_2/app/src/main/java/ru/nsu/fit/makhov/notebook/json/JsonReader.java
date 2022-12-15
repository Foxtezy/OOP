package ru.nsu.fit.makhov.notebook.json;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.Reader;
import java.util.Map;
import java.util.Optional;
import ru.nsu.fit.makhov.notebook.models.NoteIn;

/**
 * Reader of jsons.
 */
public class JsonReader {

  private JsonReader() {
  }

  /**
   * Get notes from json.
   *
   * @param json json reader.
   * @return optional Map of name and NoteIn of notes.
   */
  public static Optional<Map<String, NoteIn>> getNotes(Reader json) {
    Gson gson = new Gson();
    return Optional.ofNullable(gson.fromJson(json, new TypeToken<Map<String, NoteIn>>() {
    }.getType()));
  }

}
