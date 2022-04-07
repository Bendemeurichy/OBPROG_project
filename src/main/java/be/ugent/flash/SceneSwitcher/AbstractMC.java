package be.ugent.flash.SceneSwitcher;

import be.ugent.flash.QuestionManager.QuestionManager;
import be.ugent.flash.SceneSwitcher.questionDataManager.GeneralQuestion;
import be.ugent.flash.jdbc.Question;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;

public abstract class AbstractMC extends QuestionController {
    public AbstractMC(Question question, QuestionManager questionManager, boolean prevCorrect){
        this.prevCorrect=prevCorrect;
        this.questionData=new GeneralQuestion(question);
        this.manager=questionManager;
    }

    public void answer(ActionEvent event){
        Button called=(Button)event.getSource();
        this.correct=questionData.checkAnswer(""+called.getUserData());
        manager.next();

    }
}
