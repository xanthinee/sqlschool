package sqltask;

import sqltask.connection.DataSource;
import sqltask.courses.CoursesTableDB;
import sqltask.groups.GroupsTableDB;
import sqltask.students.Student;
import sqltask.students.StudentsTableDB;
import java.util.*;
import sqltask.applicationmenu.*;

import java.sql.Connection;

public class Main {


    public static void main(String[] args) {

        Connection connection = null;
        DataSource dataSource = new DataSource();
//        try {
//            connection = connectionProvider.getConnection();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        MenuHandler menuHandler = new MenuHandler();
//        MenuGroup menuGroup = new MenuGroup("Artem");
//        menuGroup.completeMenu(menuHandler).doAction();

//        sqlS.executeScriptUsingScriptRunner("/Users/xanthine/IdeaProjects/SqlSchool/src/main/resources/sqldata/tables_creation.sql", connection);

//        GroupsTableDB groupsTableDB = new GroupsTableDB(connectionProvider);
//        GroupsByStudentCountMenuItem abc = new GroupsByStudentCountMenuItem(groupsTableDB);
//        abc.doAction();

//        StudentsTableDB studentsTableDB = new StudentsTableDB(dataSource);
//        GroupsTableDB groupsTableDB = new GroupsTableDB(dataSource, "groups");
////        System.out.println(studentsTableDB.getById(2481041).toString());
//        System.out.println(groupsTableDB.getById(8581).toString());

        CoursesTableDB coursesTableDB = new CoursesTableDB(dataSource, "courses", "students_courses");
        System.out.println(coursesTableDB.getById(9).toString());

//        GroupsTableDB groupsTableDB = new GroupsTableDB(dataSource, "groups");
//        System.out.println(groupsTableDB.getById(6539).toString());

//        StudentsTableDB studentsTableDB = new StudentsTableDB(dataSource);
//        System.out.println(studentsTableDB.getById(45).toString());

    }
}
