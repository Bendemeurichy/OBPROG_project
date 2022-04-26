package be.ugent.flash.beheerdersinterface;

import be.ugent.flash.jdbc.DataAccesContext;
import be.ugent.flash.jdbc.DataAccesException;
import be.ugent.flash.jdbc.JDBCDataAccesProvider;
import be.ugent.flash.jdbc.Question;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.Map;

public class  BeheerdersinterfaceController extends MenuOpties{
    @FXML
    public TableView<Question> inhoud;
    public TableColumn<Question,String> titel;
    public TableColumn<Question,String> type;
    public VBox modifyQuestion;
    public Button remove;
    public GridPane algemeen=new GridPane();
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
        inhoud.getSelectionModel().selectedItemProperty().addListener(this::loadQuestion);
        remove.disableProperty().bind(inhoud.getSelectionModel().selectedItemProperty().isNull());
        modifyQuestion.getChildren().clear();
        modifyQuestion.getChildren().add(new Label("(geen vraag geselecteerd)"));
        modifyQuestion.setAlignment(Pos.CENTER);
    }

    public void loadQuestion(ObservableValue<? extends Question> observable,Question oldvalue,Question newvalue ){
        modifyQuestion.setAlignment(Pos.TOP_LEFT);
        modifyQuestion.getChildren().clear();

        modifyQuestion.getChildren().add(algemeen);
        algemeen.setHgap(10);
        algemeen.setVgap(10);

        algemeen.add(new Label("Titel"),0,0);
        TextField titel=new TextField(observable.getValue().title());
        algemeen.add(titel,1,0);
        algemeen.add(new Label("Type"),0,1);
        algemeen.add(new Label(typemap.get(observable.getValue().question_type())),1,1);
        algemeen.add(new Label("Tekst"),0,2);
        TextArea textpart=new TextArea(observable.getValue().text_part());
        textpart.setWrapText(true);
        algemeen.add(textpart,1,2);
        algemeen.add(new Label("Afbeelding"),0,3);

        //Bind niet aan disableproperty
        inhoud.disableProperty().bind(titel.textProperty().isNotEqualTo(observable.getValue().title()));
        inhoud.disableProperty().bind(textpart.textProperty().isNotEqualTo(observable.getValue().text_part()));
        if(observable.getValue().image_part()==null){
            Button voegtoe=new Button("Voeg afbeelding toe");
            voegtoe.setOnAction(this::chooseImage);
            algemeen.add(voegtoe,1,3);
        } else {
            //TODO complete
        }
        algemeen.autosize();

    }



    public void addQuestion(ActionEvent event){}

    public void removeQuestion(){
        int questionId=inhoud.getSelectionModel().getSelectedItem().question_id();
        try{
            DataAccesContext dp = new JDBCDataAccesProvider("jdbc:sqlite:"+questiondb.getPath()).getDataAccessContext();
            dp.getPartDAO().removeParts(questionId);
            dp.getQuestionDAO().removeQuestion(questionId);
        } catch (DataAccesException e) {
            throw new RuntimeException(e);
        }
        initialize();
    }

    public void save(){
        //update db
        initialize();
    }

    public void chooseImage(ActionEvent event) {
        Button eventcall=((Button) event.getSource());
        if(eventcall.getText().equals("Voeg afbeelding toe")){
            FileChooser chooser= new FileChooser();
            chooser.setTitle("Kies een afbeelding (.jpeg of png)");
            chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("jpeg files", "*.jpeg"));
            File file=chooser.showOpenDialog(pane.getScene().getWindow());
            if(file !=null){
                //TODO show image en complete
                algemeen.getChildren().remove(eventcall);;
            }


        }


    }


}
