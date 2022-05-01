package be.ugent.flash.beheerdersinterface.questionparts;

import be.ugent.flash.jdbc.Question;
import javafx.scene.layout.VBox;

import java.io.File;

public class MccPartsFactory implements QuestionPartsFactory {
    @Override
    public QuestionPartsController create(Question question, VBox answerbox, File db) {
        return null;
    }
}
