package ru.nsu.fit.makhov.snake.model.snakes;

import java.util.Deque;
import java.util.LinkedList;
import lombok.RequiredArgsConstructor;
import ru.nsu.fit.makhov.snake.model.GameField;
import ru.nsu.fit.makhov.snake.model.cell.Cell;
import ru.nsu.fit.makhov.snake.model.cell.SnakeCell;
import ru.nsu.fit.makhov.snake.model.event.Direction;

@RequiredArgsConstructor
public abstract class AbstractSnake extends Thread {

    protected final Deque<SnakeSegment> snake = new LinkedList<>();
    protected final GameField gameField;
    protected final Object monitor;

    protected boolean isAlive = true;

    public void kill() {
        isAlive = false;
    }

    public boolean move(Direction direction) {
        snake.pollLast();
        SnakeSegment oldHead = snake.getFirst();
        SnakeSegment newHead = new SnakeSegment(oldHead, direction);
        snake.addFirst(newHead);
        Cell headCell = gameField.getCell(newHead.getX(), newHead.getY());
        headCell.interactWithSnake(this);
        gameField.setCell(new SnakeCell(), newHead.getX(), newHead.getY());
        return isAlive;
    }

}
