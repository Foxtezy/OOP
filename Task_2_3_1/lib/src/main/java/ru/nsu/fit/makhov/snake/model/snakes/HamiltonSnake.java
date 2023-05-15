package ru.nsu.fit.makhov.snake.model.snakes;

import java.util.List;
import javafx.geometry.Point2D;
import ru.nsu.fit.makhov.snake.model.GameModel;
import ru.nsu.fit.makhov.snake.model.event.Direction;

public class HamiltonSnake extends AbstractSnake {

    private final List<Direction> path;

    private int nextPathIndex = 0;

    public HamiltonSnake(GameModel gameModel, int snakeId) {
        super(gameModel, snakeId);
        snake.add(new SnakeSegment(gameModel.getGameField().getSizeX() - 1, 0));
        snake.add(new SnakeSegment(gameModel.getGameField().getSizeX() - 1, 1));
        snake.add(new SnakeSegment(gameModel.getGameField().getSizeX() - 1, 2));
        path = HamiltonPath.getHamiltonPath(gameModel.getGameField().getSizeX(),
            gameModel.getGameField().getSizeY(), new Point2D(snake.peekFirst().getX(), snake.peekFirst().getY()));
    }



    @Override
    public boolean turn() {
        Direction nextDir = path.get(nextPathIndex++);
        if (nextPathIndex == path.size()) {
            nextPathIndex = 0;
        }
        return move(nextDir);
    }
}
