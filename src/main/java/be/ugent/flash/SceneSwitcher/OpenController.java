package be.ugent.flash.SceneSwitcher;

import be.ugent.flash.SceneSwitcher.questionDataManager.GeneralQuestion;
import be.ugent.flash.jdbc.Question;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class OpenController extends QuestionController {
    @FXML
    public TextField answerField;
    public Label warning;
    private boolean disabled = false;

    public OpenController(Question question) {
        this.questionData = new GeneralQuestion(question);

    }

    public void initialize() {
        super.initialize();
        answerField.setOnKeyReleased(this::answer);
        warning.setVisible(false);
        answerField.setDisable(disabled);
    }

    @Override
    public void disable() {
        disabled = true;
    }

    //de gepaste waarschuwing komt op indien de tekst in het textfield niet is toegelaten
    public void answer(KeyEvent event) {
        if (answerField.getText().equals("")){
            warning.setVisible(true);
            this.question.getStyleClass().add("warning");
        } else {
            this.question.getStyleClass().remove("warning");
            warning.setVisible(false);
            if (event.getCode() == KeyCode.ENTER){
                this.correct = questionData.checkAnswer(answerField.getText());
                manager.next();
            }
        }
    }


    @Override
    public String getfxml() {
        return "Open.fxml";
    }


}
