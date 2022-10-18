package sqltask.courses;

import org.junit.jupiter.api.Test;

import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class CourseUtilsTest {

    @Test
    void printCoursesTable_whenOneCourse_shouldReturnCorrectString() {

        Course testCourse = new Course(1, "History", "description");
        List<Course> testList = List.of(testCourse);
        String expected = """
                COURSES:
                    1.      History: description""";
        assertEquals(expected, CourseUtils.printCoursesTable(testList));
    }

    @Test
    void printCoursesTable_whenSeveralCourses_shouldReturnCorrectString() {
        Course testCourse1 = new Course(1, "History", "d");
        Course testCourse2 = new Course(10, "Math", " ");
        Course testCourse3 = new Course(100, "IT", "description");
        Course testCourse4 = new Course(1000, " ", "DESCRIPTION");
        List<Course> testList = List.of(testCourse1, testCourse2, testCourse3, testCourse4);
        String expected = """
                COURSES:
                    1.      History: d
                   10.         Math:\040\040
                  100.           IT: description
                 1000.             : DESCRIPTION""";
        assertEquals(expected, CourseUtils.printCoursesTable(testList));
    }

    @Test
    void makeCourseList_whenDataIsPresence_shouldMakeListOfCourses() {

        Course testCourse = new Course(1, "Mathematics", "mathematical science with geometry features");
        Course testCourse1 = new Course(2, "Computer science", "Study of computation and automation");
        Course testCourse2 = new Course(3, "Foreign language", "Study of English language");
        List<Course> testList = List.of(testCourse, testCourse1, testCourse2);
        assertEquals(testList, CourseUtils.makeCoursesList("testdata/methodsforcourses.txt"));
    }
}