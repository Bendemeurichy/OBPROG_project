package be.ugent.flash.SceneSwitcher;

import be.ugent.flash.beheerdersinterface.BeheerdersinterfaceController;
import be.ugent.flash.beheerdersinterface.popups.ErrorDialog;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

/**
 * klasse om van scene te wisselen en title van applicatie aan te passen
 */
public class SceneManager {
    private final Stage stage;

    public SceneManager(Stage stage) {
        this.stage = stage;
    }

    public void changeScene(String scene, QuestionController controller, String title) {
        FXMLLoader fxmlLoader = new FXMLLoader(SceneManager.class.getResource(scene));
        fxmlLoader.setController(controller);
        Scene card = null;
        try {
            card = new Scene(fxmlLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setScene(card);
        stage.setTitle(title);
        stage.show();
    }

    public void loadDB(File file) {
        FXMLLoader fxmlLoader = new FXMLLoader(BeheerdersinterfaceController.class.getResource("Beheerdersinterface.fxml"));

        Scene scene;
        try {
            fxmlLoader.setController(new BeheerdersinterfaceController(file));
            scene = new Scene(fxmlLoader.load());
            stage.setScene(scene);
            stage.setTitle(file.getName());
            stage.show();
        } catch (IOException e) {
            new ErrorDialog().display(e.getMessage());
        }
    }
}
