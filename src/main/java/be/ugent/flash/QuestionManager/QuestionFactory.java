package be.ugent.flash.QuestionManager;

import be.ugent.flash.jdbc.Question;

public interface QuestionFactory {
    GeneralQuestion CreateFlashcard(Question question);
}
