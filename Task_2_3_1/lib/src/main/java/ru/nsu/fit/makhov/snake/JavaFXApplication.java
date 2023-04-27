package ru.nsu.fit.makhov.snake;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class JavaFXApplication extends Application {

    private ConfigurableApplicationContext applicationContext;

    private SpringFXMLLoader springFXMLLoader;

    @Override
    public void init() {
        this.applicationContext = new SpringApplicationBuilder(SpringApplication.class).run();
        this.springFXMLLoader = applicationContext.getBean(SpringFXMLLoader.class);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = springFXMLLoader.load(getClass().getResource("view/main-view.fxml"));
        Scene scene = new Scene(root);
        stage.setTitle("Snake");
        stage.setScene(scene);
        stage.setWidth(800);
        stage.setHeight(600);
        stage.show();
    }

    @Override
    public void stop() {
        this.applicationContext.close();
        Platform.exit();
    }
}
