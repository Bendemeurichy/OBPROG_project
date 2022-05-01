package be.ugent.flash.QuestionManager;

import be.ugent.flash.SceneSwitcher.MrController;
import be.ugent.flash.SceneSwitcher.QuestionController;
import be.ugent.flash.jdbc.Parts;
import be.ugent.flash.jdbc.Question;

import java.util.ArrayList;

public class MrFactory implements QuestionFactory{
    @Override
    public QuestionController CreateFlashcard(Question question, QuestionManager questionManager, boolean prevCorrect) {
        MrController controller= new MrController(question,null);
        controller.makequiz(questionManager,prevCorrect);
        return controller;
    }
    @Override
    public QuestionController loadPreview(Question question, ArrayList<?> parts) {
        @SuppressWarnings("unchecked") MrController controller= new MrController(question, (ArrayList<Parts>) parts);
        controller.disable();
        return controller;
    }
}
