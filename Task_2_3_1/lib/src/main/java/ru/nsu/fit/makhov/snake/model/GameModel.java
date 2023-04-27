package ru.nsu.fit.makhov.snake.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.nsu.fit.makhov.snake.model.event.Direction;
import ru.nsu.fit.makhov.snake.model.event.MoveEvent;
import ru.nsu.fit.makhov.snake.model.snakes.AbstractSnake;
import ru.nsu.fit.makhov.snake.model.snakes.PlayerSnake;

@Component
public class GameModel implements Runnable {

    private final PropertyChangeSupport viewSender = new PropertyChangeSupport(this);
    private final GameField gameField;
    private final BlockingQueue<MoveEvent> eventQueue = new LinkedBlockingQueue<>();
    private final Object monitor = new Object();
    private final List<AbstractSnake> snakes = new ArrayList<>();
    private final PlayerSnake playerSnake = new PlayerSnake(this);

    @Value("${game.field.size-x}")
    private int fieldSizeX;
    @Value("${game.field.size-y}")
    private int fieldSizeY;
    @Value("${game.speed}")
    private int speed;

    public GameModel(GameField gameField) {
        snakes.add(playerSnake);
        this.gameField = gameField;
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

    public void addEvent(MoveEvent event) {
        eventQueue.add(event);
    }

    public GameField getGameField() {
        return gameField;
    }

    public Object getMonitor() {
        return monitor;
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
        snakes.forEach(Thread::start);
        while (!Thread.currentThread().isInterrupted()) {
            try {
                Thread.sleep(speed);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            while (!eventQueue.isEmpty()) {
                MoveEvent event = eventQueue.poll();
                if (!event.getSnake().move(event.getDirection())) {
                    // TODO: 26.04.2023 змейка убита
                }
            }
            viewSender.firePropertyChange("repaint", null, new GameField(gameField));
            synchronized (monitor) {
                monitor.notifyAll();
            }
        }
    }
}