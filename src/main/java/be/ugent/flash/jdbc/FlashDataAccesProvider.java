package be.ugent.flash.jdbc;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


/**
 * Data acces provider voor meegegeven databank
 */
public class FlashDataAccesProvider implements DataAccesProvider {

    private final Properties databaseProperties;

    public FlashDataAccesProvider(String resourceName){
        //ophalen van gegevens uit databank
        try(InputStream inp = FlashDataAccesProvider.class.getResourceAsStream(resourceName)){
            databaseProperties= new Properties();
            databaseProperties.load(inp);
        } catch (IOException ex){
            throw new RuntimeException("Could not read database properties",ex);
        }
    }


    /**
     * Open de verbinding met de databank
     */
    private Connection getConnection() throws SQLException {
        String user= databaseProperties.getProperty("user");
        if(user != null){
            return DriverManager.getConnection(databaseProperties.getProperty("url"),user,
                    databaseProperties.getProperty("password"));
        } else{
            return DriverManager.getConnection(databaseProperties.getProperty("url"));
        }
    }

    public DataAccesContext getDataAccesContext() throws DataAccesException{
        try {
            return new JDBCDataAccesContext(getConnection());
        } catch (SQLException ex){
            throw new DataAccesException("could not create data acces context", ex);
        }
    }
}
