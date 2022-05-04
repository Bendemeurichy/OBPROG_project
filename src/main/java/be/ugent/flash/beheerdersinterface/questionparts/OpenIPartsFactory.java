package be.ugent.flash.beheerdersinterface.questionparts;

import be.ugent.flash.beheerdersinterface.BeheerdersinterfaceController;
import be.ugent.flash.jdbc.Question;
import javafx.scene.layout.VBox;

import java.io.File;

public class OpenIPartsFactory implements QuestionPartsFactory {
    @Override
    public QuestionPartsController create(Question question, VBox answerbox, File db, BeheerdersinterfaceController beheerdersinterfaceController) {
        OpenIPartsController controller= new OpenIPartsController();
        controller.initParts(question,answerbox,db,beheerdersinterfaceController);
        return controller;
    }
}
