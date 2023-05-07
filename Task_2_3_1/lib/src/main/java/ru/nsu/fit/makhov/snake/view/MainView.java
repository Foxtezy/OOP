package ru.nsu.fit.makhov.snake.view;

import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import org.springframework.stereotype.Component;

@Component
public class MainView {

    @FXML
    public Pane mainMenu;

    @FXML
    public Pane game;

    public void setVisibleMainMenu(boolean state) {
        mainMenu.setDisable(!state);
        mainMenu.setVisible(state);
    }

    public void setVisibleGame(boolean state) {
        game.setDisable(!state);
        game.setVisible(state);
    }

    @FXML
    public void initialize() {
        setVisibleMainMenu(true);
        setVisibleGame(false);
    }
}
