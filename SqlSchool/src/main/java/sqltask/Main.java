package sqltask;

import sqltask.applicationmenu.MenuHandler;
import sqltask.applicationmenu.MenuGroup;
import sqltask.connection.ConnectionProvider;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {


    public static void main(String[] args) {

        Connection connection = null;
        ConnectionProvider connectionProvider = new ConnectionProvider();
        try {
            connection = connectionProvider.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        MenuHandler menuHandler = new MenuHandler();
        MenuGroup.completeMenu(menuHandler).doAction();

//        sqlS.executeScriptUsingScriptRunner("/Users/xanthine/IdeaProjects/SqlSchool/src/main/resources/sqldata/tables_creation.sql", connection);

    }
}
