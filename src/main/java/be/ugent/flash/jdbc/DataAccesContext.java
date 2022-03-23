package be.ugent.flash.jdbc;

public interface DataAccesContext extends AutoCloseable {

    QuestionDAO getQuestionDAO();

    PartDAO getPartDAO();

    void close() throws DataAccesException;
}
