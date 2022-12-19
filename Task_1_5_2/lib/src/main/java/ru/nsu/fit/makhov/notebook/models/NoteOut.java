package ru.nsu.fit.makhov.notebook.models;

import java.util.Date;
import java.util.Objects;
import javax.annotation.Nonnull;

/**
 * Class which represent output note.
 */
public class NoteOut implements Comparable<NoteOut> {

  private final String name;
  private Date date;
  private final String body;

  public NoteOut(String name, String body) {
    this.name = name;
    this.body = body;
  }

  /**
   * All arg constructor.
   */
  public NoteOut(String name, Date date, String body) {
    this.name = name;
    this.date = date;
    this.body = body;
  }

  public String getName() {
    return name;
  }

  public String getBody() {
    return body;
  }

  @Override
  public String toString() {
    return "{" + "name='" + name + '\'' + ", date=" + date + ", body='" + body + '\'' + '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof NoteOut noteOut)) {
      return false;
    }
    return getName().equals(noteOut.getName()) && getBody().equals(noteOut.getBody());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getName(), getBody());
  }

  @Override
  public int compareTo(@Nonnull NoteOut o) {
    return date.compareTo(o.date);
  }
}
