package be.ugent.flash.SceneSwitcher;

import be.ugent.flash.QuestionManager.QuestionManager;
import be.ugent.flash.jdbc.DataAccesException;
import be.ugent.flash.jdbc.Parts;
import be.ugent.flash.jdbc.Question;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

import java.util.ArrayList;

public class MccController extends AbstractMC{
    @FXML
    public HBox buttons;

    private ArrayList<Parts> parts;
    private boolean disabled=false;

    public MccController(Question question,ArrayList<Parts> parts){
        super(question,parts);
        this.parts=parts;

    }

    @Override
    public void makequiz(QuestionManager questionManager, boolean prevCorrect) {
        super.makequiz(questionManager, prevCorrect);
        try {
            this.parts=manager.getProvider().getDataAccessContext().getPartDAO().specificPart(questionData.getId());
        } catch (DataAccesException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void disable() {
        disabled=true;
    }

    public void initialize(){
        super.initialize();
        for(int i=0;i< parts.size();i++){
            Button temp= new Button(parts.get(i).part());
            temp.setDisable(disabled);
            temp.setOnAction(this::answer);
            temp.setUserData(i);
            buttons.getChildren().add(temp);
        }
        buttons.setDisable(disabled);
    }

    @Override
    public String getfxml() {
        return "Mcc.fxml";
    }
}
