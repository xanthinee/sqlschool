package sqltask.courses;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import sqltask.students.Student;
import java.util.*;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

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

    @Test
    void makeCourseList_whenDataIsPresence_shouldMakeListOfCourses() {

        Course testCourse = new Course(null, "Mathematics", "mathematical science with geometry features");
        Course testCourse1 = new Course(null, "Computer science", "Study of computation and automation");
        Course testCourse2 = new Course(null, "Foreign language", "Study of English language");
        List<Course> testList = List.of(testCourse, testCourse1, testCourse2);
        assertEquals(testList, courseService.makeCoursesList("testdata/coursestest.txt"));
    }

    @Test
    void printCoursesOfStud_whenHasCourses_shouldPrintCourses() {

        List<Course> courses = new ArrayList<>();
        Course testCourse1 = new Course(1, "music", "description1");
        Course testCourse2 = new Course(2, "mathematics", "description2");
        Course testCourse3 = new Course(3, "english", "description3");
        courses.add(testCourse1);
        courses.add(testCourse2);
        courses.add(testCourse3);
        String result = """
                Entered STUDENT has next COURSES:\s
                1. music
                2. mathematics
                3. english""";
        assertEquals(result, CourseUtils.printCoursesOfStud(courses));
    }

    @Test
    void infoToPrint_whenStudentHasCourses_shouldPrintAllExceptOfAlreadyUsedCourses() {

        List<Course> allCourses = courseService.makeCoursesList("testdata/coursestest.txt");
        List<Course> avbCourses = new ArrayList<>();
        Course course = new Course(1, "Computer science", "description1");
        Course course2 = new Course(2, "Foreign language", "description2");
        avbCourses.add(course);
        avbCourses.add(course2);

        String result = """
                You can give next COURSES to STUDENT:
                1. Computer science
                2. Foreign language""";
        assertEquals(result, CourseUtils.infoToPrint(allCourses, avbCourses));
    }

    @Test
    void infoToPrint_whenStudentHasNoCourses_ShouldPrintAllCourses() {

        List<Course> allCourses = courseService.makeCoursesList("testdata/coursestest.txt");
        List<Course> avbCourses = new ArrayList<>();
        Course course = new Course(1, "Mathematics", "description");
        Course course1 = new Course(2, "Computer science", "description");
        Course course2 = new Course(3, "Foreign language", "description");
        avbCourses.add(course);
        avbCourses.add(course1);
        avbCourses.add(course2);
        String result = """
                You can give next COURSES to STUDENT:
                1. Mathematics
                2. Computer science
                3. Foreign language""";
        assertEquals(result, CourseUtils.infoToPrint(allCourses, avbCourses));
    }
}
