package ru.nsu.fit.makhov.snake.model.cell;

import ru.nsu.fit.makhov.snake.model.snakes.AbstractSnake;

public class EmptyCell extends Cell {

    public EmptyCell() {
        super(CellType.EMPTY);
    }

    @Override
    public void interactWithSnake(AbstractSnake snake, int x, int y) {
        snake.cutTail();
        snake.addHead(x, y);
    }
}
