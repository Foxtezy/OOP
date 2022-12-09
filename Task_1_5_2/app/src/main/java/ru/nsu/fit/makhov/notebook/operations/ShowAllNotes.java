package ru.nsu.fit.makhov.notebook.operations;

import java.util.List;
import ru.nsu.fit.makhov.notebook.Operation;
import ru.nsu.fit.makhov.notebook.models.NoteOut;

@Operation(
    name = "show",
    numOfArgs = 0,
    returnable = true
)
public class ShowAllNotes implements NoteOperation{

  @Override
  public List<NoteOut> execute(List<String> args) {
    return null;
  }
}
