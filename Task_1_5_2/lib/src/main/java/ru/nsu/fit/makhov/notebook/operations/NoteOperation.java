package ru.nsu.fit.makhov.notebook.operations;

import java.util.List;
import java.util.Optional;
import ru.nsu.fit.makhov.notebook.models.NoteOut;

/**
 * This interface must be implemented
 * by all classes of operations.
 */
public interface NoteOperation {

  Optional<List<NoteOut>> execute(String jsonName, List<String> args);
}
