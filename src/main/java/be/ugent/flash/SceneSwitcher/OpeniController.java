package be.ugent.flash.SceneSwitcher;

import be.ugent.flash.jdbc.Question;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class OpeniController extends OpenController{

    public OpeniController (Question question) {
        super(question);

    }
    public void answer(KeyEvent event){
        super.warning.setText("Antwoord moet geheel getal zijn");
        if(! answerField.getText().matches("^-?\\d+$")){
            this.question.getStyleClass().add("warning");
            warning.setVisible(true);
        } else {
            this.question.getStyleClass().remove("warning");
            warning.setVisible(false);
            if (event.getCode()== KeyCode.ENTER){
                this.correct=questionData.checkAnswer(answerField.getText());
                manager.next();
            }
        }
    }
}
