package be.ugent.flash.beheerdersinterface.questionparts;

import be.ugent.flash.beheerdersinterface.BeheerdersinterfaceCompanion;
import be.ugent.flash.beheerdersinterface.BeheerdersinterfaceController;
import be.ugent.flash.jdbc.DataAccesException;
import be.ugent.flash.jdbc.JDBCDataAccesProvider;
import be.ugent.flash.jdbc.Parts;
import be.ugent.flash.jdbc.Question;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;

import java.io.File;
import java.util.ArrayList;

public class McsPartsController extends MultipleChoicePartsController {

    @Override
    public void initParts(Question question, VBox answerbox, File db, BeheerdersinterfaceController interfacecontroller) {
        super.initParts(question, answerbox, db, interfacecontroller);
        ArrayList<Parts> initialP;
        try {
            initialP = new JDBCDataAccesProvider("jdbc:sqlite:" + db.getPath()).getDataAccessContext().getPartDAO().specificPart(question.question_id());
        } catch (DataAccesException e) {
            throw new RuntimeException(e);
        }
        if (initialP.isEmpty()){ //in new question, maak hashmap met default value voor antwoord;
            CheckBox box = new CheckBox();
            box.setSelected(true);
            TextArea answerArea = new TextArea();
            partsStyling(box, answerArea);
            new BeheerdersinterfaceCompanion().addParts(new Parts(question.question_id(), ""), db);
            loadParts();
        } else {
            for (Parts part : initialP) {
                CheckBox box = new CheckBox();
                TextArea answerArea = new TextArea(part.part());
                partsStyling(box, answerArea);
                loadParts();
                selectCorrect();
            }
        }

    }

    public ArrayList<Parts> getParts() {
        ArrayList<Parts> changed = new ArrayList<>();
        for (TextArea area : partslist) {
            changed.add(new Parts(question.question_id(), area.getText()));
        }
        return changed;
    }

    @Override
    public String getCorrectAnswer() {
        ArrayList<CheckBox> checked = new ArrayList<>();
        for (CheckBox box : boxlist) {
            if (box.isSelected()){
                checked.add(box);
            }
        }
        if (checked.size() == 1){
            return boxlist.indexOf(checked.get(0)) + "";
        }
        throw new IllegalArgumentException("Er moet exact één antwoord aangeduid zijn");
    }


    protected void selectCorrect() {
        for (CheckBox box : boxlist) {
            if (question.correct_answer().equals(boxlist.indexOf(box) + "")){
                box.setSelected(true);
            }
        }
    }

    protected void loadParts() {
        answerPane.getChildren().clear();
        for (int i = 0; i < partslist.size(); i++) {
            answerPane.add(boxlist.get(i), 0, i);
            answerPane.add(partslist.get(i), 1, i);
            answerPane.add(crossbox.get(i), 2, i);
        }
    }

    protected void removePart(ActionEvent event) {
        interfacecontroller.ischanged();
        int index = crossbox.indexOf(event.getSource());
        crossbox.remove(index);
        partslist.remove(index);
        boxlist.remove(index);
        loadParts();
    }

    public void addPart(ActionEvent event) {
        interfacecontroller.ischanged();
        CheckBox box = new CheckBox();
        TextArea answerArea = new TextArea();
        partsStyling(box, answerArea);
        loadParts();
    }

    protected void partsStyling(CheckBox box, TextArea answerArea) {
        box.setOnAction(event -> updatechange());
        answerArea.setWrapText(true);
        answerArea.setOnKeyTyped(event -> updatechange());
        answerArea.setPrefSize(450, 50);
        partslist.add(answerArea);
        Button cross = new Button("X");
        cross.setOnAction(this::removePart);
        boxlist.add(box);
        crossbox.add(cross);
    }

    private void updatechange() {
        interfacecontroller.ischanged();
    }


}
