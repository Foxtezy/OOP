package ru.nsu.fit.makhov.snake.model.snakes;

import com.almasb.astar.AStarGrid;
import com.almasb.astar.AStarNode;
import com.almasb.astar.NodeState;
import java.util.Comparator;
import java.util.List;
import javafx.geometry.Point2D;
import ru.nsu.fit.makhov.snake.model.GameModel;
import ru.nsu.fit.makhov.snake.model.cell.Cell;
import ru.nsu.fit.makhov.snake.model.event.Direction;

/**
 * Snake that finds the way to the apple by A* algorithm.
 */
public class AStarSnake extends AbstractSnake {

  private final AStarGrid aStarGrid;

  /**
   * Constructor.
   *
   * @param gameModel game model.
   * @param snakeId   snake id.
   */
  public AStarSnake(GameModel gameModel, int snakeId) {
    super(gameModel, snakeId);
    spawn();
    aStarGrid = new AStarGrid(gameModel.getGameField().getSizeX(),
        gameModel.getGameField().getSizeY());
  }


  private Direction changeDirection(Point2D head, Point2D nextCell) {
    if (nextCell.getX() == head.getX() && nextCell.getY() == head.getY() - 1) {
      return Direction.UP;
    } else if (nextCell.getX() == head.getX() && nextCell.getY() == head.getY() + 1) {
      return Direction.DOWN;
    } else if (nextCell.getX() == head.getX() - 1 && nextCell.getY() == head.getY()) {
      return Direction.LEFT;
    } else {
      return Direction.RIGHT;
    }
  }


  @Override
  public boolean turn() {
    for (int i = 0; i < gameModel.getGameField().getSizeX(); i++) {
      for (int j = 0; j < gameModel.getGameField().getSizeY(); j++) {
        Cell cell = gameModel.getGameField().getCell(i, j).orElseThrow();
        switch (cell.getCellType()) {
          case SNAKE -> aStarGrid.setNodeState(i, j, NodeState.NOT_WALKABLE);
          case APPLE, EMPTY -> aStarGrid.setNodeState(i, j, NodeState.WALKABLE);
        }
      }
    }
    List<Point2D> apples = gameModel.getAppleSpawner().getApples();
    Point2D head = new Point2D(snake.peekFirst().getCoordinateX(),
        snake.peekFirst().getCoordinateY());
    Comparator<Point2D> cmp = Comparator.comparingDouble(head::distance);
    Point2D nearestApple = apples.stream().min(cmp).orElseThrow();
    List<AStarNode> path = aStarGrid.getPath((int) head.getX(), (int) head.getY(),
        (int) nearestApple.getX(),
        (int) nearestApple.getY());
    if (path.isEmpty()) {
      return move(Direction.UP);
    }
    Direction direction = changeDirection(head,
        new Point2D(path.get(0).getX(), path.get(0).getY()));
    return move(direction);
  }
}
