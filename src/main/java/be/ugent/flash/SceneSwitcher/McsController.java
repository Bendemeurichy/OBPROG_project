package be.ugent.flash.SceneSwitcher;

import be.ugent.flash.QuestionManager.QuestionManager;
import be.ugent.flash.SceneSwitcher.questionDataManager.GeneralQuestion;
import be.ugent.flash.jdbc.DataAccesException;
import be.ugent.flash.jdbc.Parts;
import be.ugent.flash.jdbc.Question;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import java.util.ArrayList;

public class McsController extends QuestionController{
    @FXML
    public VBox answers;
    public VBox buttons;

    private final ArrayList<Parts> parts;

    public McsController(Question question, QuestionManager manager,boolean prevCorrect) throws DataAccesException {
        this.prevCorrect=prevCorrect;
        this.questionData=new GeneralQuestion(question);
        this.manager=manager;
        this.parts=manager.getProvider().getDataAccessContext().getPartDAO().specificPart(questionData.getId());
    }

    public void initialize() {
        super.initialize();
        for(int i=0;i< parts.size();i++){
            String ascii= ""+(char) (65+i);
            Button temp= new Button(ascii);
            temp.setOnAction(this::answer);
            temp.setUserData(i);
            buttons.getChildren().add(temp);
            answers.getChildren().add(new TextFlow(new Text(parts.get(i).part())));
        }

    }

    @FXML
    public void answer(ActionEvent event){
        Button called=(Button)event.getSource();
        this.correct=questionData.checkAnswer(""+called.getUserData());
        manager.next();

    }

    @Override
    public String getfxml() {
        return "Mcs.fxml";
    }
}
