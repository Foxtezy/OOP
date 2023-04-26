package ru.nsu.fit.makhov.snake.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.nsu.fit.makhov.snake.model.event.MoveEvent;
import ru.nsu.fit.makhov.snake.model.snakes.AbstractSnake;

@Component
@RequiredArgsConstructor
public class GameModel implements Runnable {

    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    private final GameField gameField;

    private final BlockingQueue<MoveEvent> eventQueue = new LinkedBlockingQueue<>();

    private final Object monitor = new Object();

    private final List<AbstractSnake> snakes = new ArrayList<>();

    @Value("${game.field.size-x}")
    private int fieldSizeX;

    @Value("${game.field.size-y}")
    private int fieldSizeY;

    @Value("${game.speed}")
    private int speed;

    public void setFieldSizeX(int fieldSizeX) {
        this.fieldSizeX = fieldSizeX;
    }

    public void setFieldSizeY(int fieldSizeY) {
        this.fieldSizeY = fieldSizeY;
    }

    public void addSnake(AbstractSnake snake) {
        snakes.add(snake);
    }

    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        support.addPropertyChangeListener(pcl);
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
            synchronized (monitor) {
                monitor.notifyAll();
            }
        }
    }
}
