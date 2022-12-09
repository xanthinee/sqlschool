package sqltask.courses;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import sqltask.ContainersConfig;
import sqltask.TestConfig;
import sqltask.students.Student;
import sqltask.students.StudentDAOJdbc;

import java.util.*;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ContextConfiguration(initializers = {ContainersConfig.Initializer.class})
@SpringBootTest
@Import({ContainersConfig.class, TestConfig.class})
@ActiveProfiles("test")
class CourseServiceTest {

    @Autowired
    ApplicationContext applicationContext;
    @Autowired
    private CourseService courseService;

    @MockBean
    private CourseDAOJdbc courseDao;
    @MockBean
    private StudentDAOJdbc studentDao;

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
    void createStudCoursesTable_whenUnmodifiableListPassed_shouldCorrectlyMakeTables() {

        Student student = new Student(1000,1,"name","surname");
        List<Student> students = new ArrayList<>();
        students.add(student);
        List<Course> courses = new LinkedList<>(Arrays.asList(
                new Course(3,"name3","description3"),
                new Course(2, "name2", "description2"),
                new Course(4,"name4", "description4"),
                new Course(1,"name1", "description1")
            ));
        List<Course> unmodifiableList = Collections.unmodifiableList(courses);

        Mockito.when(studentDao.getAll()).thenReturn(students);
        Mockito.when(courseDao.getAll()).thenReturn(unmodifiableList);

        courseService.createStdCrsTable();
        List<Course> savedCourses = Arrays.asList(
                new Course(2, "name2", "description2"),
                new Course(3, "name3", "description3"),
                new Course(1, "name1", "description1"),
                new Course(4, "name4", "description4"));
        verify(courseDao, times(1)).saveStudentsCourses(student, savedCourses);
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
