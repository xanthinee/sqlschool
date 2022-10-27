package sqltask.courses;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CourseParserTest {

    CoursesParser coursesParser = new CoursesParser();

    @Test
    void parse_whenStringHaveCorrectFormat_shouldCreateCourseObject() {

        String str = "1_courseName_courseDescription";
        Course course = new Course(null, "courseName", "courseDescription");
        assertEquals(course, coursesParser.parse(str));
    }
}
