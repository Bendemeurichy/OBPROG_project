package be.ugent.flash.SceneSwitcher;

import be.ugent.flash.QuestionManager.QuestionManager;
import be.ugent.flash.SceneSwitcher.questionDataManager.GeneralQuestion;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

//algemene controllerklasse voor alle flashcards
public abstract class QuestionController {
    @FXML
    public Label wrongAnswer;
    public ImageView photoPart;
    public TextFlow textPart;


    protected GeneralQuestion questionData;
    protected QuestionManager manager;
    protected boolean correct;
    protected boolean prevCorrect;

    public abstract String getfxml();

    public void initialize(){
        wrongAnswer.setVisible(! prevCorrect);
        try {
            photoPart.setVisible(true);
            photoPart.setImage(questionData.getImage());
        } catch(NullPointerException e){
            photoPart.setVisible(false);
        }
        textPart.getChildren().add(new Text(questionData.getText()));
    }

    public boolean getCorrect(){return correct;}

    public String getTitle() {
        return questionData.getTitle();
    }

// keep a card to easily acces the data of the question+ all the fxml for a scene
}
