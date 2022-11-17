package sqltask;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import sqltask.applicationmenu.MenuGroup;
import sqltask.applicationmenu.menufunctions.*;
import sqltask.courses.*;
import sqltask.groups.GroupDaoJdbc;
import sqltask.groups.GroupService;
import sqltask.students.*;
import org.springframework.boot.SpringApplication;

@SpringBootApplication
@SuppressWarnings("java:S106")
public class Main {
    public static void main(String[] args) {

        ApplicationContext ctx = SpringApplication.run(Main.class, args);

        StudentDAOJdbc studentDAO = ctx.getBean(StudentDAOJdbc.class);
        System.out.println(studentDAO.getById(11));
        CourseDAOJdbc courseDAO = ctx.getBean(CourseDAOJdbc.class);
        System.out.println(courseDAO.getById(8));
        GroupDaoJdbc groupDAO = ctx.getBean(GroupDaoJdbc.class);
        StudentService studentService = new StudentService(studentDAO, groupDAO);
        GroupService groupService = new GroupService(groupDAO);
        CourseService courseService = new CourseService(courseDAO, studentDAO);

        MenuGroup appMenu = new MenuGroup("SQL APP");
        appMenu.addItem(new AddStudentMenuItem(studentService));
        appMenu.addItem(new DeleteStudentMenuItem(studentService));
        appMenu.addItem(new GroupsByStudentCountMenuItem(groupService));
        appMenu.addItem(new SetCourseMenuItem(courseService));
        appMenu.addItem(new StudentsByCourseMenuItem(courseService));
        appMenu.addItem(new UnlinkCourseMenuItem(courseService));
        appMenu.doAction();
    }
}


