package be.ugent.flash.jdbc;

import java.util.ArrayList;

public interface QuestionDAO {
    ArrayList<Question> allQuestionData() throws DataAccesException;

    Question specificQuestion(int id) throws DataAccesException;

    void removeQuestion(int questionId) throws DataAccesException;

    void updateQuestion(Question question) throws DataAccesException;
}
