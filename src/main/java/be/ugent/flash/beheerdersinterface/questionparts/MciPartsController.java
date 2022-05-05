package be.ugent.flash.beheerdersinterface.questionparts;

import be.ugent.flash.beheerdersinterface.BeheerdersinterfaceController;
import be.ugent.flash.beheerdersinterface.popups.ErrorDialog;
import be.ugent.flash.jdbc.DataAccesException;
import be.ugent.flash.jdbc.ImageParts;
import be.ugent.flash.jdbc.JDBCDataAccesProvider;
import be.ugent.flash.jdbc.Question;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class MciPartsController extends MultipleChoicePartsController {
    public final ArrayList<ImageView> parts = new ArrayList<>();

    @Override
    public void initParts(Question question, VBox answerbox, File file, BeheerdersinterfaceController interfacecontroller) {
        super.initParts(question, answerbox, file, interfacecontroller);
        ArrayList<ImageParts> initialP;
        try {
            initialP = new JDBCDataAccesProvider("jdbc:sqlite:" + file.getPath()).getDataAccessContext().getPartDAO().specificImagepart(question.question_id());
            if (initialP.isEmpty()){
                CheckBox box = new CheckBox();
                box.setSelected(true);
                ImageView imageAnswer;
                try {
                    FileInputStream stream = new FileInputStream("src/main/resources/leeg.png");
                    Image image = new Image(stream);
                    imageAnswer = new ImageView();
                    imageAnswer.setImage(image);
                    imageAnswer.setUserData(null);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                imageAnswer.setOnMouseClicked(this::addImagePart);
                partsStyling(box, imageAnswer);
                loadParts();
            } else {
                for (ImageParts part : initialP) {
                    CheckBox box = new CheckBox();
                    ImageView imageAnswer = new ImageView();
                    if (part.part() == null){
                        FileInputStream stream;
                        try {
                            stream = new FileInputStream("src/main/resources/leeg.png");
                            Image image = new Image(stream);
                            imageAnswer.setImage(image);
                            imageAnswer.setUserData(null);
                        } catch (FileNotFoundException e) {
                            new ErrorDialog().display("bestand leeg kon niet gevonden worden");
                            Platform.exit();
                        }
                    } else {
                        imageAnswer.setImage((new Image(new ByteArrayInputStream(part.part()))));
                        imageAnswer.setUserData(part.part());
                    }

                    imageAnswer.setOnMouseClicked(this::addImagePart);
                    partsStyling(box, imageAnswer);
                    loadParts();
                    selectCorrect();
                }
            }
        } catch (DataAccesException e) {
            new ErrorDialog().display(e.getMessage());
            Platform.exit();
        }

    }


    private void partsStyling(CheckBox box, ImageView answerArea) {
        box.setOnAction(event -> updatechange());
        answerArea.setPreserveRatio(true);
        answerArea.setFitWidth(150);
        answerArea.setFitHeight(100);
        parts.add(answerArea);
        Button cross = new Button("X");
        cross.setOnAction(this::removePart);
        boxlist.add(box);
        crossbox.add(cross);
    }

    private void updatechange() {
        interfacecontroller.ischanged();
    }

    private void selectCorrect() {
        for (CheckBox box : boxlist) {
            if (question.correct_answer().equals(boxlist.indexOf(box) + "")){
                box.setSelected(true);
            }
        }
    }

    private void loadParts() {
        answerPane.getChildren().clear();
        for (int i = 0; i < parts.size(); i++) {
            answerPane.add(boxlist.get(i), 0, i);
            if (parts.get(i).getUserData() == null){
                ImageView view = parts.get(i);
                try {
                    FileInputStream stream = new FileInputStream("src/main/resources/leeg.png");
                    Image image = new Image(stream);
                    view.setUserData(null);
                    view.setImage(image);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                answerPane.add(view, 1, i);
            } else {
                answerPane.add(parts.get(i), 1, i);
            }
            answerPane.add(crossbox.get(i), 2, i);
        }

    }

    public void addImagePart(MouseEvent event) {
        if (event.getButton().equals(MouseButton.PRIMARY)){
            if (event.getClickCount() == 2){
                FileChooser chooser = new FileChooser();
                CheckBox box = new CheckBox();
                ImageView view = (ImageView) event.getSource();
                chooser.setTitle("Kies een afbeelding (.jpeg of png)");
                chooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("jpeg files", "*.jpeg", "*jpg"), new FileChooser.ExtensionFilter("npg files", "*.npg"));
                File file = chooser.showOpenDialog(answerPane.getScene().getWindow());
                if (file != null){
                    interfacecontroller.ischanged();
                    try {
                        byte[] bytes = Files.readAllBytes(Path.of(file.getPath()));
                        Image image = new Image(new ByteArrayInputStream(bytes));
                        view.setUserData(bytes);
                        view.setImage(image);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }

            }
        }
    }

    @Override
    public ArrayList<ImageParts> getParts() {
        ArrayList<ImageParts> changed = new ArrayList<>();
        for (ImageView area : parts) {
            if (area.getUserData() == null){
                changed.add(new ImageParts(question.question_id(), null));
            } else {
                changed.add(new ImageParts(question.question_id(), (byte[]) area.getUserData()));
            }
        }
        return changed;
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
        }
        throw new IllegalArgumentException("Er moet exact één antwoord aangeduid zijn");
    }

    protected void removePart(ActionEvent event) {
        interfacecontroller.ischanged();
        int index = crossbox.indexOf(event.getSource());
        crossbox.remove(index);
        parts.remove(index);
        boxlist.remove(index);
        loadParts();
    }

    @Override
    void addPart(ActionEvent event) {
        interfacecontroller.ischanged();
        CheckBox box = new CheckBox();
        ImageView view = new ImageView();
        view.setUserData(null);
        view.setOnMouseClicked(this::addImagePart);
        partsStyling(box, view);
        loadParts();
    }


}
