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
    ConnectionInfoGenerator conInfo = new ConnectionInfoGenerator();
    private static final String CONNECTION_FILE = "data/connectioninfo";

    public void executeScriptUsingScriptRunner(String str) {
        try (Connection connection = conInfo.getConnection(CONNECTION_FILE)) {
            ScriptRunner scriptExecutor = new ScriptRunner(connection);
            Reader reader = new BufferedReader(new FileReader(str));
            scriptExecutor.runScript(reader);
            reader.close();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }
}
