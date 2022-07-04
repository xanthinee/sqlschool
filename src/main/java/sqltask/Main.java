package sqltask;

import java.sql.Connection;
import java.sql.SQLException;

import sqltask.groups.GroupsTableDB;
import sqltask.helpers.*;
import sqltask.application.menu.ApplicationMenu;
import sqltask.application.menu.MenuList;
import sqltask.courses.MethodsForCourses;
import sqltask.courses.CoursesTableDB;
import sqltask.application.methods.*;
import sqltask.application.methods.comparison.*;
import sqltask.application.methods.courseaddition.*;
import sqltask.application.methods.coursemembers.*;
import sqltask.connection.ConnectionProvider;
import sqltask.students.MethodsForStudents;
import sqltask.students.StudentsTableDB;
import sqltask.studentscourses.StudentsCoursesTableDB;

public class Main {

    @SuppressWarnings("java:S106")
    public static void main(String[] args) {

        SQLScriptRunner sqlS = new SQLScriptRunner();
        StudentsTableDB studentsTableDB = new StudentsTableDB();
        ConnectionProvider conGenerator = new ConnectionProvider();
        CoursesTableDB coursesTableDB = new CoursesTableDB();
        MethodsForCourses methodsForCourses = new MethodsForCourses();
        GroupsComparison groupsComparison = new GroupsComparison();
        DeleteStudent deleteStudent = new DeleteStudent();
        CourseAddition giveNew = new CourseAddition();
        PutNewStudent putNew = new PutNewStudent();
        StudentsByCourse byCourse = new StudentsByCourse();
        UnlinkCourse unlink = new UnlinkCourse();
        ApplicationMenu appMenu = new ApplicationMenu();
        MenuList menuMethods = new MenuList();
        GroupsTableDB groupsDB = new GroupsTableDB();
        StudentsCoursesTableDB studCourses = new StudentsCoursesTableDB();
        MethodsForStudents studentsMethods = new MethodsForStudents();
        StudentsTableDB studentsDB = new StudentsTableDB();

        Connection connection = null;
        try {
            connection = conGenerator.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
//        sqlS.executeScriptUsingScriptRunner("/Users/xanthine/IdeaProjects/SqlSchool/src/main/resources/sqldata/tables_creation.sql", connection);

        System.out.println(groupsComparison.doAction(connection));
//        System.out.println(appMenu.implementMenu(connection));


    }
}
