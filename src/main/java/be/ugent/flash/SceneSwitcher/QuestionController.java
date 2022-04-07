package be.ugent.flash.SceneSwitcher;

import be.ugent.flash.QuestionManager.QuestionManager;
import be.ugent.flash.SceneSwitcher.questionDataManager.GeneralQuestion;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

/**
 * algemene controllerklasse voor alle flashcards.
 * zorgt voor fxml en het checken van antwoorden
 */
public abstract class QuestionController {
    @FXML
    public Label wrongAnswer;
    public ImageView photoPart;
    public TextFlow textPart;
    public VBox question;


    protected GeneralQuestion questionData;
    protected QuestionManager manager;
    protected boolean correct;
    protected boolean prevCorrect;

    public abstract String getfxml();

    public void initialize(){
        wrongAnswer.setVisible(! prevCorrect);
        try {
            photoPart.setImage(questionData.getImage());
        } catch(NullPointerException e){
            question.getChildren().remove(photoPart);
        }
        textPart.getChildren().add(new Text(questionData.getText()));
    }

    public boolean getCorrect(){return correct;}

    public String getTitle() {
        return questionData.getTitle();
    }

}
