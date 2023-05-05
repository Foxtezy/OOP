package ru.nsu.fit.makhov.snake.model;

import java.util.Random;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.nsu.fit.makhov.snake.model.cell.AppleCell;
import ru.nsu.fit.makhov.snake.model.cell.EmptyCell;

@Component
@RequiredArgsConstructor
public class AppleSpawner {

    private final GameField gameField;

    private final Random random = new Random();

    private int countOfApples = 0;

    public void spawnAppleIfFieldEmpty() {
        if (countOfApples == 0) {
            spawnApple();
        }
    }

    public void decCountOfApples() {
        countOfApples--;
    }

    public void spawnApple() {
        int x;
        int y;
        do {
            x = random.nextInt(gameField.getSizeX());
            y = random.nextInt(gameField.getSizeY());
        } while (gameField.getCell(x, y).orElseThrow().getClass() != EmptyCell.class);
        gameField.setCell(new AppleCell(), x, y);
        countOfApples++;
    }
}
