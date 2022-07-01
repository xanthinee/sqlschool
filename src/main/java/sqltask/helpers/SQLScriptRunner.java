package sqltask.helpers;

import org.apache.ibatis.jdbc.ScriptRunner;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.Connection;
import java.sql.SQLException;

public class SQLScriptRunner {

    public void executeScriptUsingScriptRunner(String fileName, Connection con) {
        try (Connection connection = con) {
            ScriptRunner scriptExecutor = new ScriptRunner(connection);
            Reader reader = new BufferedReader(new FileReader(fileName));
            scriptExecutor.runScript(reader);
            reader.close();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }
}
