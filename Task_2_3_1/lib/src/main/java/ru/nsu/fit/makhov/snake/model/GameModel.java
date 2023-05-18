package ru.nsu.fit.makhov.snake.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.stereotype.Component;
import ru.nsu.fit.makhov.snake.model.dto.GameViewDto;
import ru.nsu.fit.makhov.snake.model.event.Direction;
import ru.nsu.fit.makhov.snake.model.snakes.AbstractSnake;
import ru.nsu.fit.makhov.snake.model.snakes.PlayerSnake;

/**
 * Main model class.
 */
@Component
public class GameModel implements Runnable, DisposableBean {

  private final PropertyChangeSupport viewSender = new PropertyChangeSupport(this);
  private final GameField gameField;
  private final AppleSpawner appleSpawner;
  private final List<AbstractSnake> snakes = new ArrayList<>();
  private PlayerSnake playerSnake;
  private final Object monitor = new Object();
  private volatile boolean pause = false;
  private Thread thread;
  private int speed;

  /**
   * Constructor.
   *
   * @param gameField game field.
   * @param appleSpawner spawner of apples.
   */
  public GameModel(GameField gameField, AppleSpawner appleSpawner) {
    this.gameField = gameField;
    this.appleSpawner = appleSpawner;
  }

  public void setSpeed(int speed) {
    this.speed = speed;
  }

  public void setFieldSize(int fieldSizeX, int fieldSizeY) {
    gameField.init(fieldSizeX, fieldSizeY);
  }

  public void addSnake(AbstractSnake snake) {
    snakes.add(snake);
  }

  public boolean isPause() {
    return pause;
  }

  public GameField getGameField() {
    return gameField;
  }

  public PlayerSnake getPlayerSnake() {
    return playerSnake;
  }

  public AppleSpawner getAppleSpawner() {
    return appleSpawner;
  }

  public void changePlayerSnakeDirection(Direction direction) {
    playerSnake.changeDirection(direction);
  }


  /**
   * Initialize game model.
   */
  public void init() {
    thread = Thread.currentThread();
    playerSnake = new PlayerSnake(this);
    appleSpawner.init();
    appleSpawner.spawnAppleIfFieldEmpty();
    pause = false;
  }

  public void addPropertyChangeListener(PropertyChangeListener pcl) {
    viewSender.addPropertyChangeListener(pcl);
  }

  @Override
  public void run() {
    init();
    GameViewDto oldGameViewDto = new GameViewDto(playerSnake.getScore(), new GameField(gameField));
    viewSender.firePropertyChange("init", null, oldGameViewDto);
    while (!Thread.currentThread().isInterrupted()) {
      if (pause) {
        viewSender.firePropertyChange("pause", oldGameViewDto, null);
        synchronized (monitor) {
          try {
            monitor.wait();
          } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
          }
        }
        viewSender.firePropertyChange("resume", null, oldGameViewDto);
      }
      long currTime = System.currentTimeMillis();
      snakes.removeIf(snake -> !snake.turn());
      if (!playerSnake.turn()) {
        viewSender.firePropertyChange("gameOver", oldGameViewDto, null);
        break;
      }
      GameViewDto newGameViewDto = new GameViewDto(playerSnake.getScore(),
          new GameField(gameField));
      viewSender.firePropertyChange("repaint", oldGameViewDto, newGameViewDto);
      oldGameViewDto = newGameViewDto;
      try {
        Thread.sleep((1000 / speed) - (System.currentTimeMillis() - currTime));
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
      }
    }
    snakes.clear();
  }

  /**
   * Pause or resume game.
   *
   * @param state state of pause.
   */
  public void pause(boolean state) {
    pause = state;
    if (!state) {
      synchronized (monitor) {
        monitor.notifyAll();
      }
    }
  }

  @Override
  public void destroy() {
    thread.interrupt();
  }
}
