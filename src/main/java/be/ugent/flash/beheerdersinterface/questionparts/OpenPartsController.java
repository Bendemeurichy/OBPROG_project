package be.ugent.flash.beheerdersinterface.questionparts;

import be.ugent.flash.jdbc.Parts;
import be.ugent.flash.jdbc.Question;
import javafx.scene.layout.VBox;

import java.io.File;
import java.util.ArrayList;

public class OpenPartsController implements QuestionPartsController {
    @Override
    public void initParts(Question question, VBox answerbox, File file) {

    }

    public ArrayList<Parts> getCorrectAnswer() {
        return null;
    }
}
