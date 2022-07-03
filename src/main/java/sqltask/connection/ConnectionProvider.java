package sqltask.connection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionProvider {

    Properties properties;

    public ConnectionProvider() {
        try {
            this.properties = new Properties();
            properties.load(getClass().getClassLoader().getResourceAsStream("data/connectioninfo.properties"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(properties.getProperty("db.url"), properties.getProperty("db.user")
                , properties.getProperty("db.password"));
    }
}
