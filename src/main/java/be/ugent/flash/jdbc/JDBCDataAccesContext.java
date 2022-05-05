package be.ugent.flash.jdbc;

import java.sql.Connection;
import java.sql.SQLException;

public class JDBCDataAccesContext implements DataAccesContext {

    private final Connection connection;

    public JDBCDataAccesContext(Connection connection) {
        this.connection = connection;
    }

    @Override
    public QuestionDAO getQuestionDAO() {
        return new JDBCQuestionDAO(connection);
    }

    @Override
    public PartDAO getPartDAO() {
        return new JDBCPartDAO(connection);
    }

    @Override
    public void close() throws DataAccesException {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new DataAccesException("Could not close connection", e);
        }
    }
}
