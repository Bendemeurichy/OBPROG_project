package be.ugent.flash.beheerdersinterface.questionparts;

import be.ugent.flash.jdbc.Question;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.io.File;
import java.util.ArrayList;

public class McsPartsController implements QuestionPartsController {
    private Question question;
    private GridPane answerPane=new GridPane();
    private final ArrayList<TextArea> partslist=new ArrayList<>();
    private final ArrayList<CheckBox> boxlist=new ArrayList<>();
    private final ArrayList<Button> crossbox=new ArrayList<>();
    ArrayList<String> correctAnswer; //mag eigenlijk maar 1 zijn, check gebeurt bij opslaan
    @Override
    public void initParts(Question question, VBox answerbox, File db) {
        this.question=question;
        Button addPart=new Button("Optie toevoegen");
        addPart.setOnAction(this::addParts);
        VBox layoutbox=new VBox(new Label("keuzes worden onder elkaar getoond - met knoppen A,B,C..."),answerPane,addPart);
        TitledPane partpane=new TitledPane("Mogelijke antwoorden",layoutbox);
        partpane.setCollapsible(false);

        answerbox.getChildren().add(partpane);
    }

    private void addParts(ActionEvent event) {
    }

    public String checkError(){
        return null;
    }
    public String getCorrectAnswer() {
        return correctAnswer.get(0);
    }
}
