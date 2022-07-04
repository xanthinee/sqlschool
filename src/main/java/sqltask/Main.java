package sqltask;

import sqltask.applicationmenu.Controller;
import sqltask.applicationmenu.MenuGroup;
import sqltask.connection.ConnectionProvider;
import sqltask.studentscourses.StudentsCoursesTableDB;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {

    @SuppressWarnings("java:S106")
    public static void main(String[] args) {

        Connection connection = null;
        ConnectionProvider connectionProvider = new ConnectionProvider();
        try {
            connection = connectionProvider.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Controller controller = new Controller();
        MenuGroup.completeMenu(controller).doAction();
//        sqlS.executeScriptUsingScriptRunner("/Users/xanthine/IdeaProjects/SqlSchool/src/main/resources/sqldata/tables_creation.sql", connection);

    }
}
