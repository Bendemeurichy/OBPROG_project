package be.ugent.flash.jdbc;

import java.util.ArrayList;

public interface QuestionDAO {
    ArrayList<Question> allQuestionData() throws DataAccesException;

    Question specificQuestion(int id) throws DataAccesException;
}
