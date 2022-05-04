package be.ugent.flash.beheerdersinterface.popups;

import be.ugent.flash.QuestionManager.*;
import be.ugent.flash.SceneSwitcher.QuestionController;
import be.ugent.flash.SceneSwitcher.SceneManager;
import be.ugent.flash.jdbc.Parts;
import be.ugent.flash.jdbc.Question;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.ArrayList;
import java.util.Map;

/**
 * popupvenster om de preview van de vraag in te laden, gebruikt controllers uit deel 1 en scenechanger om fxml in te laden
 */
public class Preview{
    private final Map<String, QuestionFactory> factories= Map.of("mcs",new McsFactory(),"mcc",new MccFactory(),
            "mci",new MciFactory(),"mr",new MrFactory(),"open",new OpenFactory(),"openi",new OpenIFactory());
    public final Stage previewStage=new Stage();

    public Preview(GridPane pane){

        previewStage.initOwner(pane.getScene().getWindow());
        previewStage.initStyle(StageStyle.TRANSPARENT);
    }
    public void showPreview(Question question,ArrayList<Parts> parts){
        if(!previewStage.isShowing()){

            previewStage.setTitle("Preview: " + question.title());
            QuestionController controlller = factories.get(question.question_type()).loadPreview(question, parts);
            new SceneManager(previewStage).changeScene(controlller.getfxml(), controlller, controlller.getTitle());
        }
    }

    public void closePreview() {
        previewStage.close();
    }

}
