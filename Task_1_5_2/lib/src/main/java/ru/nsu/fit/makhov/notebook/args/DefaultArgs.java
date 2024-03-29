package ru.nsu.fit.makhov.notebook.args;

import com.beust.jcommander.Parameter;

/**
 * Default arguments of command line.
 */
public class DefaultArgs extends AbstractArgs {

  @Parameter(names = {"-a", "--add"})
  private boolean add;

  @Parameter(names = {"-r", "--rm"})
  private boolean rm;

  @Parameter(names = {"-s", "--show"})
  private boolean show;
}
