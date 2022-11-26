package sqltask.courses;

import org.junit.jupiter.api.Test;
import sqltask.students.Student;

import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class CourseUtilsTest {
    @Test
    void printMembers_whenMembersArePresence_shouldPrintThem() {

        List<Student> students = new ArrayList<>();
        Student student = new Student(1,1,"A", "B");
        Student student1 = new Student(1,1,"A", "B");
        students.add(student);
        students.add(student1);
        String result = """
                STUDENTS which have this COURSE:
                1    |1    |A          B
                1    |1    |A          B""";
        assertEquals(result, CourseUtils.printMembers(students));
    }
}