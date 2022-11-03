package ru.nsu.fit.makhov.notebook;

import com.beust.jcommander.Parameter;
import java.util.ArrayList;
import java.util.List;

public class Args {

  @Parameter(names = {"-a", "--add"})
  private boolean add = false;

  @Parameter(names = {"-r", "--rm"})
  private boolean rm = false;

  @Parameter(names = {"-s", "--show"})
  private boolean show = false;

  @Parameter()
  private List<String> arguments = new ArrayList<>();

  public List<String> getArguments() {
    return arguments;
  }
}
