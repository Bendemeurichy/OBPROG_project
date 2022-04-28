package be.ugent.flash.beheerdersinterface;

import be.ugent.flash.jdbc.DataAccesContext;
import be.ugent.flash.jdbc.DataAccesException;
import be.ugent.flash.jdbc.JDBCDataAccesProvider;
import be.ugent.flash.jdbc.Question;
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

    public void save(BeheerdersinterfaceController controller, Question vraag){
        //update db
        controller.initialize();
    }

    public void loadParts(VBox parts, Question question){
        //
    }
}
