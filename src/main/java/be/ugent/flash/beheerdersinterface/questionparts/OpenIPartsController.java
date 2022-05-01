package be.ugent.flash.beheerdersinterface.questionparts;

import be.ugent.flash.jdbc.Question;
import javafx.scene.layout.VBox;

import java.io.File;

public class OpenIPartsController implements QuestionPartsController {
    @Override
    public void initParts(Question question, VBox answerbox, File file) {

    }

    public String CheckError(){
        return null;
    }

    public String getCorrectAnswer() {
        return null;
    }
}
