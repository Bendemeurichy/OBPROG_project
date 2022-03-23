package be.ugent.flash.jdbc;

public interface QuestionDAO {
    Iterable<Question> allQuestionData() throws DataAccesException;

    Question specificQuestion(int id) throws DataAccesException;
}
