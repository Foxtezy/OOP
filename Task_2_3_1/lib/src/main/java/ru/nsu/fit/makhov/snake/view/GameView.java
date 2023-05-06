package ru.nsu.fit.makhov.snake.view;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import org.springframework.stereotype.Component;
import ru.nsu.fit.makhov.snake.model.GameField;
import ru.nsu.fit.makhov.snake.model.GameModel;
import ru.nsu.fit.makhov.snake.model.cell.Cell;

@Component
public class GameView implements PropertyChangeListener {

    @FXML
    private GridPane gridPane;

    private final Map<Integer, Map<Integer, Node>> tileTable = new HashMap<>();

    public GameView(GameModel gameModel) {
        gameModel.addPropertyChangeListener(this);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        GameField prevGameField = (GameField) evt.getOldValue();
        GameField currGameField = (GameField) evt.getNewValue();
        Platform.runLater(() -> repaint(prevGameField, currGameField));
    }

    private void repaint(GameField prevGameField, GameField currGameField) {
        for (int i = 0; i < currGameField.getSizeX(); i++) {
            for (int j = 0; j < currGameField.getSizeY(); j++) {
                Cell prevCell = prevGameField.getCell(i, j).orElse(null);
                Cell currCell = currGameField.getCell(i, j).orElseThrow();
                if (!Objects.equals(prevCell, currCell)) {
                    replaceTile(currCell, i, j);
                }
            }
        }
    }

    private void replaceTile(Cell cell, int x, int y) {
        tileTable.computeIfAbsent(x, k -> new HashMap<>());
        gridPane.getChildren().remove(tileTable.get(x).get(y));
        Node tile = TileFactory.createTile(cell);
        tileTable.get(x).put(y, tile);
        gridPane.add(tile, x, y);
    }
}
