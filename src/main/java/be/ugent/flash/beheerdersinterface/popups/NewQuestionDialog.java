package be.ugent.flash.beheerdersinterface.popups;
//source popupwindow:http://www.learningaboutelectronics.com/Articles/How-to-create-a-pop-up-window-in-JavaFX.php

import be.ugent.flash.beheerdersinterface.BeheerdersinterfaceController;
import be.ugent.flash.jdbc.DataAccesException;
import be.ugent.flash.jdbc.JDBCDataAccesProvider;
import be.ugent.flash.jdbc.Question;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.util.Map;

public class NewQuestionDialog {
    private final Map<String,String> typemap= Map.of("Meerkeuze (standaard)","mcs","Meerkeuze (compact)","mcc",
            "Meerkeuze (afbeeldingen)","mci","Meerantwoord","mr","Open (tekst)","open","Open (geheel)","openi");
    private final BeheerdersinterfaceController controller;
    private final TableView<Question> tableview;
    private final File db;

    @FXML
    public final ComboBox<String> types=new ComboBox<>();
    public final TextField questiontitle=new TextField();

    public NewQuestionDialog(BeheerdersinterfaceController controller, TableView<Question> tableView, File db){
        this.db=db;
        this.controller=controller;
        this.tableview=tableView;
    }
    public void display(){
        Stage popupwindow=new Stage();

        popupwindow.initModality(Modality.APPLICATION_MODAL);
        popupwindow.setTitle("Maak nieuwe vraag");

        VBox layout=new VBox();
        layout.setAlignment(Pos.CENTER);

        Label newquestion=new Label("Nieuwe vraag toevoegen");
        newquestion.setFont(Font.font(null,FontWeight.BOLD,20));

        GridPane arguments=new GridPane();
        Label title=new Label("Titel");
        arguments.add(title,0,0);
        arguments.add(questiontitle,1,0);

        Label type= new Label("Type");
        arguments.add(type,0,1);

        types.getItems().addAll("Meerkeuze (standaard)","Meerkeuze (compact)","Meerkeuze (afbeeldingen)","Meerantwoord",
                "Open (tekst)","Open (geheel)");
        types.getSelectionModel().select(0);
        arguments.add(types,1,1);

        HBox buttons=new HBox();
        Button save=new Button("OK");
        Button cancel=new Button("Annuleren");
        save.setOnAction(this::makequestion);
        save.disableProperty().bind(questiontitle.textProperty().isEmpty());
        cancel.setOnAction(event -> popupwindow.close());
        buttons.getChildren().addAll(save,cancel);
        buttons.setAlignment(Pos.CENTER_RIGHT);
        buttons.setSpacing(5);
        arguments.add(buttons,1,2);

        arguments.setAlignment(Pos.CENTER);
        arguments.setVgap(5);
        arguments.setHgap(10);

        layout.getChildren().addAll(newquestion,arguments);
        Scene scene=new Scene(layout,300,200);
        popupwindow.setScene(scene);
        popupwindow.showAndWait();
    }

    private void makequestion(ActionEvent event) {
        try {
            Question addedQuesiton=new JDBCDataAccesProvider("jdbc:sqlite:"+db.getPath()).getDataAccessContext().getQuestionDAO().addQuestion(questiontitle.getText(),typemap.get(types.getSelectionModel().getSelectedItem()));
            controller.initialize();
            tableview.getSelectionModel().select(addedQuesiton);
            ((Stage) questiontitle.getScene().getWindow()).close();
        } catch (DataAccesException e) {
            throw new RuntimeException(e);
        }
    }
}
