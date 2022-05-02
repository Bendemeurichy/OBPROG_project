package be.ugent.flash.beheerdersinterface.questionparts;

import be.ugent.flash.beheerdersinterface.BeheerdersinterfaceCompanion;
import be.ugent.flash.jdbc.Parts;
import be.ugent.flash.jdbc.Question;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.io.File;
import java.util.ArrayList;

public class OpenPartsController extends QuestionPartsController {
    public TextField answerfield=new TextField();
    @Override
    public void initParts(Question question, VBox answerbox, File file) {
        if (question.correct_answer().equals("") || question.correct_answer().equals(0 + "")){
            new BeheerdersinterfaceCompanion().addParts(new Parts(question.question_id(), ""), file);
    } else {
            answerfield.setText(question.correct_answer());
        }
        answerbox.getChildren().add(answerfield);
    }

    @Override
    ArrayList getParts() {
        return null;
    }

    public String getCorrectAnswer() {
        if(! answerfield.getText().equals("")){
            return answerfield.getText();
        } else {
            throw new IllegalArgumentException("Antwoord mag niet leeg zijn");
        }
    }
}
