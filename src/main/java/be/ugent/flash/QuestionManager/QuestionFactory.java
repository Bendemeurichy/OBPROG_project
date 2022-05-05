package be.ugent.flash.QuestionManager;

import be.ugent.flash.SceneSwitcher.QuestionController;
import be.ugent.flash.jdbc.Question;

import java.util.ArrayList;

//factory structuur om een nieuwe controller per vraag aan te maken
public interface QuestionFactory {
    QuestionController CreateFlashcard(Question question, QuestionManager questionManager, boolean prevCorrect);

    QuestionController loadPreview(Question question, ArrayList<?> parts);
}
