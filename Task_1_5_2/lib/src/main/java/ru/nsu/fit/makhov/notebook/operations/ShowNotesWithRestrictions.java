package ru.nsu.fit.makhov.notebook.operations;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import ru.nsu.fit.makhov.notebook.configs.DataConfig;
import ru.nsu.fit.makhov.notebook.json.JsonReader;
import ru.nsu.fit.makhov.notebook.models.NoteIn;
import ru.nsu.fit.makhov.notebook.models.NoteOut;

/**
 * Show notes with restrictions.
 */
@Operation(
    name = "show",
    numOfArgs = 2,
    varArg = true
)
public class ShowNotesWithRestrictions implements NoteOperation {

  @Override
  public Optional<List<NoteOut>> execute(String jsonName, List<String> args) {
    DateFormat df = new SimpleDateFormat(DataConfig.INPUT_DATA_FORMAT);
    long start;
    long end;
    try {
      start = df.parse(args.get(0)).getTime();
      end = df.parse(args.get(1)).getTime();
    } catch (ParseException e) {
      throw new RuntimeException();
    }
    Map<String, NoteIn> notes;
    try (Reader reader = new FileReader(jsonName)) {
      notes = JsonReader.getNotes(reader).orElseThrow(RuntimeException::new);
    } catch (IOException e) {
      throw new RuntimeException();
    }
    long finalStart = start;
    long finalEnd = end;
    List<NoteOut> noteOuts = notes.entrySet().stream()
        .filter(entry -> entry.getValue().getDate().getTime() > finalStart
            && entry.getValue().getDate().getTime() < finalEnd)
        .map(entry -> new NoteOut(entry.getKey(),
            entry.getValue().getDate(), entry.getValue().getBody())).sorted().toList();
    List<String> keyWords = args.stream().skip(2).toList();
    if (keyWords.isEmpty()) {
      return Optional.of(noteOuts);
    }
    return Optional.of(noteOuts.stream().filter(note -> {
      for (String keyWord : keyWords) {
        if (note.getName().contains(keyWord)) {
          return true;
        }
      }
      return false;
    }).collect(Collectors.toList()));
  }
}
