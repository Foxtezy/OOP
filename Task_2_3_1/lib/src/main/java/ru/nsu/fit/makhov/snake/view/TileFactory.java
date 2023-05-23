package ru.nsu.fit.makhov.snake.view;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import ru.nsu.fit.makhov.snake.model.cell.Cell;
import ru.nsu.fit.makhov.snake.model.cell.SnakeCell;

/**
 * Tile factory.
 */
public class TileFactory {

  private final Random rand = new Random();

  private final Map<Integer, Color> snakeColors = new HashMap<>();

  /**
   * Create new tile from cell.
   *
   * @param cell cell.
   * @return Rectangle.
   */
  public Rectangle createTile(Cell cell) {
    return switch (cell.getCellType()) {
      case APPLE -> new Rectangle(20, 20, Color.RED);
      case SNAKE -> {
        if (cell instanceof SnakeCell snakeCell) {
          if (!snakeColors.containsKey(snakeCell.getSnakeId())) {
            Color snakeColor = new Color(rand.nextDouble(1), rand.nextDouble(1),
                rand.nextDouble(1), 1);
            snakeColors.put(snakeCell.getSnakeId(), snakeColor);
          }
          yield new Rectangle(20, 20, snakeColors.get(snakeCell.getSnakeId()));
        } else {
          throw new ClassCastException();
        }
      }
      case EMPTY -> new Rectangle(20, 20, Color.GRAY);
    };
  }
}
