package sqltask.helpers;

import org.apache.ibatis.jdbc.ScriptRunner;
import sqltask.connection.ConnectionInfoGenerator;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.Connection;
import java.sql.SQLException;

public class SQLScriptRunner {
    private final ConnectionInfoGenerator conInfo = new ConnectionInfoGenerator();
    private final String connectionFile = "data/connectioninfo";

    public void executeScriptUsingScriptRunner(String str) throws SQLException, IOException, ClassNotFoundException {
        Reader reader = null;
        try (Connection connection = conInfo.getConnection(connectionFile)) {
            ScriptRunner scriptExecutor = new ScriptRunner(connection);
            reader = new BufferedReader(new FileReader(str));
            scriptExecutor.runScript(reader);
            reader.close();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }
}
