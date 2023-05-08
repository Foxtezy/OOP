package ru.nsu.fit.makhov.snake.model.snakes;

import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Point2D;
import lombok.experimental.UtilityClass;
import ru.nsu.fit.makhov.snake.model.event.Direction;

@UtilityClass
public class HamiltonPath {

    public static List<Direction> getHamiltonPath(int boundX, int boundY, Point2D start) {
        List<Point2D> hamiltonPath = new ArrayList<>();
        List<Direction> commandPath = new ArrayList<>();
        hamiltonPath.add(start);
        if (!hamiltonStep(hamiltonPath, commandPath, boundX, boundY, start)) {
            System.out.println("ме");
            return null;
        }
        return commandPath;
    }

    private static boolean hamiltonStep(List<Point2D> hamiltonPath, List<Direction> commandPath, int boundX, int boundY, Point2D current) {
        if (hamiltonPath.size() == boundX * boundY) {
            Point2D first = hamiltonPath.get(0);
            if (first.getX() == current.getX() && first.getY() == current.getY() - 1) {
                commandPath.add(Direction.UP);
                return true;
            } else if (first.getX() == current.getX() && first.getY() == current.getY() + 1) {
                commandPath.add(Direction.DOWN);
                return true;
            } else if (first.getX() == current.getX() - 1 && first.getY() == current.getY()) {
                commandPath.add(Direction.LEFT);
                return true;
            } else if (first.getX() == current.getX() + 1 && first.getY() == current.getY()) {
                commandPath.add(Direction.RIGHT);
                return true;
            }
        }
        List<Direction> directions = List.of(Direction.DOWN, Direction.LEFT, Direction.UP, Direction.RIGHT);
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
                commandPath.add(direction);
                hamiltonPath.add(newElement);
                if (hamiltonStep(hamiltonPath, commandPath, boundX, boundY, newElement)) {
                    return true;
                }
                commandPath.remove(direction);
                hamiltonPath.remove(newElement);
            }
        }
        return false;
    }
}
