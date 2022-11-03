package ru.nsu.fit.makhov.notebook.models;

import java.util.Date;
import javax.annotation.Nonnull;

public class NoteOut implements Comparable<NoteOut> {

  private final String name;
  private final Date date;
  private final String body;

  public NoteOut(String name, Date date, String body) {
    this.name = name;
    this.date = date;
    this.body = body;
  }

  public String getName() {
    return name;
  }

  public Date getDate() {
    return date;
  }

  public String getBody() {
    return body;
  }

  @Override
  public String toString() {
    return "{" +
        "name='" + name + '\'' +
        ", date=" + date +
        ", body='" + body + '\'' +
        '}';
  }

  @Override
  public int compareTo(@Nonnull NoteOut o) {
    return date.compareTo(o.date);
  }
}
