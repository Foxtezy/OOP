package ru.nsu.fit.makhov.snake.model.cell;

import ru.nsu.fit.makhov.snake.model.snakes.AbstractSnake;

public class EmptyCell implements Cell {


    @Override
    public void interactWithSnake(AbstractSnake snake, int x, int y) {
        snake.cutTail();
        snake.addHead(x, y);
    }
}
