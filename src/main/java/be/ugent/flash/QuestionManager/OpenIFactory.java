package be.ugent.flash.QuestionManager;

import be.ugent.flash.SceneSwitcher.OpeniController;
import be.ugent.flash.SceneSwitcher.QuestionController;
import be.ugent.flash.jdbc.Question;

public class OpenIFactory implements QuestionFactory{
    @Override
    public QuestionController CreateFlashcard(Question question, QuestionManager questionManager, boolean prevCorrect) {
        return new OpeniController(question,questionManager,prevCorrect);
    }
}
