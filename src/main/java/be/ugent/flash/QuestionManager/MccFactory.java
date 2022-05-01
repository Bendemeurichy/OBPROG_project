package be.ugent.flash.QuestionManager;

import be.ugent.flash.SceneSwitcher.MccController;
import be.ugent.flash.SceneSwitcher.QuestionController;
import be.ugent.flash.jdbc.Parts;
import be.ugent.flash.jdbc.Question;

import java.util.ArrayList;

@SuppressWarnings("unchecked")
public class MccFactory implements QuestionFactory{
    @Override
    public QuestionController CreateFlashcard(Question question,QuestionManager questionManager, boolean prevCorrect) {
        MccController controller= new MccController(question,null);
        controller.makequiz(questionManager,prevCorrect);
        return controller;
    }

    @Override
    public QuestionController loadPreview(Question question, ArrayList<?> parts) {
        @SuppressWarnings("unchecked") MccController controller= new MccController(question, (ArrayList<Parts>) parts);
        controller.disable();
        return controller;
    }


}
