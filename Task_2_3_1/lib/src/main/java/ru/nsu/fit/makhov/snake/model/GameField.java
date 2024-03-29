package ru.nsu.fit.makhov.snake.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Component;
import ru.nsu.fit.makhov.snake.model.cell.Cell;
import ru.nsu.fit.makhov.snake.model.cell.EmptyCell;

/**
 * Game field.
 */
@Component
public class GameField {

  private List<List<Cell>> field = new ArrayList<>();

  /**
   * Init of game field.
   *
   * @param sizeX size X.
   * @param sizeY size Y.
   */
  public void init(int sizeX, int sizeY) {
    field = new ArrayList<>();
    for (int i = 0; i < sizeX; i++) {
      field.add(i, new ArrayList<>());
      for (int j = 0; j < sizeY; j++) {
        field.get(i).add(j, new EmptyCell());
      }
    }
  }

  public GameField() {
  }

  /**
   * Copy constructor.
   *
   * @param gameField game field.
   */
  public GameField(GameField gameField) {
    for (List<Cell> cl : gameField.field) {
      this.field.add(new ArrayList<>(cl));
    }
  }

  public int getSizeX() {
    return field.size();
  }

  public int getSizeY() {
    return field.get(0).size();
  }

  /**
   * Get cell.
   *
   * @param x x coordinate.
   * @param y y coordinate.
   * @return optional of cell.
   */
  public Optional<Cell> getCell(int x, int y) {
    try {
      return Optional.of(field.get(x).get(y));
    } catch (IndexOutOfBoundsException e) {
      return Optional.empty();
    }
  }

  public void setCell(Cell cell, int x, int y) {
    field.get(x).set(y, cell);
  }

}
