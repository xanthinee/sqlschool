package sqltask;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;

import sqltask.groups.GroupsTableDB;
import sqltask.helpers.*;
import sqltask.application.menu.ApplicationMenu;
import sqltask.application.menu.MethodsMenu;
import sqltask.courses.CourseMethods;
import sqltask.courses.CoursesTableDB;
import sqltask.application.methods.*;
import sqltask.application.methods.comparison.*;
import sqltask.application.methods.courseaddition.*;
import sqltask.application.methods.coursemembers.*;
import sqltask.connection.ConnectionProvider;
import sqltask.students.StudentsTableDB;
import sqltask.studentscourses.StudentsCoursesTableDB;

public class Main {

    @SuppressWarnings("java:S106")
    public static void main(String[] args) throws SQLException, InvocationTargetException, NoSuchMethodException, IllegalAccessException, IOException {

        SQLScriptRunner sqlS = new SQLScriptRunner();
        StudentsTableDB studentsTableDB = new StudentsTableDB();
        ConnectionProvider conGenerator = new ConnectionProvider();
        CoursesTableDB coursesTableDB = new CoursesTableDB();
        CourseMethods courseMethods = new CourseMethods();
        CompareGroups compareGroups = new CompareGroups();
        DeleteStudent deleteStudent = new DeleteStudent();
        GiveNewCourse giveNew = new GiveNewCourse();
        PutNewStudent putNew = new PutNewStudent();
        StudentsByCourse byCourse = new StudentsByCourse();
        UnlinkCourse unlink = new UnlinkCourse();
        ApplicationMenu appMenu = new ApplicationMenu();
        MethodsMenu menuMethods = new MethodsMenu();
        GroupsTableDB groupsDB = new GroupsTableDB();
        StudentsCoursesTableDB studCourses = new StudentsCoursesTableDB();

        Connection connection = conGenerator.getConnection();
//        sqlS.executeScriptUsingScriptRunner("/Users/xanthine/IdeaProjects/SqlSchool/src/main/resources/sqldata/tables_creation.sql", connection);
        deleteStudent.deleteStudent(connection);
    }
}
