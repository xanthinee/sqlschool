package sqltask;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.apache.ibatis.jdbc.ScriptRunner;

public class SQLScriptRunner {

    static void executeScriptUsingScriptRunner(String str) throws ClassNotFoundException, SQLException, IOException {
        Reader reader = null;
        Connection connection = null;

        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgreSQLTaskFoxminded",
                    "postgres", "7777");
            ScriptRunner scriptExecutor = new ScriptRunner(connection);
            reader = new BufferedReader(new FileReader(str));
            scriptExecutor.runScript(reader);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                reader.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
    }
}
