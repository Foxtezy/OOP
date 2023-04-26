package ru.nsu.fit.makhov.snake.model.event;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import ru.nsu.fit.makhov.snake.model.snakes.AbstractSnake;

@Data
public class MoveEvent {

    private final AbstractSnake snake;
    private final Direction direction;

}
