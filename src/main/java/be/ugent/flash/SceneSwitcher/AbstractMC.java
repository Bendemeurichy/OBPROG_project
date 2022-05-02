package be.ugent.flash.SceneSwitcher;

import be.ugent.flash.QuestionManager.QuestionManager;
import be.ugent.flash.SceneSwitcher.questionDataManager.GeneralQuestion;
import be.ugent.flash.jdbc.Question;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;

import java.util.ArrayList;

public abstract class AbstractMC extends QuestionController {
    public AbstractMC(Question question, ArrayList parts){
        this.questionData=new GeneralQuestion(question);

    }

    @Override
    public void makequiz(QuestionManager questionManager, boolean prevCorrect) {
        super.makequiz(questionManager, prevCorrect);
    }

    public void answer(ActionEvent event){
        Button called=(Button)event.getSource();
        //gebruik userdata om volgnummer op te vragen
        this.correct=questionData.checkAnswer(""+called.getUserData());
        manager.next();

    }
}
