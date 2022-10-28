package sqltask.students;

import org.junit.jupiter.api.Test;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

class StudentUtilsTest {

    @Test
    void printStudentsPartly_whenOneLine_shouldCorrectlyPrintThisLine() {

        Student testStud = new Student(1,1,"a","a");
        List<Student> testList = List.of(testStud);
        String expected = """
                Students which have desired course:
                1     |1     |a           |a\040\040\040\040\040\040\040\040\040\040\040\040\040\040""";
        assertEquals(expected, StudentUtils.printStudentsPartly(testList));
    }

    @Test
    void printStudentsPartly_whenSeveralStudentsAndSeveralLengths_shouldCorrectlyMakeString() {
        Student testStud = new Student(1,10,"anthony","abc");
        Student testStud1 = new Student(10,1000,"a","tomKins");
        Student testStud2 = new Student(100,100," ","Aa");
        Student testStud3 = new Student(null,null,"","XAVI");
        List<Student> testList = List.of(testStud,testStud1,testStud2,testStud3);
        String expected = """
                Students which have desired course:
                1     |10    |anthony     |abc\040\040\040\040\040\040\040\040\040\040\040\040
                10    |1000  |a           |tomKins\040\040\040\040\040\040\040\040
                100   |100   |            |Aa\040\040\040\040\040\040\040\040\040\040\040\040\040
                null  |null  |            |XAVI\040\040\040\040\040\040\040\040\040\040\040""";
        assertEquals(expected, StudentUtils.printStudentsPartly(testList));
    }

    @Test
    void printStudentsPartly_whenNoStudents_shouldPrintOnlyHeadliner() {
        List<Student> testList = List.of();
        String expected = "Students which have desired course:";
        assertEquals(expected, StudentUtils.printStudentsPartly(testList));
    }

    @Test
    void printStudentsPartly_whenNameOfStudentLongerThanColumnShouldModifyIt() {

        List<Student> students = new ArrayList<>();
        Student student = new Student(1,1,"artemartemartem","aaaaaaaaaaaaaaa");
        students.add(student);
        String expected = """
                Students which have desired course:
                1     |1     |artemarte...|aaaaaaaaaaaaaaa""";
        assertEquals(expected, StudentUtils.printStudentsPartly(students));
    }

    @Test
    void printStudentsPartly_whenSurnameOfStudentLongerThanColumn_shouldModifyIt() {

        List<Student> students = new ArrayList<>();
        Student student = new Student(1,1,"artemartemar","aaaaaaaaaaaaaaabb");
        students.add(student);
        String expected = """
                Students which have desired course:
                1     |1     |artemartemar|aaaaaaaaaaaa...""";
        assertEquals(expected, StudentUtils.printStudentsPartly(students));
    }

    @Test
    void printStudentsTable_whenThereAreStudents_shouldPrintThem() {

        List<Student> students = new ArrayList<>();
        Student student = new Student(1,1,"A", "B");
        Student student1 = new Student(1,1,"A", "B");
        Student student2 = new Student(1,1,"A", "B");
        students.add(student);
        students.add(student1);
        students.add(student2);
        String result = """
                STUDENTS
                1    |1    |A           |B\040\040\040\040\040\040\040\040\040\040\040\040\040\040
                1    |1    |A           |B\040\040\040\040\040\040\040\040\040\040\040\040\040\040
                1    |1    |A           |B\040\040\040\040\040\040\040\040\040\040\040\040\040\040""";
        assertEquals(result, StudentUtils.printStudentsTable(students));
    }
}