package tools;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseFactory {


    Environment config;


    public DatabaseFactory(Environment config) {
        this.config = config;
    }


    public Connection connection() throws SQLException {
        return DriverManager.getConnection(config.jdbcUrl, config.username, config.password);
    }

}
