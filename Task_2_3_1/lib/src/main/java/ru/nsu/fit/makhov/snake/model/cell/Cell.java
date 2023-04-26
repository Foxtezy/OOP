package ru.nsu.fit.makhov.snake.model.cell;

import ru.nsu.fit.makhov.snake.model.snakes.AbstractSnake;

public interface Cell {

    void interactWithSnake(AbstractSnake snake, int x, int y);
}
