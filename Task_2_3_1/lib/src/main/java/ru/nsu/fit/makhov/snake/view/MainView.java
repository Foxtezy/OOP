package ru.nsu.fit.makhov.snake.view;

import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import org.springframework.stereotype.Component;

@Component
public class MainView {

    @FXML
    private Pane mainMenu;

    @FXML
    private Pane gamePane;


    public void setVisibleMainMenu(boolean state) {
        mainMenu.setDisable(!state);
        mainMenu.setVisible(state);
    }

    public void setVisibleGame(boolean state) {
        gamePane.setDisable(!state);
        gamePane.setVisible(state);
    }

    @FXML
    public void initialize() {
        setVisibleMainMenu(true);
        setVisibleGame(false);
    }
}
