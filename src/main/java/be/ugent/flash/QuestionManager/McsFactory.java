package be.ugent.flash.QuestionManager;

import be.ugent.flash.SceneSwitcher.McsController;
import be.ugent.flash.SceneSwitcher.QuestionController;
import be.ugent.flash.jdbc.DataAccesException;
import be.ugent.flash.jdbc.Question;

public class McsFactory implements QuestionFactory {
    @Override
    public QuestionController CreateFlashcard(Question question, QuestionManager questionManager) {
        try {
            return new McsController(question,questionManager);
        } catch (DataAccesException e) {
            e.printStackTrace();
        }
        return null;
    }
}
