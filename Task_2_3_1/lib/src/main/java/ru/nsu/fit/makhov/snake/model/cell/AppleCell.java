package ru.nsu.fit.makhov.snake.model.cell;

import ru.nsu.fit.makhov.snake.model.snakes.AbstractSnake;

public class AppleCell extends Cell {

    public AppleCell() {
        super(CellType.APPLE);
    }

    @Override
    public void interactWithSnake(AbstractSnake snake, int x, int y) {
        snake.growSnake(x, y);
    }
}
