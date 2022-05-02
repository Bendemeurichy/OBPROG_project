package be.ugent.flash.beheerdersinterface.questionparts;

import be.ugent.flash.jdbc.Question;
import javafx.scene.layout.VBox;

import java.io.File;

public class OpenPartsFactory implements QuestionPartsFactory {
    @Override
    public QuestionPartsController create(Question question, VBox answerbox, File db) {
        OpenPartsController controller=new OpenPartsController();
        controller.initParts(question,answerbox,db);
        return controller;
    }
}
