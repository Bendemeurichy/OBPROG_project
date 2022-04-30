package be.ugent.flash.beheerdersinterface;

import be.ugent.flash.SceneSwitcher.SceneManager;
import be.ugent.flash.db.CreateTables;
import javafx.application.Platform;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MenuOpties {
    public GridPane pane;
    public void exitProgram(){
        Platform.exit();
    }

    public void nieuwDB() throws SQLException {
        FileChooser chooser= new FileChooser();
        chooser.setTitle("Kies de naam voor een nieuwe databank (.sqlite extension)");
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("sqlite files","*.sqlite"));
        File file= chooser.showSaveDialog(pane.getScene().getWindow());
        if(file != null){
            Connection connection= DriverManager.getConnection("jdbc:sqlite:"+file.getPath());
            CreateTables.createQuestionsTable(connection);
            CreateTables.createPartsTable(connection);
            new SceneManager(((Stage) pane.getScene().getWindow())).loadDB(file);
        }
        //Todo: open filechooser om nieuw bestand te open en wissel naar geopend bestand: save dialog sqlite::name
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
