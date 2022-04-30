package be.ugent.flash.beheerdersinterface;

import be.ugent.flash.jdbc.DataAccesException;
import be.ugent.flash.jdbc.JDBCDataAccesProvider;
import be.ugent.flash.jdbc.Question;
import javafx.beans.Observable;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
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
    public TextField titleEditor=new TextField();
    public TextArea textpart = new TextArea();
    public TableColumn<Question,String> titel;
    public VBox answers = new VBox();
    public TableColumn<Question,String> type;
    public VBox modifyQuestion;
    public Button remove;
    public GridPane algemeen=new GridPane();
    private final File questiondb;
    private  Question currentquestion;

    private final Map<String,String> typemap= Map.of("mcs","Meerkeuze (standaard)","mcc","Meerkeuze (compact)",
            "mci","Meerkeuze (afbeeldingen)","mr","Meerantwoord","open","Open (tekst)","openi","Open (geheel)");
    public BeheerdersinterfaceController(File file){
        this.questiondb=file;

    }

    public void initialize() {
        inhoud.disableProperty().unbind();
        inhoud.setDisable(false);
        currentquestion=null;
        titleEditor.clear();
        textpart.clear();
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
        inhoud.getSelectionModel().selectedItemProperty().addListener(this::selectQuestion);
        remove.disableProperty().bind(inhoud.getSelectionModel().selectedItemProperty().isNull());
    }

    private void selectQuestion(Observable observable) {
        currentquestion=inhoud.getSelectionModel().getSelectedItem();
        loadQuestion(currentquestion);
    }

    public void loadQuestion(Question question){
        if(question!=null){
            modifyQuestion.setAlignment(Pos.TOP_LEFT);
            algemeen.getChildren().clear();
            modifyQuestion.getChildren().clear();
            picturepart.setImage(null);
            picturepart.setUserData(null);
            modifyQuestion.getChildren().add(algemeen);
            algemeen.setHgap(10);
            algemeen.setVgap(10);
            picturepart.setPreserveRatio(true);
            picturepart.setFitHeight(100);
            picturepart.setFitWidth(150);

            algemeen.add(new Label("Titel"), 0, 0);
            titleEditor.setText(currentquestion.title());
            algemeen.add(titleEditor, 1, 0);
            algemeen.add(new Label("Type"), 0, 1);
            algemeen.add(new Label(typemap.get(question.question_type())), 1, 1);
            algemeen.add(new Label("Tekst"), 0, 2);
            textpart.setText(question.text_part());
            textpart.setWrapText(true);
            algemeen.add(textpart, 1, 2);
            algemeen.add(new Label("Afbeelding"), 0, 3);
            fotobox = new VBox();
            fotobuttons = new HBox();
            VBox imagepart= new VBox(fotobox,fotobuttons);
            algemeen.add(imagepart, 1, 3);

            if (question.image_part() == null){
                Button voegtoe = new Button("Voeg afbeelding toe");
                voegtoe.setOnAction(this::chooseImage);
                fotobox.getChildren().add(voegtoe);
            } else {
                picturepart.setUserData(question.image_part());
                picturepart.setImage(new Image(new ByteArrayInputStream(question.image_part())));
                showimage();
            }
            algemeen.add(answers,0,4);
            new BeheerdersinterfaceCompanion().loadParts(answers,question);
            HBox savebuttons=new HBox();
            Button save=new Button("opslaan");
            save.setOnAction(this::updateQuestion);
            Button recover=new Button("herstel");
            recover.setOnAction(event -> loadQuestion(currentquestion));
            Button preview=new Button("preview");
            preview.setOnAction(event ->showPreview());

            save.disableProperty().bind(inhoud.disableProperty().not());
            recover.disableProperty().bind(inhoud.disableProperty().not());
            savebuttons.getChildren().addAll(save,recover,preview);
            modifyQuestion.getChildren().add(savebuttons);
            inhoud.disableProperty().bind(titleEditor.textProperty().isNotEqualTo(question.title()).
                  or(textpart.textProperty().isNotEqualTo(question.text_part())).
                    or(new SimpleBooleanProperty(ischanged())));
        } else {
            remove.disableProperty().bind(inhoud.getSelectionModel().selectedItemProperty().isNull());
            modifyQuestion.getChildren().clear();
            modifyQuestion.getChildren().add(new Label("(geen vraag geselecteerd)"));
            modifyQuestion.setAlignment(Pos.CENTER);
        }
    }

    private void showPreview() {
    }

    private void updateQuestion(ActionEvent event) {
        Question changedQuestion= new Question(currentquestion.question_id(),titleEditor.getText(),textpart.getText(), (byte[]) picturepart.getUserData(), currentquestion.question_type(),null);
        currentquestion=null;
        new BeheerdersinterfaceCompanion().save(this,changedQuestion,questiondb);
    }

    private boolean ischanged() {
        if (picturepart.getImage()==null && currentquestion.image_part()==null){
            return false;
        } else if(picturepart.getImage()==null && currentquestion.image_part()!=null){
            return true;
        } else if(picturepart.getImage()!=null && currentquestion.image_part()==null){
            return true;
        }
        return  ! Arrays.equals(currentquestion.image_part(),(byte[]) picturepart.getUserData());
    }

    private void removeImage() {
        picturepart.setImage(null);
        picturepart.setUserData(null);
        fotobox.getChildren().clear();
        fotobuttons.getChildren().clear();
        Button voegtoe = new Button("Voeg afbeelding toe");
        voegtoe.setOnAction(this::chooseImage);
        fotobox.getChildren().add(voegtoe);
        ischanged();
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
                    showimage();

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            ischanged();
        }

    private void showimage() {
        fotobox.getChildren().add(picturepart);
        Button change_p =new Button("wijzig foto");
        Button delete_p=new Button("verwijder foto");
        change_p.setOnAction(this::chooseImage);
        delete_p.setOnAction(event -> removeImage());
        fotobuttons.getChildren().addAll(change_p,delete_p);
    }


    public void removeQuestion(){
        new BeheerdersinterfaceCompanion().removeQuestion(this,inhoud,questiondb);
    }

}
