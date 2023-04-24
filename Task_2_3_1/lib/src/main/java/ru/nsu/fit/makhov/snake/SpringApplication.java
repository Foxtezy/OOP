package ru.nsu.fit.makhov.snake;

import javafx.application.Application;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan
public class SpringApplication {

    public static void main(String[] args) {
        Application.launch(JavaFXApplication.class, args);
    }
}
