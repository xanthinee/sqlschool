package sqltask;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Random;


import static sqltask.SQLScriptRunner.executeScriptUsingScriptRunner;

public class Main {

    @SuppressWarnings("java:S106")
    public static void main(String[] args) throws SQLException {

//        try {
//            executeScriptUsingScriptRunner("/Users/xanthine/IdeaProjects/SqlSchool/src/main/resources/sqldata/tables_creation.sql");
//        } catch (IOException | SQLException e) {
//            e.printStackTrace();
//        } catch (ClassNotFoundException e) {
//            throw new RuntimeException(e);
//        }

        GroupsTable groupsTable = new GroupsTable();
//        groupsTable.putGroupIntoTable();
//        groupsTable.deleteGroupsFromTable();
        System.out.println(groupsTable.printGroupsTable());

        System.out.println("-------------------------------------");

        CoursesTable coursesT = new CoursesTable();
        System.out.println(coursesT.printCoursesTable());

        System.out.println("-------------------------------------");
        StudentsTable st = new StudentsTable();
        System.out.println(st.printStudentsTable());
    }
}
