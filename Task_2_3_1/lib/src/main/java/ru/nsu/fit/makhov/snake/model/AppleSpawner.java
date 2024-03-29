package ru.nsu.fit.makhov.snake.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javafx.geometry.Point2D;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.nsu.fit.makhov.snake.model.cell.AppleCell;
import ru.nsu.fit.makhov.snake.model.cell.EmptyCell;

/**
 * Spawner of apples.
 */
@Component
@RequiredArgsConstructor
public class AppleSpawner {

  private final GameField gameField;

  private final Random random = new Random();

  private final List<Point2D> apples = new ArrayList<>();

  public List<Point2D> getApples() {
    return apples;
  }

  public void init() {
    apples.clear();
  }

  /**
   * Spawns apple if no apples on field.
   */
  public void spawnAppleIfFieldEmpty() {
    if (apples.isEmpty()) {
      spawnApple();
    }
  }

  public void deSpawnApple(int x, int y) {
    gameField.setCell(new EmptyCell(), x, y);
    apples.remove(new Point2D(x, y));
  }

  /**
   * Spawn apple at random coordinate.
   */
  public void spawnApple() {
    int x;
    int y;
    do {
      x = random.nextInt(gameField.getSizeX());
      y = random.nextInt(gameField.getSizeY());
    } while (gameField.getCell(x, y).orElseThrow().getClass() != EmptyCell.class);
    spawnApple(x, y);
  }

  public void spawnApple(int x, int y) {
    gameField.setCell(new AppleCell(), x, y);
    apples.add(new Point2D(x, y));
  }
}
