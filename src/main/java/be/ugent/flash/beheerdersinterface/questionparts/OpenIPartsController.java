package be.ugent.flash.beheerdersinterface.questionparts;

import be.ugent.flash.jdbc.ImageParts;
import be.ugent.flash.jdbc.Parts;
import be.ugent.flash.jdbc.Question;
import javafx.scene.layout.VBox;

import java.io.File;
import java.util.ArrayList;

public class OpenIPartsController extends QuestionPartsController {
    @Override
    public void initParts(Question question, VBox answerbox, File file) {

    }

    @Override
    public ArrayList<Parts> getParts() {
        return null;
    }

    public String CheckError(){
        return null;
    }

    public String getCorrectAnswer() {
        return null;
    }
}
