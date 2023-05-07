package ru.nsu.fit.makhov.snake.model.snakes;

import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Point2D;
import lombok.experimental.UtilityClass;
import ru.nsu.fit.makhov.snake.model.event.Direction;

@UtilityClass
public class HamiltonPath {

    public static List<Point2D> getHamiltonPath(int boundX, int boundY) {
        List<Point2D> hamiltonPath = new ArrayList<>();
        hamiltonPath.add(new Point2D(0, 0));
        hamiltonStep(hamiltonPath, boundX, boundY, new Point2D(0, 0));
        return hamiltonPath;
    }

    private static boolean hamiltonStep(List<Point2D> hamiltonPath, int boundX, int boundY, Point2D current) {
        if (hamiltonPath.size() == boundX * boundY) {
            Point2D first = hamiltonPath.get(0);
            return (first.getX() == current.getX() && first.getY() == current.getY() - 1)
                || (first.getX() == current.getX() && first.getY() == current.getY() + 1)
                || (first.getX() - 1 == current.getX() && first.getY() == current.getY())
                || (first.getX() + 1 == current.getX() && first.getY() == current.getY());
        }
        List<Direction> directions = List.of(Direction.DOWN, Direction.RIGHT, Direction.UP, Direction.LEFT);
        for (Direction direction : directions) {
            Point2D newElement = switch (direction) {
                case UP -> new Point2D(current.getX(), current.getY() - 1);
                case LEFT -> new Point2D(current.getX() - 1, current.getY());
                case DOWN -> new Point2D(current.getX(), current.getY() + 1);
                case RIGHT -> new Point2D(current.getX() + 1, current.getY());
            };
            if (0 <= newElement.getX() && newElement.getX() < boundX
                && 0 <= newElement.getY() && newElement.getY() < boundY
                && !hamiltonPath.contains(newElement)) {
                hamiltonPath.add(newElement);
                if (hamiltonStep(hamiltonPath, boundX, boundY, newElement)) {
                    return true;
                }
                hamiltonPath.remove(newElement);
            }
        }
        return false;
    }
}
