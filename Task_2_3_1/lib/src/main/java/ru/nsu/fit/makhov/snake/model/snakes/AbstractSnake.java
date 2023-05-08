package ru.nsu.fit.makhov.snake.model.snakes;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import ru.nsu.fit.makhov.snake.model.GameModel;
import ru.nsu.fit.makhov.snake.model.cell.Cell;
import ru.nsu.fit.makhov.snake.model.cell.CellType;
import ru.nsu.fit.makhov.snake.model.cell.EmptyCell;
import ru.nsu.fit.makhov.snake.model.cell.SnakeCell;
import ru.nsu.fit.makhov.snake.model.event.Direction;

@RequiredArgsConstructor
public abstract class AbstractSnake {

    protected final Deque<SnakeSegment> snake = new LinkedList<>();

    protected final GameModel gameModel;

    protected boolean isAlive = true;

    public void kill() {
        isAlive = false;
    }

    public boolean move(Direction direction) {
        SnakeSegment head = snake.getFirst();
        SnakeSegment futureHead = new SnakeSegment(head, direction);
        Optional<Cell> headCell = gameModel.getGameField().getCell(futureHead.getX(), futureHead.getY());
        if (headCell.isPresent()) {
            headCell.get().interactWithSnake(this, futureHead.getX(), futureHead.getY());
        } else {
            kill();
        }
        return isAlive;
    }

    protected boolean tryMove(Direction direction) {
        SnakeSegment head = snake.peekFirst();
        SnakeSegment futureHead = new SnakeSegment(head, direction);
        Optional<Cell> headCell = gameModel.getGameField().getCell(futureHead.getX(), futureHead.getY());
        return headCell.isPresent() && headCell.get().getCellType() != CellType.SNAKE;
    }

    public void cutTail() {
        SnakeSegment tail = snake.removeLast();
        gameModel.getGameField().setCell(new EmptyCell(), tail.getX(), tail.getY());
    }

    public void addHead(int x, int y) {
        snake.addFirst(new SnakeSegment(x, y));
        gameModel.getGameField().setCell(new SnakeCell(), x, y);
    }

    public void growSnake(int x, int y) {
        gameModel.getAppleSpawner().deSpawnApple(x, y);
        gameModel.getAppleSpawner().spawnAppleIfFieldEmpty();
        addHead(x, y);
    }

    public abstract boolean turn();

}
