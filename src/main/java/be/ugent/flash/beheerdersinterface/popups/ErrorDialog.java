package be.ugent.flash.beheerdersinterface.popups;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class ErrorDialog {
    @FXML
    public Button ok;
    public Label errormessage;

    public void display(String message) throws IOException {
        Stage popupwindow =new Stage();
        popupwindow.setResizable(false);
        FXMLLoader fxmlLoader= new FXMLLoader(ErrorDialog.class.getResource("Errordialog.fxml"));
        popupwindow.setScene(fxmlLoader.load());
        popupwindow.setTitle("QuestionError");
        errormessage.setText(message);
        ok.setOnAction(event -> popupwindow.close());
        ok.setDefaultButton(true);
        popupwindow.show();

    }
}
