package be.ugent.flash.beheerdersinterface.popups;

import be.ugent.flash.QuestionManager.QuestionManager;
import be.ugent.flash.jdbc.DataAccesException;
import be.ugent.flash.jdbc.JDBCDataAccesProvider;
import be.ugent.flash.jdbc.Question;
import javafx.stage.Stage;

import java.io.File;

public class Preview {
    public void showPreview(Question question, File file) throws DataAccesException {
        Stage previewStage=new Stage();
        QuestionManager manager= new QuestionManager(new JDBCDataAccesProvider("jdbc:sqlite:"+file.getPath()),previewStage);
        manager.loadPreview(question);
        manager.start();
    }
}
