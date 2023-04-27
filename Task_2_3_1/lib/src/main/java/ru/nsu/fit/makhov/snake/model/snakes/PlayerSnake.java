package ru.nsu.fit.makhov.snake.model.snakes;

import ru.nsu.fit.makhov.snake.model.GameModel;
import ru.nsu.fit.makhov.snake.model.event.Direction;
import ru.nsu.fit.makhov.snake.model.event.MoveEvent;

public class PlayerSnake extends AbstractSnake {


    private volatile Direction direction = Direction.UP;

    public PlayerSnake(GameModel gameModel) {
        super(gameModel);
        snake.add(new SnakeSegment(0, 0));
        snake.add(new SnakeSegment(1, 0));
    }

    /**
     * главная апиха
     * @param direction
     */
    public void changeDirection(Direction direction) {
        this.direction = direction;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            gameModel.addEvent(new MoveEvent(this, direction));
            try {
                gameModel.getMonitor().wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
