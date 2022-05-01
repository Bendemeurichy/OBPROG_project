package be.ugent.flash.SceneSwitcher;

import be.ugent.flash.QuestionManager.QuestionManager;
import be.ugent.flash.jdbc.DataAccesException;
import be.ugent.flash.jdbc.ImageParts;
import be.ugent.flash.jdbc.Question;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;

public class MciController extends AbstractMC{
    @FXML
    public HBox buttons;

    private ArrayList<ImageParts> parts;
    private boolean disabled=false;
    public MciController(Question question,ArrayList<ImageParts> parts) {
        super(question,parts);

    }

    @Override
    public void makequiz(QuestionManager questionManager, boolean prevCorrect) {
        super.makequiz(questionManager, prevCorrect);
        try {
            parts=manager.getProvider().getDataAccessContext().getPartDAO().specificImagepart(questionData.getId());
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
        for(int i=0; i<parts.size();i++){
            ImageView answer= new ImageView(new Image(new ByteArrayInputStream(parts.get(i).part())));
            Button temp=new Button();
            temp.getStyleClass().add("imagebutton");
            temp.setOnAction(this::answer);
            temp.setUserData(i);
            temp.setGraphic(answer);
            buttons.getChildren().add(temp);
        }
        buttons.setDisable(disabled);
    }

    @Override
    public String getfxml() {
        return "Mci.fxml";
    }
}
