package sqltask.students;

import org.junit.jupiter.api.Test;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

class StudentUtilsTest {

    @Test
    void generateStudents_whenAskedForStudentsGeneration_shouldCreateListOfStudents() {

        List<Student> testList = StudentUtils.generateStudents();
        int expectedSizeOfList = 200;
        assertEquals(expectedSizeOfList, testList.size());
    }

    @Test
    void generateStudents_whenAskedForStudentsGeneration_StudentsShouldHaveNullsInTwoFirstFields() {
        List<Student> testList = StudentUtils.generateStudents();
        assertNull(testList.get(2).getStudentId());
    }

    @Test
    void printStudentsPartly_whenOneLine_shouldCorrectlyPrintThisLine() {

        Student testStud = new Student(1,1,"a","a");
        List<Student> testList = List.of(testStud);
        String expected = "Students which have desired course:\n" +
                "1      | 1      | a            | a";
        assertEquals(expected, StudentUtils.printStudentsPartly(testList));
    }

    @Test
    void printStudentsPartly_whenSeveralStudentsAndSeveralLengths_shouldCorrectlyMakeString() {
        Student testStud = new Student(1,10,"anthony","abc");
        Student testStud1 = new Student(10,1000,"a","tomKins");
        Student testStud2 = new Student(100,100," ","Aa");
        Student testStud3 = new Student(null,null,"","XAVI");
        List<Student> testList = List.of(testStud,testStud1,testStud2,testStud3);
        String expected = "Students which have desired course:\n" +
                "1      | 10     | anthony      | abc\n" +
                "10     | 1000   | a            | tomKins\n" +
                "100    | 100    |              | Aa\n" +
                "null   | null   |              | XAVI";
        assertEquals(expected, StudentUtils.printStudentsPartly(testList));
    }

    @Test
    void printStudentsPartly_whenNoStudents_shouldPrintOnlyHeadliner() {
        List<Student> testList = List.of();
        String expected = "Students which have desired course:";
        assertEquals(expected, StudentUtils.printStudentsPartly(testList));
    }
}