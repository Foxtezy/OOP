package ru.nsu.fit.makhov.snake.model.cell;

import java.util.Objects;
import ru.nsu.fit.makhov.snake.model.snakes.AbstractSnake;

/**
 * Abstract class which must implement all cell types.
 */
public abstract class Cell {

  private final CellType cellType;

  protected Cell(CellType cellType) {
    this.cellType = cellType;
  }

  public CellType getCellType() {
    return cellType;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Cell cell)) {
      return false;
    }
    return getCellType() == cell.getCellType();
  }

  @Override
  public int hashCode() {
    return Objects.hash(getCellType());
  }

  public abstract void interactWithSnake(AbstractSnake snake, int x, int y);
}
