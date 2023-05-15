package ru.nsu.fit.makhov.snake.model.snakes;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import ru.nsu.fit.makhov.snake.model.event.Direction;

/**
 * Segment of snake.
 */
@Data
@RequiredArgsConstructor
public class SnakeSegment {

  private final int coordinateX;
  private final int coordinateY;

  /**
   * Create next segment from others.
   *
   * @param head      old snake head.
   * @param direction direction of new head.
   */
  public SnakeSegment(SnakeSegment head, Direction direction) {
    switch (direction) {
      case UP -> {
        this.coordinateX = head.coordinateX;
        this.coordinateY = head.coordinateY - 1;
      }
      case DOWN -> {
        this.coordinateX = head.coordinateX;
        this.coordinateY = head.coordinateY + 1;
      }
      case LEFT -> {
        this.coordinateX = head.coordinateX - 1;
        this.coordinateY = head.coordinateY;
      }
      case RIGHT -> {
        this.coordinateX = head.coordinateX + 1;
        this.coordinateY = head.coordinateY;
      }
      default -> {
        this.coordinateX = 0;
        this.coordinateY = 0;
      }
    }
  }
}
