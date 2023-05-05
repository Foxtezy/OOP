package ru.nsu.fit.makhov.snake.view;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import lombok.experimental.UtilityClass;
import ru.nsu.fit.makhov.snake.model.cell.AppleCell;
import ru.nsu.fit.makhov.snake.model.cell.Cell;
import ru.nsu.fit.makhov.snake.model.cell.SnakeCell;

@UtilityClass
public class TileFactory {

    public static Node createTile(Class<? extends Cell> cellClass) {
        if (cellClass == AppleCell.class) {
            return new Rectangle(20, 20, Color.RED);
        }
        if (cellClass == SnakeCell.class) {
            return new Rectangle(20, 20, Color.BLUE);
        }
        return new Rectangle(20, 20, Color.GRAY);
    }

}
