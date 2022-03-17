package be.ugent.flash.jdbc;

import java.sql.Connection;
import java.sql.SQLException;

public class JDBCDataAccesContext implements DataAccesContext {
    private final Connection connection;

    public JDBCDataAccesContext(Connection connection) {
        this.connection=connection;
    }

    @Override
    public QuestionsdDAO getCardDAO() {
        return new QuestionsdDAO(connection);
    }

    @Override
    public PartsDAO getPartsDAO() {
        return new PartsDAO(connection);
    }

    @Override
    public void close() throws DataAccesException{
        try{
            connection.close();
        } catch (SQLException ex){
            throw new DataAccesException("Could not close connection",ex);
        }
    }
}

//https://github.ugent.be/kcoolsae/ObjProg/tree/master/src/main/java/be/ugent/objprog/contacts/jdbc
