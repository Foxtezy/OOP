package ru.nsu.fit.makhov.snake.model;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;
import ru.nsu.fit.makhov.snake.model.cell.Cell;
import ru.nsu.fit.makhov.snake.model.cell.EmptyCell;

@Component
public class GameField {

    private List<List<Cell>> field = new ArrayList<>();
    
    public void init(int sizeX, int sizeY) {
        for (int i = 0; i < sizeX; i++) {
            field.add(i, new ArrayList<>());
            for (int j = 0; j < sizeY; j++) {
                field.get(i).add(j, new EmptyCell());
            }
        }
    }

    public GameField() {
    }

    public GameField(GameField gameField) {
        for (List<Cell> cl : gameField.field) {
            this.field.add(new ArrayList<>(cl));
        }
    }

    public Cell getCell(int x, int y) {
        return field.get(x).get(y);
    }

    public Cell setCell(Cell cell, int x, int y) {
        return field.get(x).set(y, cell);
    }

}
