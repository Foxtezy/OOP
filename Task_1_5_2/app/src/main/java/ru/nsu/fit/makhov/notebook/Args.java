package ru.nsu.fit.makhov.notebook;

import com.beust.jcommander.Parameter;
import java.util.ArrayList;
import java.util.List;

public class Args {

  @Parameter(names = {"-a", "--add"}, arity = 2)
  private List<String> add;

  @Parameter(names = {"-r", "--rm"}, arity = 1)
  private List<String> rm;

  @Parameter(names = {"-s", "--show"}, variableArity = true)
  private List<String> show;

}
