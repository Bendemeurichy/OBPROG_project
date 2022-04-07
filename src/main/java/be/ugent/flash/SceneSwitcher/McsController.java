package be.ugent.flash.SceneSwitcher;

import be.ugent.flash.QuestionManager.QuestionManager;
import be.ugent.flash.jdbc.DataAccesException;
import be.ugent.flash.jdbc.Parts;
import be.ugent.flash.jdbc.Question;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.util.ArrayList;

public class McsController extends AbstractMC{
    @FXML
    public GridPane answers;

    private final ArrayList<Parts> parts;

    public McsController(Question question, QuestionManager questionManager,boolean prevCorrect) throws DataAccesException {
        super(question, questionManager, prevCorrect);
        this.parts=manager.getProvider().getDataAccessContext().getPartDAO().specificPart(questionData.getId());
    }

    public void initialize() {
        super.initialize();
        for(int i=0;i< parts.size();i++){
            String ascii= ""+(char) (65+i);
            Button temp= new Button(ascii);
            temp.setOnAction(this::answer);
            temp.setUserData(i);
            answers.add(temp,0,i);
            answers.add(new TextFlow(new Text(parts.get(i).part())),1,i);
        }

    }

    @Override
    public String getfxml() {
        return "Mcs.fxml";
    }
}