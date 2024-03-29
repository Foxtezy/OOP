package ru.nsu.fit.makhov.snake.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.nsu.fit.makhov.snake.model.ViewSelector;

/**
 * Pause controller.
 */
@Component
@RequiredArgsConstructor
public class PauseController {

  private final ViewSelector viewSelector;

  @FXML
  public void goToMenu(ActionEvent event) {
    event.consume();
    viewSelector.selectMainMenu();
  }
}
