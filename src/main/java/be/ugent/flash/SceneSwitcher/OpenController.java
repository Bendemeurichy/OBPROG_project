package be.ugent.flash.SceneSwitcher;

import be.ugent.flash.QuestionManager.QuestionManager;
import be.ugent.flash.SceneSwitcher.questionDataManager.GeneralQuestion;
import be.ugent.flash.jdbc.Question;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class OpenController extends QuestionController{
    @FXML
    public TextField answer;
    public Label warning;

    public OpenController(Question question, QuestionManager manager,boolean prevCorrect){
        this.questionData=new GeneralQuestion(question);
        this.prevCorrect=prevCorrect;
        this.manager=manager;
    }

    public void initialize(){
        super.initialize();
        answer.setOnKeyReleased(this::answer);
        warning.setVisible(false);
    }

    //de gepaste waarschuwing komt op indien de tekst in het textfield niet is toegelaten
    public void answer(KeyEvent event){
        if(answer.getText().equals("")){
            warning.setVisible(true);
            this.question.getStyleClass().add("warning");
        } else{
            this.question.getStyleClass().remove("warning");
            warning.setVisible(false);
            if (event.getCode()== KeyCode.ENTER){
                this.correct=questionData.checkAnswer(answer.getText());
                manager.next();
            }
        }
    }


    @Override
    public String getfxml() {
        return "Open.fxml";
    }
}
