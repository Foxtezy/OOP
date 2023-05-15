package ru.nsu.fit.makhov.snake.model.snakes;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import ru.nsu.fit.makhov.snake.model.GameModel;
import ru.nsu.fit.makhov.snake.model.cell.Cell;
import ru.nsu.fit.makhov.snake.model.cell.CellType;
import ru.nsu.fit.makhov.snake.model.cell.EmptyCell;
import ru.nsu.fit.makhov.snake.model.cell.SnakeCell;
import ru.nsu.fit.makhov.snake.model.event.Direction;

/**
 * Main logic of all snakes. Other snakes must extend this class.
 */
@RequiredArgsConstructor
public abstract class AbstractSnake {

  protected final Deque<SnakeSegment> snake = new LinkedList<>();

  protected final GameModel gameModel;

  protected boolean isAlive = true;

  protected int score = 0;

  protected final int snakeId;

  /**
   * Kill snake.
   */
  public void kill() {
    snake.forEach(s -> gameModel.getGameField()
        .setCell(new EmptyCell(), s.getCoordinateX(), s.getCoordinateY()));
    isAlive = false;
  }

  /**
   * Snake move.
   *
   * @param direction move direction.
   * @return isAlive status of snake.
   */
  public boolean move(Direction direction) {
    SnakeSegment head = snake.getFirst();
    SnakeSegment futureHead = new SnakeSegment(head, direction);
    Optional<Cell> headCell = gameModel.getGameField()
        .getCell(futureHead.getCoordinateX(), futureHead.getCoordinateY());
    if (headCell.isPresent()) {
      headCell.get()
          .interactWithSnake(this, futureHead.getCoordinateX(), futureHead.getCoordinateY());
    } else {
      kill();
    }
    return isAlive;
  }

  protected void spawn() {
    switch ((snakeId - 1) % 4) {
      case 0 -> {
        snake.add(new SnakeSegment(0, 0));
        snake.add(new SnakeSegment(0, 1));
        snake.add(new SnakeSegment(0, 2));
        snake.add(new SnakeSegment(0, 3));
      }
      case 1 -> {
        snake.add(new SnakeSegment(gameModel.getGameField().getSizeX() - 1, 0));
        snake.add(new SnakeSegment(gameModel.getGameField().getSizeX() - 1, 1));
        snake.add(new SnakeSegment(gameModel.getGameField().getSizeX() - 1, 2));
        snake.add(new SnakeSegment(gameModel.getGameField().getSizeX() - 1, 3));
      }
      case 2 -> {
        snake.add(new SnakeSegment(gameModel.getGameField().getSizeX() - 1, gameModel.getGameField()
            .getSizeY() - 1));
        snake.add(new SnakeSegment(gameModel.getGameField().getSizeX() - 1, gameModel.getGameField()
            .getSizeY() - 2));
        snake.add(new SnakeSegment(gameModel.getGameField().getSizeX() - 1, gameModel.getGameField()
            .getSizeY() - 3));
        snake.add(new SnakeSegment(gameModel.getGameField().getSizeX() - 1, gameModel.getGameField()
            .getSizeY() - 4));
      }
      default -> {
        snake.add(new SnakeSegment(0, gameModel.getGameField().getSizeY() - 1));
        snake.add(new SnakeSegment(0, gameModel.getGameField().getSizeY() - 2));
        snake.add(new SnakeSegment(0, gameModel.getGameField().getSizeY() - 3));
        snake.add(new SnakeSegment(0, gameModel.getGameField().getSizeY() - 4));
      }
    }
  }


  public Integer getScore() {
    return score;
  }

  public void addScore(Integer x) {
    score += x;
  }

  /**
   * Move without moving :). Snake try moving.
   *
   * @param direction direction of move.
   * @return isAlive status.
   */
  protected boolean tryMove(Direction direction) {
    SnakeSegment head = snake.peekFirst();
    SnakeSegment futureHead = new SnakeSegment(head, direction);
    Optional<Cell> headCell = gameModel.getGameField()
        .getCell(futureHead.getCoordinateX(), futureHead.getCoordinateY());
    return headCell.isPresent() && headCell.get().getCellType() != CellType.SNAKE;
  }

  public void cutTail() {
    SnakeSegment tail = snake.removeLast();
    gameModel.getGameField().setCell(new EmptyCell(), tail.getCoordinateX(), tail.getCoordinateY());
  }

  public void addHead(int x, int y) {
    snake.addFirst(new SnakeSegment(x, y));
    gameModel.getGameField().setCell(new SnakeCell(snakeId), x, y);
  }

  /**
   * Grow snake and de spawn apple.
   *
   * @param x x coordinate of new head.
   * @param y y coordinate of new head.
   */
  public void growSnake(int x, int y) {
    gameModel.getAppleSpawner().deSpawnApple(x, y);
    gameModel.getAppleSpawner().spawnAppleIfFieldEmpty();
    addHead(x, y);
  }

  /**
   * Main logic of changing direction of snake.
   *
   * @return isAlive status.
   */
  public abstract boolean turn();

}
