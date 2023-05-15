package ru.nsu.fit.makhov.snake.model.cell;

import java.util.Objects;
import ru.nsu.fit.makhov.snake.model.snakes.AbstractSnake;

public class SnakeCell extends Cell {

    private final int snakeId;

    public SnakeCell(int snakeId) {
        super(CellType.SNAKE);
        this.snakeId = snakeId;
    }

    public int getSnakeId() {
        return snakeId;
    }

    @Override
    public void interactWithSnake(AbstractSnake snake, int x, int y) {
        snake.kill();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SnakeCell snakeCell)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        return snakeId == snakeCell.snakeId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), snakeId);
    }
}
