package pl.polsl.jira_with_gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import pl.polsl.model.Model;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    
    private static Model model;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("MainMenu"), 720, 370);
        stage.setScene(scene);
        stage.show();
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        fxmlLoader.setControllerFactory( p -> { return new MainMenuController(model);} );
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        model = new Model();
        launch();
    }

}