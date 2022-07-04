package sqltask.courses;

import org.junit.jupiter.api.Test;

import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class MethodsForCoursesTest {

    MethodsForCourses testObj = new MethodsForCourses();

    @Test
    void printCoursesTable_whenOneCourse_shouldReturnCorrectString() {

        Course testCourse = new Course(1, "History", "description");
        List<Course> testList = List.of(testCourse);
        String expected = """
                COURSES:
                    1.      History: description""";
        assertEquals(expected, testObj.printCoursesTable(testList));
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
        assertEquals(expected, testObj.printCoursesTable(testList));

    }
}