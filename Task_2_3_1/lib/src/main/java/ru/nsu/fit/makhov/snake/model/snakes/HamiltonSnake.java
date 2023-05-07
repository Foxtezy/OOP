package ru.nsu.fit.makhov.snake.model.snakes;

import java.util.List;
import javafx.geometry.Point2D;
import ru.nsu.fit.makhov.snake.model.GameModel;
import ru.nsu.fit.makhov.snake.model.event.Direction;

public class HamiltonSnake extends AbstractSnake {

    private final List<Point2D> path;

    private int nextPathIndex;

    public HamiltonSnake(GameModel gameModel) {
        super(gameModel);
        snake.add(new SnakeSegment(5, 0));
        snake.add(new SnakeSegment(5, 1));
        snake.add(new SnakeSegment(5, 2));
        snake.add(new SnakeSegment(5, 3));
        path = HamiltonPath.getHamiltonPath(gameModel.getGameField().getSizeX(),
            gameModel.getGameField().getSizeY());
        nextPathIndex = findHeadIndex() + 1;
    }

    private int findHeadIndex() {
        SnakeSegment head = snake.peekFirst();
        for (int i = 0; i < path.size(); i++) {
            if (path.get(i).getX() == head.getX() && path.get(i).getY() == head.getY()) {
                return i;
            }
        }
        return 0;
    }

    private Direction nextDirection(SnakeSegment head, Point2D nextSegment) {
        if (head.getX() == nextSegment.getX() && head.getY() - 1 == nextSegment.getY()) {
            return Direction.UP;
        } else if (head.getX() - 1 == nextSegment.getX() && head.getY() == nextSegment.getY()) {
            return Direction.LEFT;
        } else if (head.getX() == nextSegment.getX() && head.getY() + 1 == nextSegment.getY()) {
            return Direction.DOWN;
        } else {
            return Direction.RIGHT;
        }

    }

    @Override
    public boolean turn() {
        SnakeSegment head = snake.peekFirst();
        Point2D nextSegment = path.get(nextPathIndex++);
        if (nextPathIndex == path.size()) {
            nextPathIndex = 0;
        }
        return move(nextDirection(head, nextSegment));
    }
}
