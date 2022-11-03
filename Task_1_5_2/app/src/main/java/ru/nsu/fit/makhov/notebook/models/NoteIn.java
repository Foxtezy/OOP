package ru.nsu.fit.makhov.notebook.models;

import java.util.Date;

public class NoteIn {

  private final String body;

  private final Date date;

  public NoteIn(String body) {
    this.body = body;
    date = new Date();
  }

  public String getBody() {
    return body;
  }

  public Date getDate() {
    return date;
  }
}
