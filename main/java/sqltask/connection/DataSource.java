package sqltask.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DataSource {

    Properties properties;

    public DataSource(String filePath) {
        try {
            this.properties = new Properties();
            properties.load(getClass().getClassLoader().getResourceAsStream(filePath));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(properties.getProperty("db.url"), properties.getProperty("db.user")
                , properties.getProperty("db.password"));
    }
}
