package be.ugent.flash.QuestionManager;

import be.ugent.flash.SceneSwitcher.OpenController;
import be.ugent.flash.SceneSwitcher.OpeniController;
import be.ugent.flash.SceneSwitcher.QuestionController;
import be.ugent.flash.jdbc.Parts;
import be.ugent.flash.jdbc.Question;

import java.util.ArrayList;

public class OpenFactory implements QuestionFactory {
    @Override
    public QuestionController CreateFlashcard(Question question, QuestionManager questionManager, boolean prevCorrect) {
        OpenController controller=new OpenController(question);
        controller.makequiz(questionManager,prevCorrect);
        return controller;
    }

    @Override
    public QuestionController loadPreview(Question question, ArrayList<Parts> parts) {
        return new OpenController(question);
    }
}
