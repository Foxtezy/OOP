package ru.nsu.fit.makhov.snake.model;

import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.nsu.fit.makhov.snake.model.dto.GameViewDto;
import ru.nsu.fit.makhov.snake.model.event.Direction;
import ru.nsu.fit.makhov.snake.model.snakes.AbstractSnake;
import ru.nsu.fit.makhov.snake.model.snakes.HamiltonSnake;
import ru.nsu.fit.makhov.snake.model.snakes.PlayerSnake;
import ru.nsu.fit.makhov.snake.view.GameView;

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

    @Value("${game.field.size-x}")
    private int fieldSizeX;
    @Value("${game.field.size-y}")
    private int fieldSizeY;
    @Value("${game.speed}")
    private int speed;

    public GameModel(GameField gameField, AppleSpawner appleSpawner, GameView gameView) {
        this.gameField = gameField;
        this.appleSpawner = appleSpawner;
        viewSender.addPropertyChangeListener(gameView);
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void setFieldSizeX(int fieldSizeX) {
        this.fieldSizeX = fieldSizeX;
    }

    public void setFieldSizeY(int fieldSizeY) {
        this.fieldSizeY = fieldSizeY;
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

    public AppleSpawner getAppleSpawner() {
        return appleSpawner;
    }

    public void changePlayerSnakeDirection(Direction direction) {
        playerSnake.changeDirection(direction);
    }

    private void init() {
        thread = Thread.currentThread();
        gameField.init(fieldSizeX, fieldSizeY);
        playerSnake = new PlayerSnake(this);
        snakes.clear();
        addSnake(new HamiltonSnake(this));
        //addSnake(new SimpleSnake(this));
        appleSpawner.init();
        appleSpawner.spawnAppleIfFieldEmpty();
        pause = false;
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
            GameViewDto newGameViewDto = new GameViewDto(playerSnake.getScore(), new GameField(gameField));
            viewSender.firePropertyChange("repaint", oldGameViewDto, newGameViewDto);
            oldGameViewDto = newGameViewDto;
            try {
                Thread.sleep((1000 / speed) - (System.currentTimeMillis() - currTime));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

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
