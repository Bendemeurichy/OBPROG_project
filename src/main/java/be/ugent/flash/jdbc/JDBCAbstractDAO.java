package be.ugent.flash.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JDBCAbstractDAO {
    private final Connection connection;

    public JDBCAbstractDAO(Connection connection){
        this.connection=connection;
    }

    protected PreparedStatement prepare(String sql) throws SQLException{
        return connection.prepareStatement(sql);
    }
}
