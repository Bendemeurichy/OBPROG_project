package be.ugent.flash.jdbc;

public interface DataAccesProvider {

    DataAccesContext getDataAccessContext() throws DataAccesException;
}
