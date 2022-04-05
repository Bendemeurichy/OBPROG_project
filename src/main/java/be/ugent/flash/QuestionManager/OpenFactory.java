package be.ugent.flash.QuestionManager;

import be.ugent.flash.SceneSwitcher.OpenController;
import be.ugent.flash.SceneSwitcher.QuestionController;
import be.ugent.flash.jdbc.Question;

public class OpenFactory implements QuestionFactory {
    @Override
    public QuestionController CreateFlashcard(Question question, QuestionManager questionManager, boolean prevCorrect) {
        return new OpenController(question,questionManager,prevCorrect);
    }
}
