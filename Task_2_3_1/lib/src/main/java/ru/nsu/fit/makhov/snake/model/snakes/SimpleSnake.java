package ru.nsu.fit.makhov.snake.model.snakes;

import java.util.Comparator;
import java.util.List;
import javafx.geometry.Point2D;
import ru.nsu.fit.makhov.snake.model.GameModel;
import ru.nsu.fit.makhov.snake.model.event.Direction;

/**
 * Snake that sees an apple and walks towards it in a straight line.
 * If it kills the snake it will go any other way.
 */
public class SimpleSnake extends AbstractSnake {

  public SimpleSnake(GameModel gameModel, int snakeId) {
    super(gameModel, snakeId);
    spawn();
  }

  private Direction nextDirection(Point2D head, Point2D apple) {
    Point2D s = apple.subtract(head);
    if (s.getX() > 0 && s.getX() >= Math.abs(s.getY()) && tryMove(Direction.RIGHT)) {
      return Direction.RIGHT;
    } else if (s.getX() <= 0 && Math.abs(s.getX()) >= Math.abs(s.getY()) && tryMove(
        Direction.LEFT)) {
      return Direction.LEFT;
    } else if (s.getY() > 0 && s.getY() >= Math.abs(s.getX()) && tryMove(Direction.DOWN)) {
      return Direction.DOWN;
    } else if (tryMove(Direction.UP)) {
      return Direction.UP;
    }
    List<Direction> directions = List.of(Direction.UP, Direction.RIGHT, Direction.DOWN,
        Direction.LEFT);
    return directions.stream().filter(this::tryMove).findAny().orElse(Direction.DOWN);
  }

  @Override
  public boolean turn() {
    List<Point2D> apples = gameModel.getAppleSpawner().getApples();
    Point2D head = new Point2D(snake.peekFirst().getCoordinateX(), snake.peekFirst().getCoordinateY());
    Comparator<Point2D> cmp = Comparator.comparingDouble(head::distance);
    Point2D nearestApple = apples.stream().min(cmp).orElseThrow();
    return move(nextDirection(head, nearestApple));
  }
}
