package sqltask;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import sqltask.applicationmenu.MenuGroup;
import sqltask.applicationmenu.menufunctions.*;
import sqltask.courses.*;
import sqltask.groups.GroupService;
import sqltask.students.*;
import org.springframework.boot.SpringApplication;

@SpringBootApplication
@SuppressWarnings("java:S106")
public class Main {
    public static void main(String[] args) {

        ApplicationContext ctx = SpringApplication.run(Main.class, args);
        StudentService studentService = ctx.getBean(StudentService.class);
        GroupService groupService = ctx.getBean(GroupService.class);
        CourseService courseService = ctx.getBean(CourseService.class);

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


