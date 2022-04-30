package be.ugent.flash.beheerdersinterface.popups;

import be.ugent.flash.QuestionManager.*;
import be.ugent.flash.jdbc.Question;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.Map;

public class Preview{
    private final Map<String, QuestionFactory> factories= Map.of("mcs",new McsFactory(),"mcc",new MccFactory(),
            "mci",new MciFactory(),"mr",new MrFactory(),"open",new OpenFactory(),"openi",new OpenIFactory());
    public Stage previewStage=new Stage();
    private Question question;

    public Preview(Question question){
        this.question=question;
        previewStage.initModality(Modality.WINDOW_MODAL);
    }
    public void showPreview(){
        if(!previewStage.isShowing()){

        previewStage.setTitle("Preview: "+question.title());
        Scene scene=new Scene(new BorderPane(),500,400);
        previewStage.setScene(scene);

        previewStage.showAndWait();
        }
    }


    public void closePreview() {
        previewStage.close();
    }

}
