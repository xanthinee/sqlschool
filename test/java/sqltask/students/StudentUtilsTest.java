package sqltask.students;

import org.junit.jupiter.api.Test;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

class StudentUtilsTest {

    @Test
    void printStudentsPartly_whenOneLine_shouldCorrectlyPrintThisLine() {

        Student testStud = new Student(1, 1, "a", "a");
        List<Student> testList = List.of(testStud);
        StringJoiner sj = new StringJoiner(System.lineSeparator());
        sj.add("Students which have desired course:")
                .add("1     |1     |a           |a              ");
        assertEquals(sj.toString(), StudentUtils.printStudentsPartly(testList));
    }

    @Test
    void printStudentsPartly_whenSeveralStudentsAndSeveralLengths_shouldCorrectlyMakeString() {
        Student testStud = new Student(1,10,"anthony","abc");
        Student testStud1 = new Student(10,1000,"a","tomKins");
        Student testStud2 = new Student(100,100," ","Aa");
        Student testStud3 = new Student(null,null,"","XAVI");
        List<Student> testList = List.of(testStud,testStud1,testStud2,testStud3);
        StringJoiner sj = new StringJoiner(System.lineSeparator());
        sj.add("Students which have desired course:")
                .add("1     |10    |anthony     |abc            ")
                .add("10    |1000  |a           |tomKins        ")
                .add("100   |100   |            |Aa             ")
                .add("null  |null  |            |XAVI           ");
        assertEquals(sj.toString(), StudentUtils.printStudentsPartly(testList));
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
        Student student1 = new Student(1,1,"C", "D");
        Student student2 = new Student(1,1,"E", "F");
        students.add(student);
        students.add(student1);
        students.add(student2);
        StringJoiner sj = new StringJoiner(System.lineSeparator());
        sj.add("STUDENTS")
          .add("1    |1    |A           |B              ")
          .add("1    |1    |C           |D              ")
          .add("1    |1    |E           |F              ");
        assertEquals(sj.toString(), StudentUtils.printStudentsTable(students));
    }
}