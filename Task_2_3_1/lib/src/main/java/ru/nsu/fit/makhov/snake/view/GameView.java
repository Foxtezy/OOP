package ru.nsu.fit.makhov.snake.view;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.NumberBinding;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import org.springframework.stereotype.Component;
import ru.nsu.fit.makhov.snake.model.GameField;
import ru.nsu.fit.makhov.snake.model.GameModel;
import ru.nsu.fit.makhov.snake.model.cell.Cell;

@Component
public class GameView implements PropertyChangeListener {

    @FXML
    private GridPane gridPane;

    private List<List<Node>> tileTable;

    private NumberBinding rectsAreaSize;

    public GameView(GameModel gameModel) {
        gameModel.addPropertyChangeListener(this);
    }

    @FXML
    public void initialize() {
        rectsAreaSize = Bindings.min(gridPane.heightProperty(), gridPane.widthProperty());
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        GameField prevGameField = (GameField) evt.getOldValue();
        GameField currGameField = (GameField) evt.getNewValue();
        String propertyName = evt.getPropertyName();
        if (propertyName.equals("init")) {
            Platform.runLater(() -> init(currGameField));
        } else if (propertyName.equals("repaint")) {
            Platform.runLater(() -> repaint(prevGameField, currGameField));
        }
    }

    public void init(GameField gameField) {
        gridPane.setGridLinesVisible(true);
        gridPane.setHgap(1);
        gridPane.setVgap(1);
        tileTable = new ArrayList<>();
        rectsAreaSize = rectsAreaSize.divide(Math.max(gameField.getSizeX(), gameField.getSizeY()));
        for (int i = 0; i < gameField.getSizeX(); i++) {
            tileTable.add(new ArrayList<>());
            for (int j = 0; j < gameField.getSizeY(); j++) {
                Cell cell = gameField.getCell(i, j).orElseThrow();
                addTile(cell, i, j);
            }
        }
    }

    private void repaint(GameField prevGameField, GameField currGameField) {
        for (int i = 0; i < currGameField.getSizeX(); i++) {
            for (int j = 0; j < currGameField.getSizeY(); j++) {
                Cell prevCell = prevGameField.getCell(i, j).orElseThrow();
                Cell currCell = currGameField.getCell(i, j).orElseThrow();
                if (!Objects.equals(prevCell, currCell)) {
                    replaceTile(currCell, i, j);
                }
            }
        }
    }

    private void addTile(Cell cell, int x, int y) {
        Rectangle tile = TileFactory.createTile(cell);
        tile.widthProperty().bind(rectsAreaSize);
        tile.heightProperty().bind(rectsAreaSize);
        tileTable.get(x).add(tile);
        gridPane.add(tile, x, y);
    }

    private void replaceTile(Cell cell, int x, int y) {
        gridPane.getChildren().remove(tileTable.get(x).get(y));
        addTile(cell, x, y);
    }
}
