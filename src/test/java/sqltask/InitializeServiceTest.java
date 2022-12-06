package sqltask;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import sqltask.courses.Course;
import sqltask.courses.CourseService;
import sqltask.groups.Group;
import sqltask.groups.GroupService;
import sqltask.students.Student;
import sqltask.students.StudentService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class InitializeServiceTest {

        @InjectMocks
        InitializeService initializeService;
        @Mock
        GroupService groupService;
        @Mock
        CourseService courseService;
        @Mock
        StudentService studentService;

        @Test
        void initializeGroups() {
            List<Group> groups = new ArrayList<>(Arrays.asList(
                    new Group(1, "11-aa"),
                    new Group(2,"22-bb")
            ));
            Mockito.when(groupService.generateGroups()).thenReturn(groups);
            initializeService.initializeTables();
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
            initializeService.initializeTables();
            verify(studentService, times(1)).saveAll(groupSetting);
        }

        @Test
        void initializeCourses() {

            List<Course> courses = new ArrayList<>(Arrays.asList(
                    new Course(1,"name", "description"),
                    new Course(2, "name2", "description2")
            ));
            Mockito.doReturn(courses).when(courseService).makeCoursesList(anyString());
            initializeService.initializeTables();
            verify(courseService, times(1)).saveAll(courses);
        }

        @Test
        void initializeStudentsCourses() {
            initializeService.initializeTables();
            verify(courseService, times(1)).createStdCrsTable();
        }
}
