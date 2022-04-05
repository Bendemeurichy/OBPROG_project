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

    public void answer(KeyEvent event){
        if(answer.getText().equals("")){
            warning.setVisible(true);
        } else{
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
