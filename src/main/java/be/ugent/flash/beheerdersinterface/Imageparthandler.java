package be.ugent.flash.beheerdersinterface;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * klasse verwerkt de meeste afbeeldinggerelateerde methodes om het photopart van de vragen in orde te brengen
 */
public class Imageparthandler {
    private final ImageView picturepart;
    private final VBox photobox;
    private final HBox photobuttons;
    private final GridPane pane;
    private final BeheerdersinterfaceController controller;

    public Imageparthandler(ImageView picturepart, VBox photobox, HBox photobuttons, GridPane pane, BeheerdersinterfaceController controller) {

        this.picturepart = picturepart;
        this.photobox = photobox;
        this.photobuttons = photobuttons;
        this.pane = pane;
        this.controller = controller;
    }

    public void removeImage() {
        picturepart.setImage(null);
        picturepart.setUserData(null);
        photobox.getChildren().clear();
        photobuttons.getChildren().clear();
        Button voegtoe = new Button("Voeg afbeelding toe");
        voegtoe.setOnAction(this::chooseImage);
        photobox.getChildren().add(voegtoe);
        controller.ischanged();
    }

    public void chooseImage(ActionEvent event) {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Kies een afbeelding (.jpeg of png)");
        chooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("jpeg files", "*.jpeg", "*jpg"), new FileChooser.ExtensionFilter("npg files", "*.npg"));
        File file = chooser.showOpenDialog(pane.getScene().getWindow());
        if (file != null){
            try {
                photobox.getChildren().clear();
                photobuttons.getChildren().clear();
                byte[] bytes = Files.readAllBytes(Path.of(file.getPath()));
                Image image = new Image(new ByteArrayInputStream(bytes));
                picturepart.setUserData(bytes);
                picturepart.setImage(image);
                showimage();
                controller.ischanged();

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }

    public void showimage() {
        photobox.getChildren().add(picturepart);
        Button change_p = new Button("wijzig foto");
        Button delete_p = new Button("verwijder foto");
        change_p.setOnAction(this::chooseImage);
        delete_p.setOnAction(event -> removeImage());
        photobuttons.getChildren().addAll(change_p, delete_p);
    }
}
