package be.ugent.flash.beheerdersinterface.questionparts;

import be.ugent.flash.beheerdersinterface.BeheerdersinterfaceCompanion;
import be.ugent.flash.beheerdersinterface.BeheerdersinterfaceController;
import be.ugent.flash.beheerdersinterface.popups.ErrorDialog;
import be.ugent.flash.jdbc.DataAccesException;
import be.ugent.flash.jdbc.JDBCDataAccesProvider;
import be.ugent.flash.jdbc.Parts;
import be.ugent.flash.jdbc.Question;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.io.File;
import java.util.ArrayList;

public class MccPartsController extends MultipleChoicePartsController {
    // door het verschil van textfield met textarea tov mcs veel duplicatie, veel errors met overerving
    protected final ArrayList<TextField> partslist = new ArrayList<>();

    @Override
    public void initParts(Question question, VBox answerbox, File file, BeheerdersinterfaceController interfacecontroller) {
        super.initParts(question, answerbox, file, interfacecontroller);
        ArrayList<Parts> initialP;
        try {
            initialP = new JDBCDataAccesProvider("jdbc:sqlite:" + file.getPath()).getDataAccessContext().getPartDAO().specificPart(question.question_id());
            if (initialP.isEmpty()){
                CheckBox box = new CheckBox();
                box.setSelected(true);
                box.setOnAction(this::updatechange);
                TextField answerArea = new TextField();
                partsStyling(box, answerArea);
                BeheerdersinterfaceCompanion companion = new BeheerdersinterfaceCompanion();
                companion.addParts(new Parts(question.question_id(), ""), file);
                System.out.println(getCorrectAnswer());
                loadParts();
            } else {
                for (Parts part : initialP) {
                    CheckBox box = new CheckBox();
                    box.setOnAction(this::updatechange);
                    TextField answerArea = new TextField(part.part());
                    partsStyling(box, answerArea);
                    loadParts();
                    selectCorrect();
                }
            }
        } catch (DataAccesException e) {
            new ErrorDialog().display(e.getMessage());
            Platform.exit();
        }
    }

    private void selectCorrect() {
        for (CheckBox box : boxlist) {
            if (question.correct_answer().equals(boxlist.indexOf(box) + "")){
                box.setSelected(true);
            }
        }

    }

    @Override
    public void addPart(ActionEvent event) {
        interfacecontroller.ischanged();
        CheckBox box = new CheckBox();
        box.setOnAction(this::updatechange);
        TextField answerArea = new TextField();
        partsStyling(box, answerArea);
        loadParts();
    }

    private void loadParts() {
        answerPane.getChildren().clear();
        for (int i = 0; i < partslist.size(); i++) {
            answerPane.add(boxlist.get(i), 0, i);
            answerPane.add(partslist.get(i), 1, i);
            answerPane.add(crossbox.get(i), 2, i);
        }

    }

    private void updatechange(ActionEvent event) {
        interfacecontroller.ischanged();
    }

    ArrayList<Parts> getParts() {
        ArrayList<Parts> changed = new ArrayList<>();
        for (TextField area : partslist) {
            changed.add(new Parts(question.question_id(), area.getText()));
        }
        return changed;
    }


    protected void partsStyling(CheckBox box, TextField answerfield) {
        answerfield.setPrefSize(450, 50);
        answerfield.setOnKeyTyped(event -> updatechange(null));
        partslist.add(answerfield);
        Button cross = new Button("X");
        cross.setOnAction(this::removePart);
        boxlist.add(box);
        crossbox.add(cross);
    }

    protected void removePart(ActionEvent event) {
        interfacecontroller.ischanged();
        int index = crossbox.indexOf(event.getSource());
        crossbox.remove(index);
        partslist.remove(index);
        boxlist.remove(index);
        loadParts();
    }

    public String getCorrectAnswer() {
        ArrayList<CheckBox> checked = new ArrayList<>();
        for (CheckBox box : boxlist) {
            if (box.isSelected()){
                checked.add(box);
            }
        }
        if (checked.size() == 1){
            return boxlist.indexOf(checked.get(0)) + "";
        } else if (checked.isEmpty()) {
            throw new IllegalArgumentException("Antwoordenlijst mag niet leeg zijn");
        }
        throw new IllegalArgumentException("Er moet exact één antwoord aangeduid zijn");
    }
}
