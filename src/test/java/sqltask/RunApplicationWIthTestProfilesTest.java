package sqltask;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import sqltask.courses.Course;
import sqltask.courses.CourseService;
import sqltask.groups.Group;
import sqltask.groups.GroupService;
import sqltask.students.Student;
import sqltask.students.StudentService;

import javax.sql.DataSource;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class RunApplicationWIthTestProfilesTest {

    @SpringBootTest
    @ActiveProfiles("test")
    @Nested
    class appRunnerTest {

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
    class appRunnerInsideMethodsTest {

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

        @Test
        void initializeGroups() {
            List<Group> groups = new ArrayList<>(Arrays.asList(
                    new Group(1, "11-aa"),
                    new Group(2,"22-bb")
            ));
            Mockito.when(groupService.generateGroups()).thenReturn(groups);
            initializeService.run(null);
            verify(groupService, times(1)).saveAll(groups);
        }

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
            initializeService.run(null);
            verify(studentService, times(1)).saveAll(groupSetting);
        }

        @Test
        void initializeCourses() {

            List<Course> courses = new ArrayList<>(Arrays.asList(
                    new Course(1,"name", "description"),
                    new Course(2, "name2", "description2")
            ));
            Mockito.when(courseService.makeCoursesList("testdata/coursestest.txt")).thenReturn(courses);
            initializeService.run(null);
            verify(courseService, times(1)).saveAll(courses);

        }
    }
}
