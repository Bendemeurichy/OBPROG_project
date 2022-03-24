package be.ugent.flash.QuestionManager;

import be.ugent.flash.jdbc.Question;

public class MrFactory implements QuestionFactory{
    @Override
    public GeneralQuestion CreateFlashcard(Question question) {
        return null;
    }
}
