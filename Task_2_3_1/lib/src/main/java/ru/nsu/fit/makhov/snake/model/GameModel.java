package ru.nsu.fit.makhov.snake.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.nsu.fit.makhov.snake.model.event.Direction;
import ru.nsu.fit.makhov.snake.model.snakes.AbstractSnake;
import ru.nsu.fit.makhov.snake.model.snakes.PlayerSnake;

@Component
public class GameModel extends Thread implements DisposableBean {

    private final PropertyChangeSupport viewSender = new PropertyChangeSupport(this);
    private final GameField gameField;
    private final AppleSpawner appleSpawner;
    private final List<AbstractSnake> snakes = new ArrayList<>();
    private final PlayerSnake playerSnake = new PlayerSnake(this);

    @Value("${game.field.size-x}")
    private int fieldSizeX;
    @Value("${game.field.size-y}")
    private int fieldSizeY;
    @Value("${game.speed}")
    private int speed;

    public GameModel(GameField gameField, AppleSpawner appleSpawner) {
        snakes.add(playerSnake);
        this.gameField = gameField;
        this.appleSpawner = appleSpawner;
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


    public GameField getGameField() {
        return gameField;
    }

    public AppleSpawner getAppleSpawner() {
        return appleSpawner;
    }

    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        viewSender.addPropertyChangeListener(pcl);
    }

    public void changePlayerSnakeDirection(Direction direction) {
        playerSnake.changeDirection(direction);
    }

    @Override
    public void run() {
        gameField.init(fieldSizeX, fieldSizeY);
        appleSpawner.spawnAppleIfFieldEmpty();
        GameField oldGameField = new GameField(gameField);
        viewSender.firePropertyChange("init", null, oldGameField);
        while (!Thread.currentThread().isInterrupted()) {
            long currTime = System.currentTimeMillis();
            snakes.forEach(AbstractSnake::turn);
            GameField newGameField = new GameField(gameField);
            viewSender.firePropertyChange("repaint", oldGameField, newGameField);
            oldGameField = newGameField;
            try {
                Thread.sleep((1000 / speed)  - (System.currentTimeMillis() - currTime));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    @Override
    public void destroy() throws Exception {
        super.interrupt();
    }
}
