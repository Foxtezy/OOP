package ru.nsu.fit.makhov.snake.model.snakes;

import java.util.List;
import javafx.geometry.Point2D;
import ru.nsu.fit.makhov.snake.model.GameModel;
import ru.nsu.fit.makhov.snake.model.event.Direction;

/**
 * A snake that walks the Hamiltonian path.
 */
public class HamiltonSnake extends AbstractSnake {

  private final List<Direction> path;

  private int nextPathIndex = 0;

  /**
   * Constructor.
   *
   * @param gameModel game model.
   * @param snakeId   snake id.
   */
  public HamiltonSnake(GameModel gameModel, int snakeId) {
    super(gameModel, snakeId);
    spawn();
    path = HamiltonPath.getHamiltonPath(gameModel.getGameField().getSizeX(),
        gameModel.getGameField().getSizeY(),
        new Point2D(snake.peekFirst().getCoordinateX(), snake.peekFirst().getCoordinateY()));
  }


  @Override
  public boolean turn() {
    Direction nextDir = path.get(nextPathIndex++);
    if (nextPathIndex == path.size()) {
      nextPathIndex = 0;
    }
    return move(nextDir);
  }
}
