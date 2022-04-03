package be.ugent.flash.QuestionManager;

import be.ugent.flash.SceneSwitcher.McsController;
import be.ugent.flash.jdbc.DataAccesException;
import be.ugent.flash.jdbc.Question;

public class McsFactory implements QuestionFactory {
    @Override
    public McsController CreateFlashcard(Question question, QuestionManager questionManager) {
        try {
            return new McsController(question,questionManager);
        } catch (DataAccesException e) {
            e.printStackTrace();
        }
        return null;
    }
}
