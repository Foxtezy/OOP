package ru.nsu.fit.makhov.notebook.models;

import java.util.Date;

public class DTO {

  private final String name;
  private final Date date;
  private final String body;

  public DTO(String name, Date date, String body) {
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
}
