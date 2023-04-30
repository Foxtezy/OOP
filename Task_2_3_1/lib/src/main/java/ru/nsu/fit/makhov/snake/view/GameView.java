package ru.nsu.fit.makhov.snake.view;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import org.springframework.stereotype.Component;
import ru.nsu.fit.makhov.snake.model.GameField;
import ru.nsu.fit.makhov.snake.model.GameModel;
import ru.nsu.fit.makhov.snake.model.cell.Cell;

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
        Platform.runLater(() -> repaint(gameField));
    }

    private void repaint(GameField gameField) {
        for (int i = 0; i < gameField.getSizeX(); i++) {
            for (int j = 0; j < gameField.getSizeY(); j++) {
                Cell cell = gameField.getCell(i, j).orElseThrow();
                gridPane.add(TileFactory.createTile(cell.getClass()), i, j);
            }
        }
    }
}
