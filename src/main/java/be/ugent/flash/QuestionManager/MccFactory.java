package be.ugent.flash.QuestionManager;

import be.ugent.flash.SceneSwitcher.QuestionController;
import be.ugent.flash.jdbc.Question;

public class MccFactory implements QuestionFactory{
    @Override
    public QuestionController CreateFlashcard(Question question, QuestionManager questionManager) {
        return null;
    }
}
