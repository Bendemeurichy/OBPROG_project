package be.ugent.flash.QuestionManager;

import be.ugent.flash.SceneSwitcher.McsController;
import be.ugent.flash.SceneSwitcher.QuestionController;
import be.ugent.flash.jdbc.Parts;
import be.ugent.flash.jdbc.Question;

import java.util.ArrayList;

public class McsFactory implements QuestionFactory {
    @Override
    public QuestionController CreateFlashcard(Question question, QuestionManager questionManager, boolean prevCorrect) {
        McsController controller = new McsController(question, null);
        controller.makequiz(questionManager, prevCorrect);
        return controller;
    }

    @Override
    public QuestionController loadPreview(Question question, ArrayList<?> parts) {
        @SuppressWarnings("unchecked") McsController controller = new McsController(question, (ArrayList<Parts>) parts);
        controller.disable();
        return controller;
    }
}
