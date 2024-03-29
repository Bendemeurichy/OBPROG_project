package be.ugent.flash.beheerdersinterface.questionparts;

import be.ugent.flash.beheerdersinterface.BeheerdersinterfaceController;
import be.ugent.flash.jdbc.Question;
import javafx.scene.layout.VBox;

import java.io.File;

//factories voor partcontrollers
public interface QuestionPartsFactory {
    QuestionPartsController create(Question question, VBox answerbox, File db, BeheerdersinterfaceController controller);
}
