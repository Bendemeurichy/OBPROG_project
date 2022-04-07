package be.ugent.flash.SceneSwitcher;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * klasse om van scene te wisselen en title van applicatie aan te passen
 */
public class SceneManager {
    private final Stage stage;

    public SceneManager(Stage stage){
        this.stage=stage;
    }

    public void changeScene(String scene,QuestionController controller,String title) {
        FXMLLoader fxmlLoader= new FXMLLoader(SceneManager.class.getResource(scene));
        fxmlLoader.setController(controller);
        Scene card= null;
        try {
            card = new Scene(fxmlLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setScene(card);
        stage.setTitle(title);
        stage.show();
    }
}
