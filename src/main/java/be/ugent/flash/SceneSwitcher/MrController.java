package be.ugent.flash.SceneSwitcher;

import be.ugent.flash.QuestionManager.QuestionManager;
import be.ugent.flash.SceneSwitcher.questionDataManager.GeneralQuestion;
import be.ugent.flash.jdbc.DataAccesException;
import be.ugent.flash.jdbc.Parts;
import be.ugent.flash.jdbc.Question;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.util.ArrayList;

public class MrController extends QuestionController {
    @FXML
    public GridPane answers;
    public Button next=new Button("Ok");

    private ArrayList<CheckBox> checkBoxes= new ArrayList<>();
    private final ArrayList<Parts> parts;

    public MrController(Question question, QuestionManager manager, boolean prevCorrect) throws DataAccesException {
        this.prevCorrect=prevCorrect;
        this.manager=manager;
        this.questionData=new GeneralQuestion(question);
        this.parts= manager.getProvider().getDataAccessContext().getPartDAO().specificPart(questionData.getId());
    }

    public void initialize(){
        super.initialize();
        for (int i=0;i< parts.size();i++) {
            CheckBox checkBox=new CheckBox();
            checkBoxes.add(checkBox);
            answers.add(new TextFlow(new Text(parts.get(i).part())),1,i);
            answers.add(checkBox,0,i);
        }
        answers.add(next,2,answers.getRowCount()-1);
        next.setOnAction(this::answer);
    }

    public void answer(ActionEvent t){
        StringBuilder answer= new StringBuilder();
        for(CheckBox box: checkBoxes){
            answer.append(box.isSelected() ? "T" : "F");
        }
        this.correct=questionData.checkAnswer(String.valueOf(answer));
        manager.next();
    }

    @Override
    public String getfxml() {
        return "Mr.fxml";
    }
}
