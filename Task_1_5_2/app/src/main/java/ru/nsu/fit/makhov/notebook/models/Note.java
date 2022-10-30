package ru.nsu.fit.makhov.notebook.models;

import java.util.Date;

public class Note {

  private final String body;

  private final Date date;

  public Note(String body) {
    this.body = body;
    date = new Date(System.currentTimeMillis());
  }

  public String getBody() {
    return body;
  }

  public Date getDate() {
    return date;
  }
}
