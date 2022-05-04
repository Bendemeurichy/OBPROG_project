package be.ugent.flash.beheerdersinterface.questionparts;

import be.ugent.flash.beheerdersinterface.BeheerdersinterfaceController;
import be.ugent.flash.jdbc.Question;
import javafx.scene.layout.VBox;

import java.io.File;

public class McsPartsFactory implements QuestionPartsFactory {
    @Override
    public QuestionPartsController create(Question question, VBox answerbox, File db, BeheerdersinterfaceController interfacecontroller) {
        McsPartsController controller= new McsPartsController();
        controller.initParts(question,answerbox,db,interfacecontroller);
        return controller;
    }
}
