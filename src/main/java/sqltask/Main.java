package sqltask;

import jdk.dynalink.linker.LinkerServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import sqltask.applicationmenu.MenuGroup;
import sqltask.applicationmenu.menufunctions.*;
import sqltask.connection.DataConnection;
import sqltask.courses.*;
import sqltask.groups.GroupDAO;
import sqltask.groups.GroupDaoJdbc;
import sqltask.groups.GroupService;
import sqltask.groups.GroupDAOImpl;
import sqltask.helpers.SQLScriptRunner;
import sqltask.students.*;
import org.springframework.boot.SpringApplication;
import sqltask.groups.*;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.*;

import java.util.ArrayList;

@SpringBootApplication
@SuppressWarnings("java:S106")
public class Main {

    private final DataConnection ds = new DataConnection("data/connectioninfo.properties");
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
        ApplicationContext ctx = SpringApplication.run(Main.class, args);
        StudentDAOJdbc studentDAO = ctx.getBean(StudentDAOJdbc.class);
        System.out.println(studentDAO.getById(11));
        CourseDAOJdbc courseDAO = ctx.getBean(CourseDAOJdbc.class);
        System.out.println(courseDAO.getById(8));


//        main.startApp();
//
//        MenuGroup menuGroup = new MenuGroup("SQL APP");
//        menuGroup.addItem(new AddStudentMenuItem(main.studentService));
//        menuGroup.addItem(new DeleteStudentMenuItem(main.studentService));
//        menuGroup.addItem(new GroupsByStudentCountMenuItem(main.groupService));
//        menuGroup.addItem(new SetCourseMenuItem(main.courseService));
//        menuGroup.addItem(new StudentsByCourseMenuItem(main.courseService));
//        menuGroup.addItem(new UnlinkCourseMenuItem(main.courseService));
//        menuGroup.doAction();
    }

//    @Bean
//    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
//        return args -> {
//
//            CourseDAOJdbc daoCourses = ctx.getBean(CourseDAOJdbc.class);
//            StudentDAOJdbc daoStudents = ctx.getBean(StudentDAOJdbc.class);
//            GroupDaoJdbc daoGroups = ctx.getBean(GroupDaoJdbc.class);
//
//            System.out.println(daoGroups.getById(1));
//            System.out.println(daoStudents.getById(11));
//
//        };
//    }

}

