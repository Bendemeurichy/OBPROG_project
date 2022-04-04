package be.ugent.flash.SceneSwitcher;

import be.ugent.flash.QuestionManager.QuestionManager;
import be.ugent.flash.SceneSwitcher.questionDataManager.MC;
import be.ugent.flash.jdbc.DataAccesException;
import be.ugent.flash.jdbc.Parts;
import be.ugent.flash.jdbc.Question;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class MccController extends QuestionController{
    @FXML
    public HBox buttons;

    private final ArrayList<Parts> parts;

    public MccController(Question question, QuestionManager questionManager) throws DataAccesException{
        this.questionData=new MC(question);
        this.manager=questionManager;
        this.parts=manager.getProvider().getDataAccessContext().getPartDAO().specificPart(questionData.getId());
    }

    public void initialize(){
        try {
            photoPart.setVisible(true);
            photoPart.setImage(questionData.getImage());
        } catch(NullPointerException e){
            photoPart.setVisible(false);
        }
        textPart.getChildren().add(new Text(questionData.getText()));
        for(int i=0;i< parts.size();i++){
            Button temp= new Button(parts.get(i).part());
            temp.setOnAction(this::answer);
            temp.setUserData(i);
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
