package ru.nsu.fit.makhov.snake.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.nsu.fit.makhov.snake.model.GameField;

@Data
@AllArgsConstructor
public class GameViewDto {

    private int playerScore;
    private GameField gameField;
}
