package be.ugent.flash.QuestionManager;

import be.ugent.flash.SceneSwitcher.QuestionController;
import be.ugent.flash.jdbc.Question;

public interface QuestionFactory {
    QuestionController CreateFlashcard(Question question, QuestionManager questionManager);
}
