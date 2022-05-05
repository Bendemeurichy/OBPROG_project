package be.ugent.flash.jdbc;

import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCDataAccesProvider implements DataAccesProvider {
    private final String database;

    public JDBCDataAccesProvider(String resource) {
        this.database = resource;
    }

    @Override
    public DataAccesContext getDataAccessContext() throws DataAccesException {
        try {
            return new JDBCDataAccesContext(DriverManager.getConnection(database));
        } catch (SQLException e) {
            throw new DataAccesException("Databank bestaat niet of kan niet geopend worden", e);
        }
    }
}
