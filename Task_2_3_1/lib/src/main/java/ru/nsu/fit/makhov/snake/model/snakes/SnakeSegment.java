package ru.nsu.fit.makhov.snake.model.snakes;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import ru.nsu.fit.makhov.snake.model.event.Direction;

@Data
@RequiredArgsConstructor
public class SnakeSegment {

    private final int x;
    private final int y;

    public SnakeSegment(SnakeSegment head, Direction direction) {
        switch (direction) {
            case UP -> {
                this.x = head.x;
                this.y = head.y - 1;
            }
            case DOWN -> {
                this.x = head.x;
                this.y = head.y + 1;
            }
            case LEFT -> {
                this.x = head.x - 1;
                this.y = head.y;
            }
            case RIGHT -> {
                this.x = head.x + 1;
                this.y = head.y;
            }
            default -> {
                this.x = 0;
                this.y = 0;
            }
        }
    }
}
