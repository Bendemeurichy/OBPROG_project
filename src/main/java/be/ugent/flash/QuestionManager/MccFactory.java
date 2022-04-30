package be.ugent.flash.QuestionManager;

import be.ugent.flash.SceneSwitcher.MccController;
import be.ugent.flash.SceneSwitcher.QuestionController;
import be.ugent.flash.jdbc.DataAccesException;
import be.ugent.flash.jdbc.Parts;
import be.ugent.flash.jdbc.Question;

import java.util.ArrayList;

public class MccFactory implements QuestionFactory{
    @Override
    public QuestionController CreateFlashcard(Question question, QuestionManager questionManager, boolean prevCorrect) {
        try {
            return new MccController(question,questionManager,prevCorrect);
        } catch (DataAccesException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public QuestionController loadPreview(Question question, ArrayList<Parts> parts) {
        return null;
    }


}
