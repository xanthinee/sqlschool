package sqltask;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import sqltask.applicationmenu.AppMenu;
import sqltask.courses.Course;
import sqltask.courses.CourseDAOJdbc;
import sqltask.courses.CourseService;
import sqltask.groups.Group;
import sqltask.groups.GroupService;
import sqltask.students.Student;
import sqltask.students.StudentDAOJdbc;
import sqltask.students.StudentService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class InitializeServiceTest {

    @SpringBootTest
    @ActiveProfiles("test")
    @Nested
    class AppRunnerTest {

        @MockBean
        InitializeService initializeService;
        @MockBean
        GroupService groupService;

        @Test
        void whenContextLoads_thenRunnersRun() {
            verify(initializeService, times(1)).run(any());
        }
    }


    @Nested
    @ExtendWith(MockitoExtension.class)
    class AppRunnerInsideMethodsTest {

        @InjectMocks
        InitializeService initializeService;
        @Mock
        GroupService groupService;
        @Mock
        CourseService courseService;
        @Mock
        StudentService studentService;
        @Mock
        JdbcTemplate jdbcTemplate;
        @Mock
        StudentDAOJdbc studentDao;
        @Mock
        CourseDAOJdbc courseDao;
        @Mock
        AppMenu appMenu;
        @Test
        void initializeGroups() {
            List<Group> groups = new ArrayList<>(Arrays.asList(
                    new Group(1, "11-aa"),
                    new Group(2,"22-bb")
            ));
            Mockito.when(groupService.generateGroups()).thenReturn(groups);
            Mockito.doNothing().when(appMenu).doAction();
            initializeService.run(null);
            verify(groupService, times(1)).saveAll(groups);
        }

        @Test
        void initializeStudents() {

            List<Student> creation = new ArrayList<>(Arrays.asList(
                    new Student(1,null,"name", "surname"),
                    new Student(2,null, "name2", "surname2")
            ));

            List<Student> groupSetting = new ArrayList<>(Arrays.asList(
                    new Student(1, 1, "name", "surname"),
                    new Student(2, null, "name2", "surname2")
            ));
            Mockito.when(studentService.generateStudents()).thenReturn(creation);
            Mockito.when(studentService.setGroupsId(creation)).thenReturn(groupSetting);
            Mockito.doNothing().when(appMenu).doAction();
            initializeService.run(null);
            verify(studentService, times(1)).saveAll(groupSetting);
        }

        @Test
        void initializeCourses() {

            List<Course> courses = new ArrayList<>(Arrays.asList(
                    new Course(1,"name", "description"),
                    new Course(2, "name2", "description2")
            ));
            Mockito.doReturn(courses).when(courseService).makeCoursesList(anyString());
            Mockito.doNothing().when(appMenu).doAction();
            initializeService.run(null);
            verify(courseService, times(1)).saveAll(courses);
        }

        @Test
        void initializeStudentsCourses() {
            Mockito.doNothing().when(appMenu).doAction();
            initializeService.run(null);
            verify(courseService, times(1)).createStdCrsTable();
        }
    }
}
