package be.ugent.flash.SceneSwitcher;

import be.ugent.flash.QuestionManager.QuestionManager;
import be.ugent.flash.SceneSwitcher.questionDataManager.GeneralQuestion;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextFlow;

//algemene controllerklasse voor alle flashcards
public abstract class QuestionController {
    @FXML
    public ImageView photoPart;
    public TextFlow textPart;
    public VBox answers;
    public VBox buttons;



    protected GeneralQuestion questionData;
    protected QuestionManager manager;
    protected boolean correct;
    public abstract String getfxml();

    public boolean getCorrect(){return correct;}

    public String getTitle() {
        return questionData.getTitle();
    }

// keep a card to easily acces the data of the question+ all the fxml for a scene
}
