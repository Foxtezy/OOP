package ru.nsu.fit.makhov.notebook.operations;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import ru.nsu.fit.makhov.notebook.models.NoteOut;

public interface NoteOperation {

  String JSON_NAME = "src/main/resources/notes.json";
  Optional<List<NoteOut>> execute(List<String> args);
}
