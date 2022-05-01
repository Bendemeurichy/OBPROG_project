package be.ugent.flash.beheerdersinterface.questionparts;

import be.ugent.flash.jdbc.DataAccesException;
import be.ugent.flash.jdbc.JDBCDataAccesProvider;
import be.ugent.flash.jdbc.Parts;
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
        partpane.setPrefSize(450,300);
        partpane.setCollapsible(false);

        answerbox.getChildren().add(partpane);
        ArrayList<Parts> initialP;
        try {
            initialP=new JDBCDataAccesProvider("jdbc:sqlite:"+db.getPath()).getDataAccessContext().getPartDAO().specificPart(question.question_id());
        } catch (DataAccesException e) {
            throw new RuntimeException(e);
        }
        for(Parts part:initialP){
            CheckBox box=new CheckBox();
            box.setOnAction(this::addsolution);
            TextArea answerField=new TextArea(part.part());
            answerField.setWrapText(true);
            partslist.add(answerField);
            Button cross=new Button("X");
            cross.setOnAction(this::removePart);
            boxlist.add(box);
            crossbox.add(cross);
        }
        loadParts();
    }

    private void loadParts() {
        for(int i=0;i<partslist.size();i++){
            answerPane.add(boxlist.get(i),0,i);
            answerPane.add(partslist.get(i),1,i);
            answerPane.add(crossbox.get(i),2,i);
        }
    }

    private void addsolution(ActionEvent event) {
    }

    private void removePart(ActionEvent event) {
    }

    private void addParts(ActionEvent event) {
    }

    public String getCorrectAnswer() {
        if(correctAnswer.size()!=1){
        return correctAnswer.get(0);
        }else{
            throw new RuntimeException("Too many answers");
        }

    }
}
