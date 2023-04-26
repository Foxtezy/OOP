package ru.nsu.fit.makhov.snake.model.snakes;

import java.util.Deque;
import java.util.LinkedList;
import lombok.RequiredArgsConstructor;
import ru.nsu.fit.makhov.snake.model.GameField;
import ru.nsu.fit.makhov.snake.model.GameModel;
import ru.nsu.fit.makhov.snake.model.cell.Cell;
import ru.nsu.fit.makhov.snake.model.cell.EmptyCell;
import ru.nsu.fit.makhov.snake.model.cell.SnakeCell;
import ru.nsu.fit.makhov.snake.model.event.Direction;

@RequiredArgsConstructor
public abstract class AbstractSnake extends Thread {

    protected final Deque<SnakeSegment> snake = new LinkedList<>();

    protected final GameModel gameModel;

    protected boolean isAlive = true;

    public void kill() {
        isAlive = false;
    }

    public boolean move(Direction direction) {
        SnakeSegment head = snake.getFirst();
        SnakeSegment futureHead = new SnakeSegment(head, direction);
        Cell headCell = gameModel.getGameField().getCell(futureHead.getX(), futureHead.getY());
        headCell.interactWithSnake(this, futureHead.getX(), futureHead.getY());
        return isAlive;
    }

    public void cutTail() {
        SnakeSegment tail = snake.removeLast();
        gameModel.getGameField().setCell(new EmptyCell(), tail.getX(), tail.getY());
    }

    public void addHead(int x, int y) {
        snake.addFirst(new SnakeSegment(x, y));
        gameModel.getGameField().setCell(new SnakeCell(), x, y);
    }

}
