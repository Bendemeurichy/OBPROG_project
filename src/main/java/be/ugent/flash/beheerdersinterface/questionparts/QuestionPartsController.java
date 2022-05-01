package be.ugent.flash.beheerdersinterface.questionparts;

import be.ugent.flash.jdbc.Question;
import javafx.scene.layout.VBox;

import java.io.File;

public interface QuestionPartsController {

    void initParts(Question question, VBox answerbox, File file);

}
