package be.ugent.flash.SceneSwitcher;

import be.ugent.flash.QuestionManager.QuestionManager;
import be.ugent.flash.jdbc.Question;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class OpeniController extends OpenController{

    public OpeniController (Question question, QuestionManager manager, boolean prevCorrect) {
        super(question,manager,prevCorrect);

    }

    public void answer(KeyEvent event){
        super.warning.setText("Antwoord moet geheel getal zijn");
        if(answer.getText().matches("[0-9]*[^0-9]+[0-9]*")){

            warning.setVisible(true);
        } else {
            warning.setVisible(false);
            if (event.getCode()== KeyCode.ENTER){
                this.correct=questionData.checkAnswer(answer.getText());
                manager.next();
            }
        }
    }
}
