package be.ugent.flash.beheerdersinterface.questionparts;

import be.ugent.flash.beheerdersinterface.BeheerdersinterfaceCompanion;
import be.ugent.flash.beheerdersinterface.BeheerdersinterfaceController;
import be.ugent.flash.jdbc.Parts;
import be.ugent.flash.jdbc.Question;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;

import java.io.File;
import java.util.ArrayList;

public class OpenPartsController extends QuestionPartsController {
    public TextField answerfield=new TextField();
    private BeheerdersinterfaceController interfacecontroller;
    @Override
    public void initParts(Question question, VBox answerbox, File file, BeheerdersinterfaceController interfacecontroller) {
        this.interfacecontroller=interfacecontroller;
        if (question.correct_answer().equals("") || question.correct_answer().equals(0 + "")){
            new BeheerdersinterfaceCompanion().addParts(new Parts(question.question_id(), ""), file);
        } else {
            answerfield.setText(question.correct_answer());
            answerfield.setOnKeyTyped(this::updatechange);
        }
        answerbox.getChildren().add(answerfield);
    }

    private void updatechange(KeyEvent keyEvent) {
        interfacecontroller.ischanged();
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
