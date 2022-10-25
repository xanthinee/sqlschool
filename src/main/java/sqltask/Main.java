package sqltask;

import sqltask.connection.DataSource;
import sqltask.courses.*;
import sqltask.groups.GroupDAO;
import sqltask.groups.GroupService;
import sqltask.groups.GroupDAOImpl;
import sqltask.helpers.SQLScriptRunner;
import sqltask.students.*;
import sqltask.groups.*;
import sqltask.applicationmenu.*;
import sqltask.applicationmenu.menufunctions.*;

import java.sql.SQLException;
import java.util.*;

@SuppressWarnings("java:S106")
public class Main {

    DataSource ds = new DataSource();
    SQLScriptRunner sqlRunner = new SQLScriptRunner();
    StudentDAO studentDAO = new StudentDAOImpl(ds);
    GroupDAO groupDAO = new GroupDAOImpl(ds);
    StudentService studentService = new StudentService(studentDAO, groupDAO);
    CourseDAO courseDAO = new CourseDAOImpl(ds);
    GroupService groupService = new GroupService(groupDAO);
    CourseService courseService = new CourseService(courseDAO, studentDAO);

    public void startApp() {

        try {
            sqlRunner.executeScriptUsingScriptRunner("src/main/resources/sqldata/tables_creation.sql",
                    ds.getConnection());

            List<Course> courses = CourseUtils.makeCoursesList("data/courses.txt");
            courseDAO.saveAll(courses);

            List<Group> groups = groupService.generateGroups();
            groupDAO.saveAll(groups);

            List<Student> students = studentService.generateStudents();
            studentDAO.saveAll(students);
            studentService.setGroupsToStudents();

            courseService.createStdCrsTable();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        DataSource dataSource = new DataSource();

        StudentDAOImpl studentDAO = new StudentDAOImpl(dataSource);
        GroupDAOImpl groupDAO = new GroupDAOImpl(dataSource);
        StudentService studentService = new StudentService(studentDAO, groupDAO);
        GroupService groupService = new GroupService(groupDAO);
        CourseDAOImpl courseDAO = new CourseDAOImpl(dataSource);
        CourseService courseService = new CourseService(courseDAO, studentDAO);

        Main main = new Main();
        main.startApp();

        MenuGroup menuGroup = new MenuGroup("SQL APP");
        menuGroup.addItem(new AddStudentMenuItem(studentService));
        menuGroup.addItem(new DeleteStudentMenuItem(studentService));
        menuGroup.addItem(new GroupsByStudentCountMenuItem(groupService));
        menuGroup.addItem(new SetCourseMenuItem(courseService));
        menuGroup.addItem(new StudentsByCourseMenuItem(courseService));
        menuGroup.addItem(new UnlinkCourseMenuItem(courseService));
        menuGroup.doAction();
    }
}
