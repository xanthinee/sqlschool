package sqltask;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import sqltask.connection.*;
import sqltask.courses.CourseMethods;
import sqltask.courses.CoursesTableDB;
import sqltask.helpers.*;
import sqltask.application.methods.*;
import sqltask.connection.ConnectionInfoGenerator;
import sqltask.connection.UserConnection;
import sqltask.groups.GroupsTableDB;

import sqltask.students.StudentsTableDB;

public class Main {

    @SuppressWarnings("java:S106")
    public static void main(String[] args) throws SQLException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {

//        SQLScriptRunner sqlS = new SQLScriptRunner();
        StudentsTableDB studentsTableDB = new StudentsTableDB();
        ConnectionInfoGenerator conGenerator = new ConnectionInfoGenerator();
        CoursesTableDB coursesTableDB = new CoursesTableDB();
        CourseMethods courseMethods = new CourseMethods();
        CompareGroups compareGroups = new CompareGroups();
        DeleteStudent deleteStudent = new DeleteStudent();
        GiveNewCourseToStudent giveNew = new GiveNewCourseToStudent();
        PutNewStudent putNew = new PutNewStudent();
        StudentsByCourse byCourse = new StudentsByCourse();
        UnlinkCourse unlink = new UnlinkCourse();

        Connection connection = conGenerator.getConnection(conGenerator.getConnectionProperties("data/connectioninfo.txt"));
//        sqlS.executeScriptUsingScriptRunner("/Users/xanthine/IdeaProjects/SqlSchool/src/main/resources/sqldata/tables_creation.sql", connection);


    }
}
