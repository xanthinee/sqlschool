package sqltask.helpers;

import org.apache.ibatis.jdbc.ScriptRunner;

import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;

public class SQLScriptRunner {

    public void executeScriptUsingScriptRunner(String fileName, Connection con) {
        try (Connection connection = con) {
            ScriptRunner scriptExecutor = new ScriptRunner(connection);
            Reader reader = new BufferedReader(new InputStreamReader(getClass().getClassLoader().getResourceAsStream(fileName)));
            scriptExecutor.runScript(reader);
            reader.close();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }
}
