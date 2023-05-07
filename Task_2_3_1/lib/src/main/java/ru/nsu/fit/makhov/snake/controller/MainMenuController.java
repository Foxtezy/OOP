package ru.nsu.fit.makhov.snake.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Slider;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.SpinnerValueFactory.IntegerSpinnerValueFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.nsu.fit.makhov.snake.model.GameModel;
import ru.nsu.fit.makhov.snake.model.ViewSelector;

@Component
@RequiredArgsConstructor
public class MainMenuController {

    @FXML
    public Spinner<Integer> speed;

    @FXML
    public Spinner<Integer> sizeX;

    @FXML
    public Spinner<Integer> sizeY;

    private final ViewSelector viewSelector;

    private final GameModel gameModel;

    @FXML
    public void startGame(ActionEvent event) {
        event.consume();
        viewSelector.selectGame();
        gameModel.setFieldSizeX(sizeX.getValue());
        gameModel.setFieldSizeY(sizeY.getValue());
        gameModel.setSpeed(speed.getValue());
        gameModel.start();
    }

    @FXML
    public void initialize() {
        SpinnerValueFactory<Integer> sizeFactoryX = new IntegerSpinnerValueFactory(10, 50, 30);
        SpinnerValueFactory<Integer> sizeFactoryY = new IntegerSpinnerValueFactory(10, 50, 30);
        SpinnerValueFactory<Integer> speedFactory = new IntegerSpinnerValueFactory(1, 20, 10);
        sizeX.setValueFactory(sizeFactoryX);
        sizeY.setValueFactory(sizeFactoryY);
        speed.setValueFactory(speedFactory);
    }
}
