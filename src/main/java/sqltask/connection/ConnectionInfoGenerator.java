package sqltask.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.stream.Stream;
import sqltask.helpers.*;

public class ConnectionInfoGenerator {

    public UserConnection getConnectionProperties(String fileName) {

        customFileReader fileCon = new customFileReader();
        ConnectionParser conP = new ConnectionParser();
        Stream<String> stream = fileCon.readFile(fileName);
        return stream.map(conP::parse).toList().get(0);
    }

    public Connection getConnection(UserConnection userCon) throws SQLException {
        return DriverManager.getConnection(userCon.host, userCon.user, userCon.password);
    }
}
