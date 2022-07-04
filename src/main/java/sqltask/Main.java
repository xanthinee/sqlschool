package sqltask;

import sqltask.connection.ConnectionProvider;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {

    @SuppressWarnings("java:S106")
    public static void main(String[] args) {

        ConnectionProvider connectionProvider = new ConnectionProvider();
        try {
            Connection connection = connectionProvider.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }

//        sqlS.executeScriptUsingScriptRunner("/Users/xanthine/IdeaProjects/SqlSchool/src/main/resources/sqldata/tables_creation.sql", connection);

    }
}
