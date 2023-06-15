package ru.nsu.fit.makhov.snake.model;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.nsu.fit.makhov.snake.view.MainView;

/**
 * Selector of Pane.
 */
@Component
@RequiredArgsConstructor
public class ViewSelector {

  private final MainView mainView;


  public void selectMainMenu() {
    mainView.setVisibleMainMenu(true);
    mainView.setVisibleGame(false);
  }

  public void selectGame() {
    mainView.setVisibleMainMenu(false);
    mainView.setVisibleGame(true);
  }
}
