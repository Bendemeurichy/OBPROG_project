package be.ugent.flash.beheerdersinterface;

import be.ugent.flash.jdbc.*;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;

import java.io.File;

public class BeheerdersinterfaceCompanion {
    public void removeQuestion(BeheerdersinterfaceController controller, TableView<Question> inhoud, File file){
        int questionId=inhoud.getSelectionModel().getSelectedItem().question_id();
        try{
            DataAccesContext dp = new JDBCDataAccesProvider("jdbc:sqlite:"+file.getPath()).getDataAccessContext();
            dp.getPartDAO().removeParts(questionId);
            dp.getQuestionDAO().removeQuestion(questionId);
        } catch (DataAccesException e) {
            throw new RuntimeException(e);
        }
        controller.initialize();
    }

    public void save(BeheerdersinterfaceController controller, Question question,File file){
        //update db
        try{
            DataAccesContext dp= new JDBCDataAccesProvider("jdbc:sqlite:"+file.getPath()).getDataAccessContext();
            dp.getQuestionDAO().updateQuestion(question);
        } catch (DataAccesException e) {
            throw new RuntimeException(e);
        }
        controller.initialize();
    }

    public void loadParts(VBox parts, Question question){
        //
    }
}
