package ru.nsu.fit.makhov.snake.model.snakes;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import ru.nsu.fit.makhov.snake.model.GameModel;
import ru.nsu.fit.makhov.snake.model.event.Direction;

/**
 * Player snake.
 */
public class PlayerSnake extends AbstractSnake {

  private Direction direction = Direction.DOWN;
  private final BlockingQueue<Direction> directionQueue = new LinkedBlockingQueue<>(3);

  public PlayerSnake(GameModel gameModel) {
    super(gameModel, 1);
    spawn();
  }

  public void changeDirection(Direction direction) {
    directionQueue.offer(direction);
  }

  @Override
  public boolean turn() {
    if (!directionQueue.isEmpty()) {
      direction = directionQueue.poll();
    }
    return move(direction);
  }
}
