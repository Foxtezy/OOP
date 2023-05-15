package ru.nsu.fit.makhov.snake;

import javafx.application.Application;
import org.springframework.context.annotation.ComponentScan;

/**
 * Launcher.
 */
@ComponentScan
public class Launcher {

  public static void main(String[] args) {
    Application.launch(MainApplication.class, args);
  }
}
