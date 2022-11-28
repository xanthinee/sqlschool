package sqltask;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
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

    public InitializeService(CourseService courseService,
                             StudentService studentService, GroupService groupService) {
        this.courseService = courseService;
        this.studentService = studentService;
        this.groupService = groupService;
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
    }
}
