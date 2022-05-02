package be.ugent.flash.beheerdersinterface;

import be.ugent.flash.beheerdersinterface.popups.ErrorDialog;
import be.ugent.flash.beheerdersinterface.questionparts.Partsloader;
import be.ugent.flash.jdbc.*;
import javafx.scene.control.TableView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

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

    public void save(BeheerdersinterfaceController controller, Question question, File file, Partsloader partsloader){
        //update db
        //TODO parts ook opslaan: eerst allemaal weg, dan opslaan
        try{
            DataAccesContext dp= new JDBCDataAccesProvider("jdbc:sqlite:"+file.getPath()).getDataAccessContext();
            dp.getQuestionDAO().updateQuestion(question);
            dp.getPartDAO().removeParts(question.question_id());
            dp.getPartDAO().addParts(partsloader.getParts());
        } catch (DataAccesException e) {
            throw new RuntimeException(e);
        }
        controller.initialize();
    }

    public void addParts(Parts parts,File db) {
        try{
            DataAccesContext dp= new JDBCDataAccesProvider("jdbc:sqlite:"+db.getPath()).getDataAccessContext();
            dp.getPartDAO().addParts(new ArrayList<>(List.of(parts)));
        } catch (DataAccesException e) {
            throw new RuntimeException(e);
        }
    }

    public void addCorrectQuestion(Question question, String correctAnswer,File db) {
        try{
            Question changed=new Question(question.question_id(), question.title(), question.text_part(), question.image_part(), question.question_type(), correctAnswer);
            new JDBCDataAccesProvider("jdbc:sqlite:"+db.getPath()).getDataAccessContext().getQuestionDAO().updateQuestion(changed);
        }  catch (DataAccesException e) {
            throw new RuntimeException(e);
        }
    }
}
