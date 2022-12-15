package ru.nsu.fit.makhov.notebook.args;

import com.beust.jcommander.Parameter;
import java.util.ArrayList;
import java.util.List;

/**
 * A class that should inherit all
 * classes that provide command line arguments.
 */
public abstract class AbstractArgs {

  @Parameter
  private List<String> args = new ArrayList<>();

  public List<String> getArgs() {
    return args;
  }
}
