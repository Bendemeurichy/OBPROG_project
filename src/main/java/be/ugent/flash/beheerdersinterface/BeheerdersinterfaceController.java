package be.ugent.flash.beheerdersinterface;

import be.ugent.flash.jdbc.DataAccesException;
import be.ugent.flash.jdbc.JDBCDataAccesProvider;
import be.ugent.flash.jdbc.Question;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;

import java.io.File;
import java.util.Map;

public class  BeheerdersinterfaceController extends MenuOpties{
    @FXML
    public TableView<Question> inhoud;
    public TableColumn<Question,String> titel;
    public TableColumn<Question,String> type;
    public VBox modifyQuestion;
    public Button remove;

    private final File questiondb;
    private final Map<String,String> typemap= Map.of("mcs","Meerkeuze (standaard)","mcc","Meerkeuze (compact)",
            "mci","Meerkeuze (afbeeldingen)","mr","Meerantwoord","open","Open (tekst)","openi","Open (geheel)");
    public BeheerdersinterfaceController(File file){
        this.questiondb=file;

    }

    public void initialize() {
        ObservableList<Question> questions;
        try {
            questions = FXCollections.observableArrayList(
                    new JDBCDataAccesProvider("jdbc:sqlite:" + questiondb.getPath()).getDataAccessContext().getQuestionDAO().allQuestionData());
        } catch (DataAccesException e){
            //foutpopup
            throw new RuntimeException("kon vragen niet lezen");
        }
        titel.setCellValueFactory(q -> new SimpleStringProperty(q.getValue().title()));
        type.setCellValueFactory(q -> new SimpleStringProperty(typemap.get(q.getValue().question_type())));
        inhoud.setItems(questions);
        inhoud.getSelectionModel().selectedItemProperty().addListener(this::bewerk);
        remove.disableProperty().bind(inhoud.getSelectionModel().selectedItemProperty().isNull());
    }

    public void bewerk(ObservableValue<? extends Question> observable,Question oldvalue,Question newvalue ){
        System.out.println(observable.toString());
        modifyQuestion.getChildren().clear();
    }

    public void addQuestion(ActionEvent event){}

    public void removeQuestion(ActionEvent event){}

    public void save(){
        //update db
        initialize();
    }
}
