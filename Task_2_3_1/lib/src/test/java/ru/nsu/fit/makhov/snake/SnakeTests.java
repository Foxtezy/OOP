package ru.nsu.fit.makhov.snake;

import java.util.ArrayDeque;
import java.util.Deque;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.nsu.fit.makhov.snake.model.AppleSpawner;
import ru.nsu.fit.makhov.snake.model.GameField;
import ru.nsu.fit.makhov.snake.model.GameModel;
import ru.nsu.fit.makhov.snake.model.event.Direction;
import ru.nsu.fit.makhov.snake.model.snakes.PlayerSnake;
import ru.nsu.fit.makhov.snake.model.snakes.SnakeSegment;

class SnakeTests {

  private final GameField gameField = new GameField();
  private final GameModel gameModel = new GameModel(gameField, new AppleSpawner(gameField));

  @BeforeEach
  void setup() {
    gameModel.setFieldSize(10, 10);
    gameModel.init();
  }

  @Test
  void playerSnakeMovementTest() {
    PlayerSnake playerSnake = gameModel.getPlayerSnake();
    Deque<SnakeSegment> oldSnake = new ArrayDeque<>(playerSnake.getSnake());
    playerSnake.changeDirection(Direction.DOWN);
    Assertions.assertTrue(playerSnake.turn());
    Deque<SnakeSegment> newSnake = new ArrayDeque<>(playerSnake.getSnake());
    SnakeSegment expectedHead = new SnakeSegment(oldSnake.getFirst().getCoordinateX(), oldSnake.getFirst().getCoordinateY() + 1);
    SnakeSegment expectedTail = new SnakeSegment(oldSnake.getLast().getCoordinateX(), oldSnake.getLast().getCoordinateY() + 1);
    Assertions.assertEquals(expectedHead, newSnake.getFirst());
    Assertions.assertEquals(expectedTail, newSnake.getLast());
  }

  @Test
  void playerSnakeGrowTest() {
    PlayerSnake playerSnake = gameModel.getPlayerSnake();
    gameModel.getAppleSpawner().spawnApple(0, 4);
    Deque<SnakeSegment> oldSnake = new ArrayDeque<>(playerSnake.getSnake());
    playerSnake.changeDirection(Direction.DOWN);
    Assertions.assertTrue(playerSnake.turn());
    Deque<SnakeSegment> newSnake = new ArrayDeque<>(playerSnake.getSnake());
    SnakeSegment expectedHead = new SnakeSegment(oldSnake.getFirst().getCoordinateX(), oldSnake.getFirst().getCoordinateY() + 1);
    SnakeSegment expectedTail = new SnakeSegment(oldSnake.getLast().getCoordinateX(), oldSnake.getLast().getCoordinateY());
    Assertions.assertEquals(expectedHead, newSnake.getFirst());
    Assertions.assertEquals(expectedTail, newSnake.getLast());
  }

  @Test
  void playerSnakeKillTest() {
    PlayerSnake playerSnake = gameModel.getPlayerSnake();
    playerSnake.changeDirection(Direction.LEFT);
    Assertions.assertFalse(playerSnake.turn());
  }
}
