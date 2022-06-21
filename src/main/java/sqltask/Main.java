package sqltask;

import java.io.IOException;
import java.sql.SQLException;

import static sqltask.SQLScriptRunner.executeScriptUsingScriptRunner;

public class Main {

    @SuppressWarnings("java:S106")
    public static void main(String[] args) {

        try {
            executeScriptUsingScriptRunner("/Users/xanthine/IdeaProjects/SqlSchool/src/main/resources/sqldata/tables_creation.sql");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
