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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.nsu.fit.makhov.snake.model.GameField;
import ru.nsu.fit.makhov.snake.model.cell.Cell;

@Component
@RequiredArgsConstructor
public class GameView implements PropertyChangeListener {

    @FXML
    private Pane gameViewPane;
    @FXML
    private Pane gameOver;
    @FXML
    private GridPane gridPane;

    private List<List<Node>> tileTable = new ArrayList<>();

    private NumberBinding rectsAreaSize;


    @FXML
    public void initialize() {
        selectGameOver(false);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        GameField prevGameField = (GameField) evt.getOldValue();
        GameField currGameField = (GameField) evt.getNewValue();
        String propertyName = evt.getPropertyName();
        switch (propertyName) {
            case "init" ->  Platform.runLater(() -> init(currGameField));
            case "repaint" -> Platform.runLater(() -> repaint(prevGameField, currGameField));
            case "gameOver" -> setGameOver();
        }
    }

    private void selectGameOver(boolean state) {
        gameOver.setDisable(!state);
        gameOver.setVisible(state);
    }
    private void setGameOver() {
        selectGameOver(true);
        gameViewPane.getChildren().stream().filter(n -> !Objects.equals(n.getId(), "gameOver")).forEach(n -> n.setOpacity(0.5));
    }

    private void init(GameField gameField) {
        rectsAreaSize = Bindings.min(gridPane.heightProperty(), gridPane.widthProperty()).divide(Math.max(gameField.getSizeX(), gameField.getSizeY()));
        tileTable.forEach(l -> l.forEach(n -> gridPane.getChildren().remove(n)));
        selectGameOver(false);
        gameViewPane.getChildren().forEach(n -> n.setOpacity(1));
        gridPane.setGridLinesVisible(true);
        gridPane.setHgap(1);
        gridPane.setVgap(1);
        tileTable.clear();
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
