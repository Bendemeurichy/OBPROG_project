package be.ugent.flash.SceneSwitcher;

import be.ugent.flash.QuestionManager.QuestionManager;
import be.ugent.flash.SceneSwitcher.questionDataManager.MC;
import be.ugent.flash.jdbc.DataAccesException;
import be.ugent.flash.jdbc.Parts;
import be.ugent.flash.jdbc.Question;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.io.IOException;
import java.util.ArrayList;

public class McsController extends QuestionController{
    private ArrayList<Parts> parts;

    public McsController(Question question, QuestionManager manager) throws DataAccesException {
        this.questionData=new MC(question);
        this.manager=manager;
        this.parts=manager.getProvider().getDataAccessContext().getPartDAO().specificPart(questionData.getId());
    }

    public void initialize() {
        try {
            photoPart.setVisible(true);
            photoPart.setImage(questionData.getImage());
            } catch(NullPointerException e){
            photoPart.setVisible(false);
        }
        textPart.getChildren().add(new Text(questionData.getText()));
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
