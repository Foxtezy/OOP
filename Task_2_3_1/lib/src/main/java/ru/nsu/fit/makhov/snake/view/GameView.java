package ru.nsu.fit.makhov.snake.view;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import org.springframework.stereotype.Component;
import ru.nsu.fit.makhov.snake.model.GameField;
import ru.nsu.fit.makhov.snake.model.GameModel;

@Component
public class GameView implements PropertyChangeListener {

    @FXML
    private GridPane gridPane;


    public GameView(GameModel gameModel) {
        gameModel.addPropertyChangeListener(this);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        GameField gameField = (GameField) evt.getNewValue();


    }

    private void repaint(GameField gameField) {
        //gridPane.add(gameField.);
    }
}
