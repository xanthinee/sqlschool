package sqltask;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionProviderTest {

    Properties properties;

    public ConnectionProviderTest() {
        try {
            this.properties = new Properties();
            properties.load(getClass().getClassLoader().getResourceAsStream("data/connectioninfotests.properties"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(properties.getProperty("db.url"), properties.getProperty("db.user")
                , properties.getProperty("db.password"));
    }
}
