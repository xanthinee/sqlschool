package sqltask.connection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionProvider {

    public Connection getConnection() throws SQLException, IOException {
        Properties properties = new Properties();
        properties.load(getClass().getClassLoader().getResourceAsStream("data/connectioninfo.properties"));
        return DriverManager.getConnection(properties.getProperty("db.url"), properties.getProperty("db.user")
                , properties.getProperty("db.password"));
    }
}
