package be.ugent.flash.jdbc;

public interface DataAccesContext extends AutoCloseable {

    QuestionsdDAO getCardDAO();

    PartsDAO getPartsDAO();

    void close() throws DataAccesException;
}
