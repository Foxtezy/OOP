package ru.nsu.fit.makhov.snake.view;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Map;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.springframework.stereotype.Component;
import ru.nsu.fit.makhov.snake.model.GameField;
import ru.nsu.fit.makhov.snake.model.GameModel;
import ru.nsu.fit.makhov.snake.model.cell.AppleCell;
import ru.nsu.fit.makhov.snake.model.cell.Cell;
import ru.nsu.fit.makhov.snake.model.cell.EmptyCell;
import ru.nsu.fit.makhov.snake.model.cell.SnakeCell;
import ru.nsu.fit.makhov.snake.model.cell.WallCell;

@Component
public class GameView implements PropertyChangeListener {

    @FXML
    private GridPane gridPane;

    private Map<Class<? extends Cell>, ? extends Node> tiles =
        Map.of(EmptyCell.class, new Rectangle(), SnakeCell.class, new Rectangle(10, 10, Color.BLUE),
            AppleCell.class, new Rectangle(10, 10, Color.RED), WallCell.class, new Rectangle(10, 10, Color.BROWN));


    public GameView(GameModel gameModel) {
        gameModel.addPropertyChangeListener(this);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        GameField gameField = (GameField) evt.getNewValue();
        repaint(gameField);
    }

    private void repaint(GameField gameField) {
        for (int i = 0; i < gameField.getSizeX(); i++) {
            for (int j = 0; j < gameField.getSizeY(); j++) {
                Cell cell = gameField.getCell(i, j);
                gridPane.add(tiles.get(cell.getClass()), i, j);
            }
        }
    }
}
