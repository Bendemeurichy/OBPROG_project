package be.ugent.flash.beheerdersinterface.questionparts;

import be.ugent.flash.jdbc.Question;
import javafx.scene.layout.VBox;

import java.io.File;

public class OpenIPartsController extends OpenPartsController {
    @Override
    public void initParts(Question question, VBox answerbox, File file) {
        super.initParts(question,answerbox,file);
    }

    @Override
    public String getCorrectAnswer() {
        if(answerfield.getText().matches("^-?\\d+$")){
            return answerfield.getText();
        }
        throw new IllegalArgumentException("Antwoord moet een geheel getal zijn");
    }
}
