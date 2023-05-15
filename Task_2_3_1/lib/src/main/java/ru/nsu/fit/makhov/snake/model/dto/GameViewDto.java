package ru.nsu.fit.makhov.snake.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.nsu.fit.makhov.snake.model.GameField;

/**
 * Dto for sending game state from model to view.
 */
@Data
@AllArgsConstructor
public class GameViewDto {

  private int playerScore;
  private GameField gameField;
}
