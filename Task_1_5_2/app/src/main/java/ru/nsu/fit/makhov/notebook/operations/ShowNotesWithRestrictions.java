package ru.nsu.fit.makhov.notebook.operations;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import ru.nsu.fit.makhov.notebook.json.JsonReader;
import ru.nsu.fit.makhov.notebook.models.NoteIn;
import ru.nsu.fit.makhov.notebook.models.NoteOut;

@Operation(
    name = "show",
    numOfArgs = 2,
    varArg = true
)
public class ShowNotesWithRestrictions implements NoteOperation {

  @Override
  public Optional<List<NoteOut>> execute(List<String> args) {
    DateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm");
    long start = 0;
    long end = 0;
    try {
      start = df.parse(args.get(0)).getTime();
      end = df.parse(args.get(1)).getTime();
    } catch (ParseException e) {
      e.printStackTrace();
    }
    Map<String, NoteIn> notes = null;
    try (Reader reader = new FileReader(JSON_NAME)) {
      notes = JsonReader.getNotes(reader);
    } catch (IOException e) {
      e.printStackTrace();
    }
    long finalStart = start;
    long finalEnd = end;
    List<NoteOut> noteOuts = notes.entrySet().stream()
        .filter(entry -> entry.getValue().getDate().getTime() > finalStart
            && entry.getValue().getDate().getTime() < finalEnd)
        .map(entry -> new NoteOut(entry.getKey(),
            entry.getValue().getDate(), entry.getValue().getBody())).sorted().toList();

    List<String> keyWords = new ArrayList<>(args);
    keyWords.remove(0);
    keyWords.remove(0);

    return Optional.of(noteOuts.stream().filter(note -> {
      int cnt = 0;
      for (String keyWord : keyWords) {
        if (note.getName().contains(keyWord)) {
          cnt++;
        }
      }
      return cnt == keyWords.size();
    }).collect(Collectors.toList()));
  }
}
