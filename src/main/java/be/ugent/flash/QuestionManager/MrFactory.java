package be.ugent.flash.QuestionManager;

import be.ugent.flash.SceneSwitcher.QuestionController;
import be.ugent.flash.SceneSwitcher.MrController;
import be.ugent.flash.jdbc.DataAccesException;
import be.ugent.flash.jdbc.Parts;
import be.ugent.flash.jdbc.Question;

import java.util.ArrayList;

public class MrFactory implements QuestionFactory{
    @Override
    public QuestionController CreateFlashcard(Question question, QuestionManager questionManager, boolean prevCorrect) {
        try {
            MrController controller= new MrController(question,null);
            controller.makequiz(questionManager,prevCorrect);
            return controller;
        } catch (DataAccesException e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    public QuestionController loadPreview(Question question, ArrayList<Parts> parts) {
        return new MrController(question,parts);
    }
}
