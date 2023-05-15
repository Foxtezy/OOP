package ru.nsu.fit.makhov.snake;

import java.io.IOException;
import java.net.URL;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * Spring FXMLLoader.
 */
@Component
public class SpringFxmlLoader {

  private final ApplicationContext context;

  @Autowired
  public SpringFxmlLoader(ApplicationContext context) {
    this.context = context;
  }

  /**
   * Load fxml.
   *
   * @param url fxml url.
   * @return Parent.
   * @throws IOException exception.
   */
  public Parent load(URL url) throws IOException {
    FXMLLoader loader = new FXMLLoader();
    loader.setControllerFactory(context::getBean); //Spring now FXML Controller Factory
    loader.setLocation(url);
    return loader.load();
  }

}
