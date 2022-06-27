package sqltask;

import java.sql.SQLException;
import java.util.List;

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

        StudentsCoursesTable stcoT = new StudentsCoursesTable();
        System.out.println(stcoT.printStudCourseTable());


//        GroupsTable groupsTable = new GroupsTable();
////        groupsTable.putGroupIntoTable();
//        groupsTable.deleteGroupsFromTable();
//        System.out.println(groupsTable.printGroupsTable());
//
//        System.out.println("-------------------------------------");
//
//        CoursesTable coursesT = new CoursesTable();
//        coursesT.deleteCoursesFromTable();
//
//        System.out.println("-------------------------------------");
//        StudentsTable st = new StudentsTable();
//        st.deleteStudentsFromTable();
//    }
    }
}
