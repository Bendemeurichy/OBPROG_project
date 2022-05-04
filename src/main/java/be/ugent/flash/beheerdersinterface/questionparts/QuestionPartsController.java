package be.ugent.flash.beheerdersinterface.questionparts;

import be.ugent.flash.beheerdersinterface.BeheerdersinterfaceController;
import be.ugent.flash.jdbc.Question;
import javafx.scene.layout.VBox;

import java.io.File;
import java.util.ArrayList;

public abstract class QuestionPartsController {


    abstract void initParts(Question question, VBox answerbox, File file, BeheerdersinterfaceController interfacecontroller);

    abstract ArrayList getParts();

    public abstract String getCorrectAnswer();
}
