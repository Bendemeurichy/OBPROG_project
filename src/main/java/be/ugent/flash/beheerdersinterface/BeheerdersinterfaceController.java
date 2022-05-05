package be.ugent.flash.beheerdersinterface;

import be.ugent.flash.beheerdersinterface.popups.ErrorDialog;
import be.ugent.flash.beheerdersinterface.popups.NewQuestionDialog;
import be.ugent.flash.beheerdersinterface.popups.Preview;
import be.ugent.flash.beheerdersinterface.questionparts.Partsloader;
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
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.Map;


/**
 * zeer grote klasse waar de meest javafx intensieve methodes worden verwerkt, zeer veel lijnen "opmaak" om de beheerdersinterface
 * er deftig uit te laten zien
 */
public class BeheerdersinterfaceController extends MenuOpties {
    @FXML
    public TableView<Question> contents;
    public TableColumn<Question, String> titel;
    public TableColumn<Question, String> type;
    public VBox photobox;
    public HBox photobuttons;
    public final ImageView picturepart = new ImageView();
    public final TextField titleEditor = new TextField();
    public final TextArea textpart = new TextArea();
    public final VBox answers = new VBox();
    public VBox modifyQuestion;
    public Button remove;
    public final GridPane general = new GridPane();
    private final File questiondb;
    private Question currentquestion;
    private Preview questionpreview;
    private final Partsloader partsloader = new Partsloader();
    @SuppressWarnings("FieldMayBeFinal")
    private final SimpleBooleanProperty changed = new SimpleBooleanProperty(false);

    private final Map<String, String> typemap = Map.of("mcs", "Meerkeuze (standaard)", "mcc", "Meerkeuze (compact)",
            "mci", "Meerkeuze (afbeeldingen)", "mr", "Meerantwoord", "open", "Open (tekst)", "openi", "Open (geheel)");

    public BeheerdersinterfaceController(File file) {
        this.questiondb = file;

    }

    //laad beheerdersinterface in, wordt ook gebruikt om tableview te refreshen
    public void initialize() {
        changed.set(false);
        contents.disableProperty().unbind();
        contents.setDisable(false);
        currentquestion = null;
        titleEditor.clear();
        textpart.clear();
        ObservableList<Question> questions;
        try {
            questions = FXCollections.observableArrayList(
                    new JDBCDataAccesProvider("jdbc:sqlite:" + questiondb.getPath()).getDataAccessContext().getQuestionDAO().allQuestionData());
        } catch (DataAccesException e) {
            //foutpopup
            throw new RuntimeException("kon vragen niet lezen");
        }
        titel.setCellValueFactory(q -> new SimpleStringProperty(q.getValue().title()));
        type.setCellValueFactory(q -> new SimpleStringProperty(typemap.get(q.getValue().question_type())));
        contents.setItems(questions);
        contents.getSelectionModel().selectedItemProperty().addListener(this::selectQuestion);
        remove.disableProperty().bind(contents.getSelectionModel().selectedItemProperty().isNull());
    }

    private void selectQuestion(Observable observable) {
        currentquestion = contents.getSelectionModel().getSelectedItem();
        loadQuestion(currentquestion);
    }

