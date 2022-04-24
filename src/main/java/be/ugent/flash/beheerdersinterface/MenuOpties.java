package be.ugent.flash.beheerdersinterface;

import be.ugent.flash.SceneSwitcher.SceneManager;
import javafx.application.Platform;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class MenuOpties {
    public GridPane pane;
    public void exitProgram(){
        Platform.exit();
    }

    public void nieuwDB(){
        //Todo: open filechooser om nieuw bestand te open en wissel naar geopend bestand
    }

    public void openDB(){
        FileChooser chooser= new FileChooser();
        chooser.setTitle("Open bestaande databank (.sqlite extension)");
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("sqlite files", "*.sqlite"));
        File file=chooser.showOpenDialog(pane.getScene().getWindow());
        if(file !=null){
            new SceneManager((Stage) pane.getScene().getWindow()).loadDB(file);
        }
    }


}
