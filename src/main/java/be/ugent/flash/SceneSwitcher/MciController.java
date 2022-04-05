package be.ugent.flash.SceneSwitcher;

import be.ugent.flash.QuestionManager.QuestionManager;
import be.ugent.flash.SceneSwitcher.questionDataManager.GeneralQuestion;
import be.ugent.flash.jdbc.DataAccesException;
import be.ugent.flash.jdbc.ImageParts;
import be.ugent.flash.jdbc.Question;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;

public class MciController extends QuestionController{
    @FXML
    public HBox buttons;

    private final ArrayList<ImageParts> parts;

    public MciController(Question question, QuestionManager questionManager,boolean prevCorrect) throws DataAccesException {
        this.prevCorrect=prevCorrect;
        this.manager=questionManager;
        this.questionData=new GeneralQuestion(question);
        this.parts = manager.getProvider().getDataAccessContext().getPartDAO().specificImagepart(questionData.getId());
    }

    public void initialize(){
        super.initialize();
        for(int i=0; i<parts.size();i++){
            ImageView answer= new ImageView(new Image(new ByteArrayInputStream(parts.get(i).part())));
            Button temp=new Button();
            temp.setOnAction(this::answer);
            temp.setUserData(i);
            temp.setGraphic(answer);
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
        return "Mci.fxml";
    }
}
