package ru.nsu.fit.makhov.snake.model.snakes;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import ru.nsu.fit.makhov.snake.model.GameField;
import ru.nsu.fit.makhov.snake.model.event.Direction;

public class PlayerSnake extends AbstractSnake {


    private final BlockingQueue<Direction> commandQueue = new LinkedBlockingQueue<>();

    public PlayerSnake(GameField gameField, Object monitor) {
        super(gameField, monitor);
    }

    public void addCommand(Direction direction) {
        commandQueue.add(direction);
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {

        }
    }
}
