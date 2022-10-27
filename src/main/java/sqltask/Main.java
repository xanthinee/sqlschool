package sqltask;

import sqltask.connection.DataSource;
import sqltask.courses.*;
import sqltask.groups.Group;
import sqltask.groups.GroupDAO;
import sqltask.groups.GroupService;
import sqltask.groups.GroupDAOImpl;
import sqltask.helpers.SQLScriptRunner;
import sqltask.students.*;
import sqltask.applicationmenu.*;
import sqltask.applicationmenu.menufunctions.*;

import java.sql.SQLException;
import java.util.*;

@SuppressWarnings("java:S106")
public class Main {

    private final DataSource ds = new DataSource("data/connectioninfo.properties");
    private final SQLScriptRunner sqlRunner = new SQLScriptRunner();
    private final StudentDAO studentDAO = new StudentDAOImpl(ds);
    private final GroupDAO groupDAO = new GroupDAOImpl(ds);
    private final StudentService studentService = new StudentService(studentDAO, groupDAO);
    private final CourseDAO courseDAO = new CourseDAOImpl(ds);
    private final GroupService groupService = new GroupService(groupDAO);
    private final CourseService courseService = new CourseService(courseDAO, studentDAO);

    public void startApp() {

        try {
            sqlRunner.executeScriptUsingScriptRunner("sqldata/tables_creation.sql",
                    ds.getConnection());

            List<sqltask.courses.Course> courses = CourseUtils.makeCoursesList("data/courses.txt");
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

        Main main = new Main();
        main.startApp();

        MenuGroup menuGroup = new MenuGroup("SQL APP");
        menuGroup.addItem(new AddStudentMenuItem(main.studentService));
        menuGroup.addItem(new DeleteStudentMenuItem(main.studentService));
        menuGroup.addItem(new GroupsByStudentCountMenuItem(main.groupService));
        menuGroup.addItem(new SetCourseMenuItem(main.courseService));
        menuGroup.addItem(new StudentsByCourseMenuItem(main.courseService));
        menuGroup.addItem(new UnlinkCourseMenuItem(main.courseService));
        menuGroup.doAction();
    }
}
