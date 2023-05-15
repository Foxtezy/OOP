package ru.nsu.fit.makhov.snake.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.SpinnerValueFactory.IntegerSpinnerValueFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.nsu.fit.makhov.snake.model.GameModel;
import ru.nsu.fit.makhov.snake.model.ViewSelector;
import ru.nsu.fit.makhov.snake.model.snakes.HamiltonSnake;
import ru.nsu.fit.makhov.snake.model.snakes.SimpleSnake;

@Component
@RequiredArgsConstructor
public class MainMenuController {

    @FXML
    private Spinner<Integer> speed;

    @FXML
    private Spinner<Integer> sizeX;

    @FXML
    private Spinner<Integer> sizeY;

    @FXML
    private ComboBox<String> secondPlayer;

    @FXML
    private ComboBox<String> thirdPlayer;

    private final ViewSelector viewSelector;

    private final GameModel gameModel;

    @FXML
    public void startGame(ActionEvent event) {
        event.consume();
        viewSelector.selectGame();
        gameModel.setFieldSize(sizeX.getValue(), sizeY.getValue());
        gameModel.setSpeed(speed.getValue());
        addSnakeToField(secondPlayer.getValue(), 2);
        addSnakeToField(thirdPlayer.getValue(), 3);
        new Thread(gameModel).start();
    }

    private void addSnakeToField(String type, int id) {
        if (type == null) return;
        switch (type) {
            case "Hamilton" -> gameModel.addSnake(new HamiltonSnake(gameModel, id));
            case "SimpleSnake" -> gameModel.addSnake(new SimpleSnake(gameModel, id));
        }
    }

    @FXML
    public void initialize() {
        SpinnerValueFactory<Integer> sizeFactoryX = new IntegerSpinnerValueFactory(10, 50, 30);
        SpinnerValueFactory<Integer> sizeFactoryY = new IntegerSpinnerValueFactory(10, 50, 30);
        SpinnerValueFactory<Integer> speedFactory = new IntegerSpinnerValueFactory(1, 20, 10);
        sizeX.setValueFactory(sizeFactoryX);
        sizeY.setValueFactory(sizeFactoryY);
        speed.setValueFactory(speedFactory);
        ObservableList<String> secondPlayerTypes = FXCollections.observableArrayList("None", "Hamilton", "SimpleSnake");
        ObservableList<String> thirdPlayerTypes = FXCollections.observableArrayList("None", "Hamilton", "SimpleSnake");
        secondPlayer.setItems(secondPlayerTypes);
        thirdPlayer.setItems(thirdPlayerTypes);
    }
}
