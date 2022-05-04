package be.ugent.flash.beheerdersinterface.popups;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * popupscherm dat een bericht neemt met de methode display die dan de gepaste melding geeft aan de gebruiker
 * als deze iets verkeerd heeft gedaan (verkeerd antwoord,....)
 */
public class ErrorDialog {
    @FXML
    public final Button ok=new Button("OK");
    public final Label errormessage=new Label();

    public void display(String message) {
        Stage popupwindow =new Stage();
        popupwindow.setResizable(false);
        VBox box=new VBox(errormessage,ok);
        box.setAlignment(Pos.CENTER);
        box.setSpacing(15);
        box.setPadding(new Insets(5,5,5,5));
        box.setBackground(new Background(new BackgroundFill(Color.rgb(255,160,160), CornerRadii.EMPTY, Insets.EMPTY)));
        errormessage.setTextFill(Color.rgb(255,255,255));
        errormessage.setTextAlignment(TextAlignment.CENTER);
        errormessage.setWrapText(true);
        errormessage.setFont(Font.font(null,FontWeight.BOLD,14));
        box.setPrefSize(275,150);
        popupwindow.setScene(new Scene(box));
        popupwindow.setTitle("QuestionError");
        errormessage.setText(message);
        ok.setOnAction(event -> popupwindow.close());
        ok.setDefaultButton(true);
        popupwindow.initModality(Modality.APPLICATION_MODAL);
        popupwindow.showAndWait();

    }

}
