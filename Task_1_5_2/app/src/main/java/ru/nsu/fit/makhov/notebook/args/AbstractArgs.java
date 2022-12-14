package ru.nsu.fit.makhov.notebook.args;

import com.beust.jcommander.Parameter;
import java.util.ArrayList;
import java.util.List;

public class AbstractArgs {

  @Parameter
  private List<String> args = new ArrayList<>();

  public List<String> getArgs() {
    return args;
  }
}
