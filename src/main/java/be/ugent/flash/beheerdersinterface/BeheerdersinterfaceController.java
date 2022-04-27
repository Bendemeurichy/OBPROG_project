package be.ugent.flash.beheerdersinterface;

import be.ugent.flash.jdbc.DataAccesException;
import be.ugent.flash.jdbc.JDBCDataAccesProvider;
import be.ugent.flash.jdbc.Question;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
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
import java.util.Arrays;
import java.util.Map;

public class  BeheerdersinterfaceController extends MenuOpties{
    @FXML
    public TableView<Question> inhoud;
    public VBox fotobox;
    public HBox fotobuttons;
    public ImageView picturepart= new ImageView();
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
        if(observable.getValue()!=null){
            modifyQuestion.setAlignment(Pos.TOP_LEFT);
            algemeen.getChildren().clear();
            modifyQuestion.getChildren().clear();
            picturepart.setImage(null);
            picturepart.setUserData(null);
            modifyQuestion.getChildren().add(algemeen);
            algemeen.setHgap(10);
            algemeen.setVgap(10);

            algemeen.add(new Label("Titel"), 0, 0);
            TextField titel = new TextField(observable.getValue().title());
            algemeen.add(titel, 1, 0);
            algemeen.add(new Label("Type"), 0, 1);
            algemeen.add(new Label(typemap.get(observable.getValue().question_type())), 1, 1);
            algemeen.add(new Label("Tekst"), 0, 2);
            TextArea textpart = new TextArea(observable.getValue().text_part());
            textpart.setWrapText(true);
            algemeen.add(textpart, 1, 2);
            algemeen.add(new Label("Afbeelding"), 0, 3);
            fotobox = new VBox();
            fotobuttons = new HBox();
            VBox imagepart= new VBox(fotobox,fotobuttons);
            algemeen.add(imagepart, 1, 3);

            if (observable.getValue().image_part() == null){
                Button voegtoe = new Button("Voeg afbeelding toe");
                voegtoe.setOnAction(this::chooseImage);
                fotobox.getChildren().add(voegtoe);
            } else {
                picturepart.setUserData(observable.getValue().image_part());
                picturepart.setImage(new Image(new ByteArrayInputStream(observable.getValue().image_part())));
                showimage();
            }
            inhoud.disableProperty().bind(titel.textProperty().isNotEqualTo(observable.getValue().title()).
                    or(textpart.textProperty().isNotEqualTo(observable.getValue().text_part())).
                    or(Bindings.createBooleanBinding(this::ischanged)));
        } else {
            initialize();
        }
    }
    private boolean ischanged() {
        if (picturepart.getImage()==null && inhoud.getSelectionModel().getSelectedItem().image_part()==null){
            return false;
        } else if(picturepart.getImage()==null && inhoud.getSelectionModel().getSelectedItem().image_part()!=null){
            return true;
        } else if(picturepart.getImage()!=null && inhoud.getSelectionModel().getSelectedItem().image_part()==null){
            return true;
        }
        return  ! Arrays.equals(inhoud.getSelectionModel().getSelectedItem().image_part(),(byte[]) picturepart.getUserData());
    }

    private void removeImage(ActionEvent event) {
    }

    public void addQuestion(){}

    public void chooseImage(ActionEvent event) {
            FileChooser chooser= new FileChooser();
            chooser.setTitle("Kies een afbeelding (.jpeg of png)");
            chooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("jpeg files", "*.jpeg"),new FileChooser.ExtensionFilter("npg files","*.npg"));
            File file=chooser.showOpenDialog(pane.getScene().getWindow());
            if(file !=null){
                try {
                    fotobox.getChildren().clear();
                    fotobuttons.getChildren().clear();
                    byte[] bytes = Files.readAllBytes(Path.of(file.getPath()));
                    Image image=new Image(new ByteArrayInputStream(bytes));
                    picturepart.setUserData(bytes);
                    picturepart.setImage(image);
                    picturepart.setFitHeight(100);
                    picturepart.setFitWidth(150);
                    showimage();

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            ischanged();
            System.out.println(ischanged());
        }

    private void showimage() {
        fotobox.getChildren().add(picturepart);
        Button change_p =new Button("wijzig foto");
        Button delete_p=new Button("verwijder foto");
        change_p.setOnAction(this::chooseImage);
        delete_p.setOnAction(this::removeImage);
        fotobuttons.getChildren().addAll(change_p,delete_p);
    }


    public void removeQuestion(){
        new BeheerdersinterfaceCompanion().removeQuestion(this,inhoud,questiondb);
    }

}
