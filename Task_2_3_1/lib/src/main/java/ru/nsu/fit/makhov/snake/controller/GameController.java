package ru.nsu.fit.makhov.snake.controller;

import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.nsu.fit.makhov.snake.model.GameModel;
import ru.nsu.fit.makhov.snake.model.event.Direction;

@Component
public class GameController {

    @FXML
    private Pane pane;

    private final GameModel gameModel;

    @Autowired
    public GameController(GameModel gameModel) {
        this.gameModel = gameModel;
    }

    @FXML
    public void initialize() {
        pane.sceneProperty().addListener((observable, oldScene, newScene) -> {
            if (newScene != null) {
                newScene.setOnKeyPressed(
                    event -> {
                        switch (event.getCode()) {
                            case W -> gameModel.changePlayerSnakeDirection(Direction.UP);
                            case A -> gameModel.changePlayerSnakeDirection(Direction.LEFT);
                            case S -> gameModel.changePlayerSnakeDirection(Direction.DOWN);
                            case D -> gameModel.changePlayerSnakeDirection(Direction.RIGHT);
                        }
                    });
            }
        });
    }

}
