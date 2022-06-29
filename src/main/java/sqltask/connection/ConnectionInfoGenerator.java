package sqltask.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.stream.Stream;
import sqltask.helpers.*;

public class ConnectionInfoGenerator {

    private UserConnection getConnectionInfo(String fileName) {

        customFileReader fileCon = new customFileReader();
        ConnectionParser conP = new ConnectionParser();
        Stream<String> stream = fileCon.readFile(fileName);
        return stream.map(conP::parse).toList().get(0);
    }

    public Connection getConnection(String fileName) throws SQLException {
        UserConnection user = getConnectionInfo(fileName);
        return DriverManager.getConnection(user.host, user.user, user.password);
    }
}
