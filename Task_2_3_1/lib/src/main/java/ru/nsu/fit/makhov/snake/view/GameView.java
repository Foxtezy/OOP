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
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import org.springframework.stereotype.Component;
import ru.nsu.fit.makhov.snake.model.GameField;
import ru.nsu.fit.makhov.snake.model.GameModel;
import ru.nsu.fit.makhov.snake.model.cell.Cell;
import ru.nsu.fit.makhov.snake.model.dto.GameViewDto;

/**
 * Main game field view.
 */
@Component
public class GameView implements PropertyChangeListener {

  @FXML
  private Pane gameOver;
  @FXML
  private Pane pause;
  @FXML
  private GridPane gridPane;
  @FXML
  private Label scoreLabel;

  private final List<List<Node>> tileTable = new ArrayList<>();

  private NumberBinding rectsAreaSize;

  private TileFactory tileFactory;

  public GameView(GameModel gameModel) {
    gameModel.addPropertyChangeListener(this);
  }

  @FXML
  public void initialize() {
    selectGameOver(false);
    selectPause(false);
  }

  @Override
  public void propertyChange(PropertyChangeEvent evt) {
    GameViewDto prevGameViewDto = (GameViewDto) evt.getOldValue();
    GameViewDto currGameViewDto = (GameViewDto) evt.getNewValue();
    String propertyName = evt.getPropertyName();
    switch (propertyName) {
      case "init" -> Platform.runLater(
          () -> init(currGameViewDto.getPlayerScore(), currGameViewDto.getGameField()));
      case "repaint" -> Platform.runLater(
          () -> repaint(currGameViewDto.getPlayerScore(), prevGameViewDto.getGameField(),
              currGameViewDto.getGameField()));
      case "pause" -> selectPause(true);
      case "resume" -> selectPause(false);
      case "gameOver" -> selectGameOver(true);
    }
  }

  private void selectPause(boolean state) {
    pause.setDisable(!state);
    pause.setVisible(state);
  }

  private void selectGameOver(boolean state) {
    gameOver.setDisable(!state);
    gameOver.setVisible(state);
  }

  private void setPlayerScore(Integer score) {
    scoreLabel.setText(score.toString());
  }

  private void init(Integer playerScore, GameField gameField) {
    tileFactory = new TileFactory();
    setPlayerScore(playerScore);
    selectGameOver(false);
    selectPause(false);
    rectsAreaSize = Bindings.min(gridPane.heightProperty(), gridPane.widthProperty())
        .divide(Math.max(gameField.getSizeX(), gameField.getSizeY()));
    tileTable.forEach(l -> l.forEach(n -> gridPane.getChildren().remove(n)));
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

  private void repaint(Integer playerScore, GameField prevGameField, GameField currGameField) {
    setPlayerScore(playerScore);
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
    Rectangle tile = tileFactory.createTile(cell);
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
