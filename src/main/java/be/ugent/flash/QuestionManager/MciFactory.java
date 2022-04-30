package be.ugent.flash.QuestionManager;

import be.ugent.flash.SceneSwitcher.MciController;
import be.ugent.flash.SceneSwitcher.QuestionController;
import be.ugent.flash.jdbc.DataAccesException;
import be.ugent.flash.jdbc.Parts;
import be.ugent.flash.jdbc.Question;

import java.util.ArrayList;

public class MciFactory implements QuestionFactory{
    @Override
    public QuestionController CreateFlashcard(Question question, QuestionManager questionManager,boolean prevCorrect) {
        try {
            return new MciController(question,questionManager,prevCorrect);
        } catch (DataAccesException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public QuestionController loadPreview(Question questio, ArrayList<Parts> parts) {
        return null;
    }
}
