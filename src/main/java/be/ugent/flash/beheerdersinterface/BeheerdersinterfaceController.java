package be.ugent.flash.beheerdersinterface;

import be.ugent.flash.jdbc.DataAccesException;
import be.ugent.flash.jdbc.JDBCDataAccesProvider;
import be.ugent.flash.jdbc.Question;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.io.File;

public class BeheerdersinterfaceController extends MenuOpties{
    @FXML
    public TableColumn<String,String> titel;
    public TableColumn<String,String> type;
    public TableView<Question> inhoud;

    private File questiondb;
    public BeheerdersinterfaceController(File file) throws DataAccesException {
        this.questiondb=file;
        ObservableList<Question> questions= FXCollections.observableArrayList(
                new JDBCDataAccesProvider(file.getAbsolutePath()).getDataAccessContext().getQuestionDAO().allQuestionData());
    }
}
