package be.ugent.flash.beheerdersinterface.questionparts;

import be.ugent.flash.jdbc.Question;
import javafx.scene.layout.VBox;

import java.io.File;

public class McsPartsFactory implements QuestionPartsFactory {
    @Override
    public QuestionPartsController create(Question question, VBox answerbox, File db) {
        McsPartsController controller= new McsPartsController();
        controller.initParts(question,answerbox,db);
        return controller;
    }
}
