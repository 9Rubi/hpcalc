package ink.rubi.calc;

import ink.rubi.calc.config.GUIConfig;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author : Rubi
 * @version : 2019-05-17 01:51 上午
 */
@Getter
public class App extends Application {
    private static final Logger log = LoggerFactory.getLogger("[MAIN]");

    private Stage window;
    private Scene scene;
    private Parent layout;

    public static void main(String[] args) {
        log.info("loading conf.properties");
        GUIConfig.init();
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/App.fxml"));
        layout = fxmlLoader.load();
        window.setResizable(false);
        window.setTitle("压血计算器");

        scene = new Scene(layout);
        window.setScene(scene);
        window.show();
    }

}