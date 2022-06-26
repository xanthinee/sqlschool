package sqltask;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.stream.Stream;

public class ConnectionInfoGenerator {

    private UserConnection getConnectionInfo(String fileName) {

        FileConverter fileCon = new FileConverter();
        ConnectionParser conP = new ConnectionParser();
        Stream<String> stream = fileCon.readFile(fileName);
        return stream.map(conP::parse).toList().get(0);
    }

    public Connection getConnection(String fileName) throws SQLException {
        UserConnection user = getConnectionInfo(fileName);
        return DriverManager.getConnection(user.host, user.user, user.password);
    }
}
