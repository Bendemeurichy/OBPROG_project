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
    public final Button next=new Button("Ok");

    private final ArrayList<CheckBox> checkBoxes= new ArrayList<>();
    private ArrayList<Parts> parts;
    private boolean disabled=false;

    public MrController(Question question, ArrayList<Parts> parts) {
        this.questionData=new GeneralQuestion(question);
        this.parts= parts;
    }

    public void makequiz(QuestionManager manager, boolean prevCorrect) {
        super.makequiz(manager,prevCorrect);
        try {
            this.parts=manager.getProvider().getDataAccessContext().getPartDAO().specificPart(questionData.getId());
        } catch (DataAccesException e) {
            try {
                throw new DataAccesException("kon parts niet vinden",e);
            } catch (DataAccesException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    @Override
    public void disable() {
        disabled=true;
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
        for(CheckBox checkBox:checkBoxes){
            checkBox.setDisable(disabled);
        }
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
