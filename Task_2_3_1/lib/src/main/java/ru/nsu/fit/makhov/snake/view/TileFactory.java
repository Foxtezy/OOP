package ru.nsu.fit.makhov.snake.view;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import lombok.experimental.UtilityClass;
import ru.nsu.fit.makhov.snake.model.cell.Cell;

@UtilityClass
public class TileFactory {

    public static Rectangle createTile(Cell cell) {
        return switch (cell.getCellType()) {
            case APPLE -> new Rectangle(20, 20, Color.RED);
            case SNAKE -> new Rectangle(20, 20, Color.BLUE);
            case EMPTY -> new Rectangle(20, 20, Color.GRAY);
        };
    }
}
