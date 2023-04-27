package ru.nsu.fit.makhov.snake;

import java.io.IOException;
import java.net.URL;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class SpringFXMLLoader {

    private final ApplicationContext context;

    @Autowired
    public SpringFXMLLoader(ApplicationContext context) {
        this.context = context;
    }

    public Parent load(URL url) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setControllerFactory(context::getBean); //Spring now FXML Controller Factory
        loader.setLocation(url);
        return loader.load();
    }

}
