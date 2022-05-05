package be.ugent.flash.beheerdersinterface;

import be.ugent.flash.beheerdersinterface.popups.ErrorDialog;
import be.ugent.flash.beheerdersinterface.questionparts.Partsloader;
import be.ugent.flash.jdbc.*;
import javafx.scene.control.TableView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * klasse om minder javafx intensieve methoden te verwerken zoals vragen bijwerken, verwijderen, etc.
 */
public class BeheerdersinterfaceCompanion {
    public void removeQuestion(BeheerdersinterfaceController controller, TableView<Question> inhoud, File file) {
        int questionId = inhoud.getSelectionModel().getSelectedItem().question_id();
        try {
            DataAccesContext dp = new JDBCDataAccesProvider("jdbc:sqlite:" + file.getPath()).getDataAccessContext();
            dp.getPartDAO().removeParts(questionId);
            dp.getQuestionDAO().removeQuestion(questionId);
        } catch (DataAccesException e) {
            new ErrorDialog().display("kon vraag niet verwijderen");
        }
        controller.initialize();
    }

    //update de db, parts van vraag worden eerst allemaal verwijderd en dan opnieuw toegevoegd om methodes in dao makkelijk te houden
    public void save(BeheerdersinterfaceController controller, Question question, File file, Partsloader partsloader) {
        try {
            DataAccesContext dp = new JDBCDataAccesProvider("jdbc:sqlite:" + file.getPath()).getDataAccessContext();
            dp.getQuestionDAO().updateQuestion(question);
            dp.getPartDAO().removeParts(question.question_id());
            if (Objects.equals(question.question_type(), "mci")){
                dp.getPartDAO().addImageParts(partsloader.getImageParts());
            } else {
                dp.getPartDAO().addParts(partsloader.getParts());
            }
        } catch (DataAccesException e) {
            new ErrorDialog().display("kon vraag niet opslaan");
        }
        controller.initialize();
    }

    public void addParts(Parts parts, File db) {
        try {
            DataAccesContext dp = new JDBCDataAccesProvider("jdbc:sqlite:" + db.getPath()).getDataAccessContext();
            dp.getPartDAO().addParts(new ArrayList<>(List.of(parts)));
        } catch (DataAccesException e) {
            new ErrorDialog().display("kon antwoordmogelijkheden niet toevoegen");
        }
    }

    //update correcte vraag in db
    public void addCorrectQuestion(Question question, String correctAnswer, File db) {
        Question changed = new Question(question.question_id(), question.title(), question.text_part(), question.image_part(), question.question_type(), correctAnswer);
        new JDBCDataAccesProvider("jdbc:sqlite:" + db.getPath()).getDataAccessContext().getQuestionDAO().updateQuestion(changed);
    }
}
