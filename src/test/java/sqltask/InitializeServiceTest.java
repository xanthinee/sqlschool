package sqltask;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.jdbc.core.JdbcTemplate;
import sqltask.courses.CourseDAOJdbc;
import sqltask.courses.CourseService;
import sqltask.courses.CourseUtils;
import sqltask.groups.Group;
import sqltask.courses.Course;
import sqltask.groups.GroupService;
import sqltask.students.Student;
import sqltask.groups.GroupDaoJdbc;
import sqltask.students.StudentDAOJdbc;
import sqltask.students.StudentService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InitializeServiceTest {

    @Mock
    GroupDaoJdbc groupDao;
    @Mock
    StudentDAOJdbc studentDao;
    @Mock
    CourseDAOJdbc courseDao;
    @Mock
    CourseService courseService;
    @Mock
    GroupService groupService;
    @Mock
    CourseUtils courseUtils;
    @Mock
    ApplicationArguments args;
    @Mock
    StudentService studentService;
    @Mock
    JdbcTemplate jdbcTemplate;
    @InjectMocks
    @SpyBean
    InitializeService initializeService;

    @Test
    void whenContextLoads_thenRunnersRun() {
        verify(initializeService, times(1)).run(any());
    }

    @Test
    void initializeGroups() {
        List<Group> groups = new ArrayList<>(Arrays.asList(
                new Group(1, "11-aa"),
                new Group(2,"22-bb")
        ));

        List<Student> students = new ArrayList<>(Arrays.asList(
                new Student(1,1,"a","a")
        ));
        Mockito.when(studentService.generateStudents()).thenReturn(students);
        Mockito.when(studentService.setGroupsId(students)).thenReturn(students);
        Mockito.doNothing().when(studentDao).saveAll(any());
        Mockito.doNothing().when(courseDao).saveAll(any());
        Mockito.doNothing().when(courseService).createStdCrsTable();
        Mockito.when(groupService.generateGroups()).thenReturn(groups);
        initializeService.run(null);
        verify(groupDao, times(1)).saveAll(groups);
    }

    @Test
    void initializeCourses() {

        List<Course> courses = new ArrayList<>(Arrays.asList(
                new Course(1,"name", "description"),
                new Course(2, "name2", "description2")
        ));
        verify(courseDao, times(1)).saveAll(courses);
    }

    @Test
    void initializeStudents() {
        List<Student> students = new ArrayList<>(Arrays.asList(
                new Student(1,1,"name", "surname"),
                new Student(2,2,"name2", "surname2")
        ));
        verify(studentDao, times(1)).saveAll(students);
    }

    @Test
    void initializeStudCourses() {
        initializeService.run(any());
        verify(courseService, times(1)).createStdCrsTable();
    }
}
