package be.ugent.flash.SceneSwitcher;

import be.ugent.flash.SceneSwitcher.questionDataManager.GeneralQuestion;
import be.ugent.flash.jdbc.Question;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;

public abstract class AbstractMC extends QuestionController {
    public AbstractMC(Question question) {
        this.questionData = new GeneralQuestion(question);

    }

    public void answer(ActionEvent event) {
        Button called = (Button) event.getSource();
        //gebruik userdata om volgnummer op te vragen
        this.correct = questionData.checkAnswer("" + called.getUserData());
        manager.next();

    }
}
