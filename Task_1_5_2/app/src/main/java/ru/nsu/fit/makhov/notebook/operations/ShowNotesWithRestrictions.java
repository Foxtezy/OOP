package ru.nsu.fit.makhov.notebook.operations;

import java.util.List;
import java.util.Optional;
import ru.nsu.fit.makhov.notebook.Operation;
import ru.nsu.fit.makhov.notebook.models.NoteOut;

@Operation(
    name = "show",
    numOfArgs = -1,
    variableArity = true
)
public class ShowNotesWithRestrictions implements NoteOperation {

  @Override
  public Optional<List<NoteOut>> execute(List<String> args) {
    return null;
  }
}
