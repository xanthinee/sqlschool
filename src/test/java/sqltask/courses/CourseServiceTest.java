package sqltask.courses;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import sqltask.JdbcDaoTestConfig;
import sqltask.students.Student;
import java.util.*;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@SpringBootTest()
@ActiveProfiles("test")
class CourseServiceTest {

    @Autowired
    private CourseService courseService;

    @MockBean
    private CourseDAOJdbc courseDao;

    @Test
    void setCourse() {

        int studId = 10;
        String courseName = "Music";
        courseService.setNewCourse(studId,courseName);
        verify(courseDao, times(1)).setNewCourse(studId, courseName);
    }

    @Test
    void getCourseMembers() {

        List<Student> members = new ArrayList<>(Arrays.asList(
                new Student(1,1,"a","b"),
                new Student(2,2,"c","d"),
                new Student(3,3,"e", "f")
        ));
        Mockito.when(courseDao.getCourseMembers("Music")).thenReturn(members);
        assertEquals(members, courseService.getCourseMembers("Music"));
    }

    @Test
    void unlinkCourse() {

        int studId = 10;
        String courseName = "Music";
        courseService.unlinkCourse(studId, courseName);
        verify(courseDao, times(1)).unlinkCourse(studId, courseName);
    }

    @Test
    void deleteAll() {
        courseService.deleteAll();
        verify(courseDao, times(1)).deleteAll();
    }

    @Test
    void getAll() {

        List<Course> expected = new ArrayList<>(Arrays.asList(
                new Course(1,"name", "description"),
                new Course(2, "name2", "description2")
        ));
        Mockito.when(courseDao.getAll()).thenReturn(expected);
        assertEquals(expected, courseService.getAll());
    }

    @Test
    void getCoursesOfStudent() {

        List<Course> expected = new ArrayList<>(Arrays.asList(
                new Course(1,"name", "description"),
                new Course(2, "name2", "description2")
        ));
        int studId = 10;
        Mockito.when(courseDao.getCoursesOfStudent(studId)).thenReturn(expected);
        assertEquals(expected, courseService.getCoursesOfStudent(studId));
    }

    @Test
    void findAvailableCourses() {

        List<Course> expected = new ArrayList<>(Arrays.asList(
                new Course(1,"name", "description"),
                new Course(2, "name2", "description2")
        ));
        int studId = 10;
        Mockito.when(courseDao.findAvailableCourses(studId)).thenReturn(expected);
        assertEquals(expected, courseService.findAvailableCourses(studId));
    }

    @Test
    void getById() {

        int courseId = 1;
        Course expected = new Course(courseId,"name", "description");
        Mockito.when(courseDao.getById(courseId)).thenReturn(expected);
        assertEquals(expected, courseService.getById(courseId));
    }

    @Test
    void deleteById() {
        int courseId = 1;
        courseService.deleteById(courseId);
        verify(courseDao, times(1)).deleteById(courseId);
    }
}
