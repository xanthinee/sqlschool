package sqltask;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import sqltask.applicationmenu.AppMenu;
import sqltask.courses.*;
import sqltask.groups.Group;
import sqltask.groups.GroupService;
import sqltask.students.Student;
import sqltask.students.StudentService;

import java.util.*;

@Service
@Profile("!test")
public class InitializeService implements ApplicationRunner {

    private final CourseService courseService;
    private final StudentService studentService;
    private final GroupService groupService;
    private static final String COURSES_LIST_PATH = "data/courses.txt";
    private final AppMenu appMenu;

    public InitializeService(CourseService courseService,
                             StudentService studentService, GroupService groupService, AppMenu appMenu) {
        this.courseService = courseService;
        this.studentService = studentService;
        this.groupService = groupService;
        this.appMenu = appMenu;
    }

    @Override
    public void run(ApplicationArguments args) {

        try {
            List<Course> courses = courseService.makeCoursesList(COURSES_LIST_PATH);
            courseService.saveAll(courses);

            List<Group> groups = groupService.generateGroups();
            groupService.saveAll(groups);

            List<Student> students = studentService.setGroupsId(studentService.generateStudents());
            studentService.saveAll(students);

            courseService.createStdCrsTable();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Student student = new Student(1,1,"a","a");
        studentService.save(student);
        appMenu.doAction();

    }
}
