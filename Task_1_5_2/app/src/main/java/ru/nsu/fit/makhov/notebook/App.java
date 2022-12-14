package ru.nsu.fit.makhov.notebook;

import com.beust.jcommander.JCommander;
import ru.nsu.fit.makhov.notebook.args.AbstractArgs;
import ru.nsu.fit.makhov.notebook.args.DefaultArgs;

public class App {

  public static void main(String[] args) {
    String[] testArgs = {"--show"};
    AbstractArgs arguments = new DefaultArgs();
    JCommander.newBuilder()
        .addObject(arguments)
        .build()
        .parse(testArgs);
    OperationListener operationListener = new OperationListener(arguments);
    System.out.println(operationListener.onOperationReceived().orElse(null));
  }
}
