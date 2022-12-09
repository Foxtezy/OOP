package ru.nsu.fit.makhov.notebook.operations;

import java.util.List;
import ru.nsu.fit.makhov.notebook.models.NoteOut;

public interface NoteOperation {

  String JSON_NAME = "src/main/resources/notes.json";
  List<NoteOut> execute(List<String> args);
}
