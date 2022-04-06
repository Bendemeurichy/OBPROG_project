package be.ugent.flash.SceneSwitcher;

import be.ugent.flash.QuestionManager.QuestionManager;
import be.ugent.flash.SceneSwitcher.questionDataManager.GeneralQuestion;
import be.ugent.flash.jdbc.DataAccesException;
import be.ugent.flash.jdbc.Parts;
import be.ugent.flash.jdbc.Question;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

import java.util.ArrayList;

public class MccController extends QuestionController{
    @FXML
    public HBox buttons;

    private final ArrayList<Parts> parts;

    public MccController(Question question, QuestionManager questionManager,boolean prevCorrect) throws DataAccesException{
        this.prevCorrect=prevCorrect;
        this.questionData=new GeneralQuestion(question);
        this.manager=questionManager;
        this.parts=manager.getProvider().getDataAccessContext().getPartDAO().specificPart(questionData.getId());
    }

    public void initialize(){
        super.initialize();
        for(int i=0;i< parts.size();i++){
            Button temp= new Button(parts.get(i).part());
            temp.setOnAction(this::answer);
            temp.setUserData(i+1);
            buttons.getChildren().add(temp);
        }
    }

    public void answer(ActionEvent event){
        Button called=(Button)event.getSource();
        this.correct=questionData.checkAnswer(""+called.getUserData());
        manager.next();

    }

    @Override
    public String getfxml() {
        return "Mcc.fxml";
    }
}
