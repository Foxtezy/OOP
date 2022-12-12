package ru.nsu.fit.makhov.notebook;

import com.beust.jcommander.Parameter;
import java.util.List;

public class Args {

  @Parameter(names = {"-a", "--add"})
  private boolean add;

  @Parameter(names = {"-r", "--rm"})
  private boolean rm;

  @Parameter(names = {"-s", "--show"})
  private boolean show;

  @Parameter
  private List<String> args;
}