    //laad de gekozen vraag uit de tableview (zorgt dat alle knoppen juist worden gebind en gedisabled worden, roept partscontrollers op,...)
    public void loadQuestion(Question question) {
        if (question != null){
            answers.getChildren().clear();
            modifyQuestion.setAlignment(Pos.TOP_LEFT);
            general.getChildren().clear();
            modifyQuestion.getChildren().clear();
            picturepart.setImage(null);
            picturepart.setUserData(null);
            modifyQuestion.getChildren().add(general);
            general.setHgap(10);
            general.setVgap(10);
            modifyQuestion.setSpacing(5);
            picturepart.setPreserveRatio(true);
            picturepart.setFitHeight(100);
            picturepart.setFitWidth(150);
            modifyQuestion.setPadding(new Insets(5, 5, 5, 5));

            general.add(new Label("Titel"), 0, 0);
            titleEditor.setText(currentquestion.title());
            general.add(titleEditor, 1, 0);
            general.add(new Label("Type"), 0, 1);
            general.add(new Label(typemap.get(question.question_type())), 1, 1);
            general.add(new Label("Tekst"), 0, 2);
            textpart.setText(question.text_part());
            textpart.setWrapText(true);
            general.add(textpart, 1, 2);
            general.add(new Label("Afbeelding"), 0, 3);
            photobox = new VBox();
            photobuttons = new HBox();
            VBox imagepart = new VBox(photobox, photobuttons);
            general.add(imagepart, 1, 3);

            if (question.image_part() == null){
                Button voegtoe = new Button("Voeg afbeelding toe");
                voegtoe.setOnAction(new Imageparthandler(picturepart, photobox, photobuttons, pane, this)::chooseImage);
                photobox.getChildren().add(voegtoe);
            } else {
                picturepart.setUserData(question.image_part());
                picturepart.setImage(new Image(new ByteArrayInputStream(question.image_part())));
                new Imageparthandler(picturepart, photobox, photobuttons, pane, this).showimage();
            }
            modifyQuestion.getChildren().add(answers);
            partsloader.loadParts(answers, question, questiondb, this);
            HBox savebuttons = new HBox();
            Button save = new Button("opslaan");
            save.setOnAction(this::updateQuestion);
            Button recover = new Button("herstel");
            recover.setOnAction(this::restore);
            Button preview = new Button("preview");
            questionpreview = new Preview(pane);

            preview.setOnMousePressed(event -> showPreview());
            preview.setOnMouseReleased(event -> questionpreview.closePreview());
            preview.setOnMouseExited(mouseEvent -> questionpreview.closePreview());

            save.disableProperty().bind(contents.disableProperty().not());
            recover.disableProperty().bind(contents.disableProperty().not());
            savebuttons.getChildren().addAll(save, recover, preview);
            savebuttons.setAlignment(Pos.CENTER_RIGHT);
            modifyQuestion.getChildren().add(savebuttons);
            contents.disableProperty().bind((textpart.textProperty().isNotEqualTo(question.text_part())).
                    or(titleEditor.textProperty().isNotEqualTo(question.title()).or(changed))
            );
        } else {
            remove.disableProperty().bind(contents.getSelectionModel().selectedItemProperty().isNull());
            modifyQuestion.getChildren().clear();
            modifyQuestion.getChildren().add(new Label("(geen vraag geselecteerd)"));
            modifyQuestion.setAlignment(Pos.CENTER);
        }
    }

    private void restore(ActionEvent event) {
        changed.set(false);
        loadQuestion(currentquestion);
    }

    private void showPreview() {
        Question changedQuestion = new Question(currentquestion.question_id(), titleEditor.getText(), textpart.getText(), (byte[]) picturepart.getUserData(), currentquestion.question_type(), null);

        questionpreview.showPreview(changedQuestion, partsloader.getParts());
    }

    private void updateQuestion(ActionEvent event) {
        try {
            Question changedQuestion = new Question(currentquestion.question_id(), titleEditor.getText(), textpart.getText(), (byte[]) picturepart.getUserData(), currentquestion.question_type(), partsloader.getCorrectAnswer());
            new BeheerdersinterfaceCompanion().save(this, changedQuestion, questiondb, partsloader);
        } catch (IllegalArgumentException e) {
            new ErrorDialog().display(e.getMessage());
        }

    }

    //verandert de waarde van de booleanproperty die is gebind aan de disableproperty van de tableview
    public void ischanged() {
        changed.set(true);
    }

    public void addQuestion() {
        new NewQuestionDialog(this, contents, questiondb).display();
    }

    public void removeQuestion() {
        new BeheerdersinterfaceCompanion().removeQuestion(this, contents, questiondb);
    }
}
